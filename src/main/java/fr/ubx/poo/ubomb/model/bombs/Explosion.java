package fr.ubx.poo.ubomb.model.bombs;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.GameObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Explosion extends GameObject {
    private long timeBeforeExplode = TimeUnit.NANOSECONDS.convert(game.getGameConfig().getTimeBeforeExplosionRemove(), TimeUnit.MILLISECONDS);

    public Explosion(Game game, Position position) {
        super(game, position, List.of(EntityCode.Monster, EntityCode.Player));
    }

    /** *********************************************************************** **/

    public long getTimeBeforeExplode() {
        return timeBeforeExplode;
    }
    public void decTimeBeforeExplode(long time) {
        timeBeforeExplode -= time;
    }

    /** *********************************************************************** **/

    @Override
    public boolean isEntity(EntityCode entity) {
        return entity == EntityCode.Explosion;
    }
}