package fr.ubx.poo.ubomb.model.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.GameObject;
import fr.ubx.poo.ubomb.model.Movable;

import java.util.List;

import static fr.ubx.poo.ubomb.game.EntityCode.Monster;
import static fr.ubx.poo.ubomb.game.EntityCode.Player;

public abstract class Character extends GameObject implements Movable {
    private Direction direction;
    private int lives;
    private long invisibilityTime = 0;
    private boolean moveRequested = false;

    public Character(Game game, Direction direction, int nbLives, Position position, List<EntityCode> authrorizedWalk) {
        super(game, position, authrorizedWalk);
        this.direction = direction;
        this.lives = nbLives;
    }

    public Character(Game game, Direction direction, int nbLives, Position position) {
        this(game, direction, nbLives, position, List.of(Player, Monster));
    }

    /** *********************************************************************** **/

    public void setLives(int lives) {
        this.lives = lives;
    }
    public int getLives() {
        return Math.max(lives, 0);
    }

    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public long getInvisibilityTime() {
        return invisibilityTime;
    }

    public void setInvisibilityTime(long invisibilityTime) {
        this.invisibilityTime = invisibilityTime;
    }
    public void decInvisibilityTime(long time) {
        setInvisibilityTime(getInvisibilityTime() - time);
    }

    public boolean isMoveRequested() {
        return moveRequested;
    }
    public void setMoveRequested(boolean moveRequested) {
        this.moveRequested = moveRequested;
    }

    public void decLives() {}
    public void incLives() {
        setLives(getLives() + 1);
    }

    /** *********************************************************************** **/

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
            setModified(true);
        }
        moveRequested = true;
    }

    public boolean canMove(Direction direction) {
        if(game.isInsidePosition(direction.nextPosition(getPosition())))
            return game.getGameObjects(direction.nextPosition(getPosition())).stream().allMatch(gameObject -> gameObject.isWalkable(this));
        return false;
    }

    @Override
    public boolean canExplode() {
        return true;
    }

    @Override
    public void onExplode() {
        decLives();
    }
}