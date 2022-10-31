package fr.ubx.poo.ubomb.engine;

import fr.ubx.poo.ubomb.game.*;
import fr.ubx.poo.ubomb.model.*;
import fr.ubx.poo.ubomb.model.bombs.Bomb;
import fr.ubx.poo.ubomb.model.bombs.Explosion;
import fr.ubx.poo.ubomb.model.character.Monster;
import fr.ubx.poo.ubomb.model.character.Player;
import fr.ubx.poo.ubomb.model.doors.Door;
import fr.ubx.poo.ubomb.model.doors.DoorNextOpened;
import fr.ubx.poo.ubomb.view.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static fr.ubx.poo.ubomb.game.EntityCode.*;
import static fr.ubx.poo.ubomb.view.ImageResource.*;

public final class GameEngine {
    private static AnimationTimer gameLoop;
    private final String windowTitle;
    private final List<Sprite> sprites = new LinkedList<>();
    private final Set<Sprite> cleanUpSprites = new HashSet<>();
    private final Stage stage;
    private StatusBar statusBar;
    private Pane layer;
    private Input input;

    private final Game game;
    private final Player player;
    private long gameTime = 0;

    private enum LevelChangeState {DEC, INC}

    public GameEngine(final String windowTitle, Game game, final Stage stage) {
        this.stage = stage;
        this.windowTitle = windowTitle;
        this.game = game;
        this.player = game.getPlayer();
        initialize();
        buildAndSetGameLoop();
    }

    /** **********************************************************************
     **                 GAME GESTION PART (LOOP / INITIALIZE                **
     ** ******************************************************************* **/

    private void initialize() {
        Group root = new Group();
        layer = new Pane();

        updateScene(root);
        updateStatusBar(root);
        setAllSprites(game.getGameConfig().getInitPlayerPosition(), LevelChangeState.INC);
    }

    private void updateScene(Group root) {
        int height = game.getGrid().getHeight();
        int width = game.getGrid().getWidth();
        int sceneWidth = Math.max((width * Sprite.size), 12 * Sprite.size);
        int sceneHeight = height * Sprite.size;
        Scene scene = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());

        stage.setTitle(windowTitle);
        stage.getIcons().add(BOMB_3.getImage());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        input = new Input(scene);
        root.getChildren().add(layer);
    }

    private void updateStatusBar(Group root) {
        int height = game.getGrid().getHeight();
        int width = game.getGrid().getWidth();
        int sceneWidth = Math.max((width * Sprite.size), 12 * Sprite.size);
        int sceneHeight = height * Sprite.size;

        statusBar = new StatusBar(root, sceneWidth, sceneHeight);
    }

    private void setAllSprites(Position playerPosition, LevelChangeState levelChangeState) {
        game.getGrid().values().forEach(object -> {
            createNewSprites(object);
            if (playerPosition == null)
                if (levelChangeState == LevelChangeState.INC && object.isEntity(DoorPrevOpened) || levelChangeState == LevelChangeState.DEC && object.isEntity(DoorNextOpened))
                    player.setPosition(object.getPosition());
        });

        game.getAllObjetsCurrentLevel().forEach(this::createNewSprites);

        if (playerPosition != null)
            player.setPosition(playerPosition);
        createNewSprites(player);
    }

    private void buildAndSetGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long now) {
                long timeGap = now - gameTime;
                gameTime = now;

                processInput();

                update(timeGap);
                checkExplosions(timeGap);

                cleanupSprites();
                render();
                statusBar.update(game);

                if (game.getCurrentLevel() != player.getPlayerLevel())
                    updateOnLevelChange();
            }
        };
    }

    private void updateOnLevelChange() {
        Group root = new Group();
        LevelChangeState levelChangeState = player.getPlayerLevel() < game.getCurrentLevel() ? LevelChangeState.INC : LevelChangeState.DEC;

        player.setPlayerLevel(game.getCurrentLevel());

        removeAllSprites();
        updateScene(root);
        updateStatusBar(root);
        setAllSprites(null, levelChangeState);
    }

    /** **********************************************************************
     **                        EXPLOSION / BOMB PART                        **
     ** ******************************************************************* **/

    private void createBombs() {
        // The player must have at least one bomb in their inventory.
        if (player.getBombBagCapacity() == 0)
            return;

        if(game.getGameObjects(player.getPosition()).stream().allMatch(GameObject::canExplode)) {
            Bomb bomb = new Bomb(game, player.getPosition());

            game.addNewObjects(bomb);
            createNewSprites(bomb);

            player.decBombBagCapacity();
        }
    }

    private void createExplosion(int bombRange, Position position) {
        List<Direction> allDirections = Direction.getDirections();
        List<Direction> singleDirection = List.of(Direction.NONE);
        List<Direction> directionsToRemove = new ArrayList<>();

        for (int range = 0; range <= bombRange; range++) {
            for (Direction dir : range == 0 ? singleDirection : allDirections) {
                Position explosionPosition = dir.nextPosition(position, range);
                if (game.isInsidePosition(explosionPosition)) {
                    if (!game.getGameObjects(explosionPosition).isEmpty()) {
                        game.getGameObjects(explosionPosition).stream().filter(GameObject::canExplode).forEach(object -> {
                            setExplosion(explosionPosition);
                            object.onExplode();
                        });
                        directionsToRemove.add(dir);
                    } else {
                        if (game.getObjets(Bomb).stream().noneMatch(bomb -> bomb.getPosition().equals(explosionPosition))) { // Is not a bomb
                            setExplosion(explosionPosition);
                        } else {
                            directionsToRemove.add(dir);
                        }
                    }

                    if (game.getPlayer().getPosition().equals(explosionPosition)) {
                        game.getPlayer().onExplode();
                        directionsToRemove.add(dir);
                    }
                }
            }

            directionsToRemove.forEach(allDirections::remove);
        }
    }

    private void setExplosion(Position explosionPosition) {
        Explosion explosion = new Explosion(game, explosionPosition);
        game.addNewObjects(explosion);
        createNewSprites(explosion);
    }

    private void checkExplosions(long now) {
        List<Bomb> bombToRemove = new ArrayList<>();
        List<Explosion> explosionToRemove = new ArrayList<>();

        // The bomb exploded. Spawn of the explosion
        game.getObjets(Bomb).stream().map(gameObject -> (Bomb) gameObject).forEach(bomb -> {
            bomb.decTimeBeforeExplode(now);
            updateBombState(bomb);
            if (bomb.getPosition().getLevel() == game.getCurrentLevel())
                bomb.setModified(true);
            if (bomb.getTimeBeforeExplode() <= 0) {
                player.incBombBagCapacity();
                bombToRemove.add(bomb);
                createExplosion(bomb.getBombRange(), bomb.getPosition());
            }
        });

        game.getObjets(Explosion).stream().map(object -> (Explosion) object).forEach(explosion -> {
            explosion.decTimeBeforeExplode(now);
            if (explosion.getTimeBeforeExplode() <= 0)
                explosionToRemove.add(explosion);
        });

        bombToRemove.forEach(game::removeObject);
        explosionToRemove.forEach(game::removeObject);
    }

    private void updateBombState(Bomb bomb) {
        bomb.setBombState((int) (bomb.getTimeBeforeExplode() / TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS)));
    }

    /** **********************************************************************
     **                         DOOR MANAGEMENT PART                        **
     ** ******************************************************************* **/

    private void wantTakeDoor() {
        Position potentialDoorPosition = game.getPlayer().getDirection().nextPosition(game.getPlayer().getPosition());
        if (foundDoor(potentialDoorPosition)) {
            Door door = (Door) game.getDecor(potentialDoorPosition);
            // >= To avoid possible but must be strictly equal to 1 normally.
            if (player.getNbKeys() >= 1) {
                player.decKeys();

                game.getGrid().set(potentialDoorPosition, new DoorNextOpened(potentialDoorPosition));
                createNewSprites(game.getGrid().get(potentialDoorPosition));
                door.remove();
            }
        }
    }

    private boolean foundDoor(Position pos) {
        if (game.isDecorNull(pos))
            return false;
        return game.getDecor(pos).isEntity(EntityCode.DoorNextClosed);
    }

    /** **********************************************************************
     **                        SYSTEM MANAGEMENT PART                       **
     ** ******************************************************************* **/

    private void processInput() {
        if (input.isExit()) {
            gameLoop.stop();
            Platform.exit();
            System.exit(0);
        } else if (input.isMoveDown()) {
            player.requestMove(Direction.DOWN);
        } else if (input.isMoveLeft()) {
            player.requestMove(Direction.LEFT);
        } else if (input.isMoveRight()) {
            player.requestMove(Direction.RIGHT);
        } else if (input.isMoveUp()) {
            player.requestMove(Direction.UP);
        } else if (input.isKey()) {
            wantTakeDoor();
        } else if (input.isBomb()) {
            createBombs();
        }
        input.clear();
    }

    private void showMessage(String msg, Color color) {
        Text waitingForKey = new Text(msg);
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(60));
        waitingForKey.setFill(color);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene = new Scene(root, 400, 200, Color.WHITE);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        input = new Input(scene);
        stage.show();
        new AnimationTimer() {
            public void handle(long now) {
                processInput();
            }
        }.start();
    }

    /** **********************************************************************
     **                            SPRITES GESTION                          **
     ** ******************************************************************* **/

    private void update(long now) {
        game.getGameObjects(player.getPosition()).stream().filter(
                object -> !(object.isEntity(DoorNextOpened)
                         || object.isEntity(DoorPrevOpened)
                         || object.isEntity(DoorNextClosed))
        ).forEach(gameObject -> gameObject.playerTake(player));

        sprites.stream().filter(sprite -> sprite.getClass() == SpriteMonster.class).forEach(Sprite::updateImage);
        sprites.stream().filter(sprite -> sprite.getClass() == SpritePlayer.class).forEach(Sprite::updateImage);
        game.getObjets(Monster).forEach(object -> {
            Monster monster = (Monster) object;
            monster.update(now);
        });
        game.getObjets(Box).stream().filter(GameObject::isModified).forEach(this::createNewSprites);
        player.update(now);

        if (player.getLives() == 0) {
            gameLoop.stop();
            showMessage("Perdu!", Color.RED);
        }

        if (player.isWinner()) {
            gameLoop.stop();
            showMessage("Gagné", Color.BLUE);
        }

        List<Monster> monsterToDelete = game.getObjets(Monster).stream().map(object -> (Monster) object).filter(monster -> monster.getLives() == 0).collect(Collectors.toList());
        monsterToDelete.forEach(game::removeObject);
    }

    private void createNewSprites(GameObject objet) {
        if (objet.getPosition().getLevel() == player.getPlayerLevel()) {
            sprites.add(SpriteFactory.create(layer, objet));
            objet.setModified(true);
        }
    }

    private void removeAllSprites() {
        cleanUpSprites.addAll(sprites);
    }

    private void cleanupSprites() {
        sprites.stream().filter(sprite -> sprite.getGameObject().isDeleted()).forEach(sprite -> {
            game.getGrid().remove(sprite.getPosition(), sprite.getGameObject());
            cleanUpSprites.add(sprite);
        });

        cleanUpSprites.forEach(Sprite::remove);
        sprites.removeAll(cleanUpSprites);
        cleanUpSprites.clear();
    }

    private void render() {
        sprites.forEach(Sprite::render);
    }
    public void start() {
        gameLoop.start();
    }
}