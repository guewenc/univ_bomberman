package fr.ubx.poo.ubomb.model.decor;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;

public class Stone extends Decor {
    public Stone(Position position) {
        super(position);
    }

    @Override
    public boolean isEntity(EntityCode entity) {
        return entity == EntityCode.Stone;
    }
}