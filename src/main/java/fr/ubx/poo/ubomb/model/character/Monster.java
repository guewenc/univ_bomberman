package fr.ubx.poo.ubomb.model.character;

import fr.ubx.poo.ubomb.game.*;
import fr.ubx.poo.ubomb.model.GameObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static fr.ubx.poo.ubomb.game.EntityCode.Monster;
import static fr.ubx.poo.ubomb.game.EntityCode.Player;

public class Monster extends Character {
    private long timeBeforeNextMove;
    private final int monsterVelocity;

    public Monster(Game game, Position position) {
        super(game, Direction.DOWN, game.getGameConfig().getMonsterLives(), position, List.of(Player));
        monsterVelocity = game.getGameConfig().getMonsterVelocity() + 5 * (position.getLevel() - 1);
        setTimeBeforeNextMove(monsterVelocity);

        setLivesBasedOnLevel();
    }

    /** *********************************************************************** **/

    public long getTimeBeforeNextMove() {
        return timeBeforeNextMove;
    }

    public void setTimeBeforeNextMove(int monsterVelocity) {
        // 0,1 to avoid any problem of division by 0.
        this.timeBeforeNextMove = TimeUnit.NANOSECONDS.convert((long) (1000 / (monsterVelocity + 0.1) * 20), TimeUnit.MILLISECONDS);
    }

    public void decTimeBeforeNextMove(long now) {
        this.timeBeforeNextMove -= now;
    }

    public void decLives() {
        if(getInvisibilityTime() == 0) {
            setLives(getLives() - 1);
            setInvisibilityTime(TimeUnit.NANOSECONDS.convert(game.getGameConfig().getMonsterInvisibilityTime(), TimeUnit.MILLISECONDS));
        }
    }

    private Direction getMonsterDirection() {
        return SmartPosition.getNextDirection(game.getPlayer().getPosition(), getPosition());
    }

    private void setLivesBasedOnLevel() {
        for(int i = 1; i <= getPosition().getLevel(); i++)
            if(i % 2 == 0)
                incLives();
    }

    /** *********************************************************************** **/

    public void update(long time) {
        if(getTimeBeforeNextMove() <= 0) {
            Direction dir = getMonsterDirection();
            if (canMove(dir)) {
                setTimeBeforeNextMove(monsterVelocity);
                doMove(dir);
            }
        }

        if(getTimeBeforeNextMove() > 0)
            decTimeBeforeNextMove(time);

        if(getInvisibilityTime() > 0)
            decInvisibilityTime(time);

        if(getInvisibilityTime() < 0)
            setInvisibilityTime(0);
    }

    @Override
    public void doMove(Direction direction) {
        setPosition(direction.nextPosition(getPosition()));
        setDirection(direction);
    }

    @Override
    public void playerTake(Player player) {
        player.decLives();
    }

    @Override
    public boolean isEntity(EntityCode entity) {
        return entity == Monster;
    }
}