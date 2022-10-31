package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.model.GameObject;
import fr.ubx.poo.ubomb.model.character.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static fr.ubx.poo.ubomb.game.EntityCode.*;

public class Game {
    private final List<Grid> grid = new ArrayList<>();
    private final List<GameObject> objects = new ArrayList<>();

    private final GameConfig gameConfig;
    private final Player player;
    private int currentLevel = 1;

    public Game(String worldPath) {
        gameConfig = new GameConfig(worldPath);
        GridRepo gridRepo = new GridRepoFile(this);

        this.player = new Player(this, gameConfig.getInitPlayerPosition(), gameConfig.getInitPlayerLives());
        player.setPlayerLevel(currentLevel);

        // Initialization of all grids
        for(int level = 1; level <= gameConfig.getLevels(); level++) {
            try {
                grid.add(level - 1, gridRepo.load(level, worldPath + "/" + gameConfig.getLevelPrefix() + level));
            } catch (Exception e) {
                    e.printStackTrace();
            }

            int finalLevel = level;

            List<GameObject> collectObjects = getGrid(level).values().stream().filter(object -> object.isEntity(Monster) || object.isEntity(Box)).collect(Collectors.toList());
            collectObjects.forEach(object -> {
                addNewObjects(object);
                getGrid(finalLevel).remove(object.getPosition(), object);
            });
        }
    }

    /** *********************************************************************** **/

    public Player getPlayer() {
        return this.player;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Grid getGrid() {
        return grid.get(currentLevel - 1);
    }
    public Grid getGrid(int level) {
        return grid.get(level - 1);
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    /** *********************************************************************** **/

    public List<GameObject> getAllObjetsCurrentLevel() {
        return objects.stream().filter(x -> x.getPosition().getLevel() == getCurrentLevel()).collect(Collectors.toList());
    }
    public List<GameObject> getObjets(EntityCode entityCode) {
        return objects.stream().filter(object -> object.isEntity(entityCode)).collect(Collectors.toList());
    }
    public List<GameObject> getObjets(int level, EntityCode entityCode) {
        return getObjets(entityCode).stream().filter(x -> x.getPosition().getLevel() == level).collect(Collectors.toList());
    }
    public void removeObject(GameObject object) {
        objects.remove(object);
        object.remove();
    }
    public void addNewObjects(GameObject object) {
        objects.add(object);
    }
    public GameObject getDecor(Position pos) {
        return getGrid(pos.getLevel()).get(pos);
    }

    public List<GameObject> getGameObjects(Position position) {
        List<GameObject> gos = new ArrayList<>();

        if(!isDecorNull(position))
            gos.add(getDecor(position));

        if (getPlayer().getPosition().equals(position))
            gos.add(player);

        getObjets(position.getLevel(), Monster).stream().filter(monster -> monster.getPosition().equals(position)).forEach(gos::add);
        getObjets(position.getLevel(), Bomb).stream().filter(bomb -> bomb.getPosition().equals(position)).forEach(gos::add);
        getObjets(position.getLevel(), Box).stream().filter(box -> box.getPosition().equals(position)).forEach(gos::add);

        return gos;
    }

    /** *********************************************************************** **/

    public void incCurrentLevel() {
        setCurrentLevel(getCurrentLevel() + 1);
    }
    public void decCurrentLevel() {
        setCurrentLevel(getCurrentLevel() - 1);
    }

    /** *********************************************************************** **/

    public boolean isInsidePosition(Position pos) {
        return pos.getX() >= 0
                && pos.getX() < this.getGrid(pos.getLevel()).getWidth()
                && pos.getY() >= 0
                && pos.getY() < this.getGrid(pos.getLevel()).getHeight();
    }

    public boolean isDecorNull(Position pos) {
        return getGrid(pos.getLevel()).get(pos) == null;
    }
}