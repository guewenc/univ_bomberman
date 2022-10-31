package fr.ubx.poo.ubomb.model.bonus;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;

import static fr.ubx.poo.ubomb.game.EntityCode.BombBagCapacityInc;

public class BombBagCapacityInc extends BombBagCapacity {
    public BombBagCapacityInc(Position position) {
        super(position, BombNumberBonus.INC);
    }

    @Override
    public boolean isEntity(EntityCode entity) { return entity == BombBagCapacityInc; }
}