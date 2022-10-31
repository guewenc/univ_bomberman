package fr.ubx.poo.ubomb.model.bombs;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.GameObject;

import java.util.List;

import static java.util.concurrent.TimeUnit.*;

public class Bomb extends GameObject {
    private int bombState = 3;
    private final int bombRange;
    private long timeBeforeExplode = NANOSECONDS.convert(game.getGameConfig().getTimeBeforeBombExplode(), MILLISECONDS);

    public Bomb(Game game, Position position) {
        super(game, position, List.of());
        this.bombRange = game.getPlayer().getBombRange();
    }

    /** *********************************************************************** **/

    public int getBombState() { return bombState; }
    public int getBombRange() { return bombRange; }
    public long getTimeBeforeExplode() { return timeBeforeExplode; }

    public void setBombState(int bombState) { this.bombState = bombState; }
    public void decTimeBeforeExplode(long time) { timeBeforeExplode -= time; }

    /** *********************************************************************** **/

    @Override
    public boolean canExplode() { return true; }

    @Override
    public void onExplode() { timeBeforeExplode = 0; }

    @Override
    public boolean isEntity(EntityCode entity) {
        return entity == EntityCode.Bomb;
    }
}