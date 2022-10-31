package fr.ubx.poo.ubomb.model.bonus;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;

import static fr.ubx.poo.ubomb.game.EntityCode.BombBagCapacityDec;

public class BombBagCapacityDec extends BombBagCapacity {
    public BombBagCapacityDec(Position position) {
        super(position, BombNumberBonus.DEC);
    }

    @Override
    public boolean isEntity(EntityCode entity) {
        return entity == BombBagCapacityDec;
    }
}