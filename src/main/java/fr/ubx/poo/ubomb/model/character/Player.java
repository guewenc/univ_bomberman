package fr.ubx.poo.ubomb.model.character;

import fr.ubx.poo.ubomb.exception.GameException;
import fr.ubx.poo.ubomb.game.*;
import fr.ubx.poo.ubomb.model.GameObject;
import fr.ubx.poo.ubomb.model.bonus.*;
import fr.ubx.poo.ubomb.model.doors.*;

import java.util.concurrent.TimeUnit;

import static fr.ubx.poo.ubomb.game.EntityCode.Player;

public class Player extends Character {
    private int nbKeys;
    private int bombRange;
    private int bombBagCapacity;
    private boolean isWinner = false;

    public Player(Game game, Position position, int nbLives) {
        super(game, Direction.DOWN, nbLives, position);

        this.nbKeys = 0;
        this.bombRange = game.getGameConfig().getInitBombRange();
        this.bombBagCapacity = game.getGameConfig().getInitBombBagCapacity();
    }

    /** ********************************************************************** **/

    public int getNbKeys() {
        return Math.max(nbKeys, 0);
    }
    public void setNbKeys(int nbKeys) {
        this.nbKeys = nbKeys;
    }
    public int getBombRange() {
        return bombRange;
    }
    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }
    public int getBombBagCapacity() {
        return Math.max(bombBagCapacity, 0);
    }
    public void setBombBagCapacity(int bombBagCapacity) {
        this.bombBagCapacity = bombBagCapacity;
    }
    public int getPlayerLevel() {
        return getPosition().getLevel();
    }
    public void setPlayerLevel(int level) {
        setPosition(new Position(level, getPosition()));
    }
    public void setWinner() {
        this.isWinner = true;
    }

    /** ********************************************************************** **/

    public void incKeys() {
        setNbKeys(getNbKeys() + 1);
    }
    public void decKeys() {
        setNbKeys(getNbKeys() - 1);
    }
    public void incBombRange() {
        setBombRange(getBombRange() + 1);
    }
    public void decBombRange() {
        setBombRange(getBombRange() - 1);
    }
    public void incBombBagCapacity() {
        setBombBagCapacity(getBombBagCapacity() + 1);
    }
    public void decBombBagCapacity() {
        setBombBagCapacity(getBombBagCapacity() - 1);
    }

    public void decLives() {
        if (getInvisibilityTime() == 0) {
            setLives(getLives() - 1);
            setInvisibilityTime(TimeUnit.NANOSECONDS.convert(game.getGameConfig().getPlayerInvisibilityTime(), TimeUnit.MILLISECONDS));
        }
    }

    /**
     * **********************************************************************
     * *                         PLAYER WALK ON BONUS                      **
     * **********************************************************************
     **/

    public void walkOnKey() {
        incKeys();
    }
    public void walkOnHeart() {
        incLives();
    }

    public void walkOnBombRange(BombRange.BombRangeBonus bombRangeBonus) {
        if (bombRangeBonus == BombRange.BombRangeBonus.INC) {
            incBombRange();
        } else {
            decBombRange();
        }
    }

    public void walkOnBombBagCapacity(BombBagCapacity.BombNumberBonus bombNumberBonus) {
        if (bombNumberBonus == BombBagCapacity.BombNumberBonus.INC) {
            incBombBagCapacity();
        } else {
            decBombBagCapacity();
        }
    }

    public void takeDoor(Door.DoorOrientation doorOrientation, Door.DoorLock doorLock) throws GameException {
        if (doorOrientation == Door.DoorOrientation.NEXT) {
            if (!(game.getCurrentLevel() == game.getGameConfig().getLevels())) {
                if (doorLock == Door.DoorLock.OPENED) {
                    game.incCurrentLevel();
                }
            } else {
                throw new GameException("An error are occurred on level change");
            }
        } else if (doorOrientation == Door.DoorOrientation.PREV) {
            if (!(game.getCurrentLevel() == 1)) {
                if (doorLock == Door.DoorLock.OPENED) {
                    game.decCurrentLevel();
                }
            } else {
                throw new GameException("An error are occurred on level change");
            }
        } else {
            throw new GameException("An error is present in the implementation of the level " + game.getCurrentLevel() + " card.");
        }
    }

    public void update(long time) {
        if (isMoveRequested())
            if (canMove(getDirection()))
                doMove(getDirection());

        if (getInvisibilityTime() > 0)
            decInvisibilityTime(time);

        if (getInvisibilityTime() < 0)
            setInvisibilityTime(0);

        setMoveRequested(false);
    }

    public void doMove(Direction direction) {
        setPosition(direction.nextPosition(getPosition()));
        game.getGameObjects(getPosition()).stream().filter(
                gameObject -> {
                    Class<?> className = gameObject.getClass();
                    return className == DoorNextClosed.class ||
                            className == DoorNextOpened.class ||
                            className == DoorPrevOpened.class;
                }).forEach(gameObject -> gameObject.playerTake(game.getPlayer()));
    }

    public boolean isWinner() {
        return isWinner;
    }
    public boolean isEntity(EntityCode entity) {
        return entity == Player;
    }
}