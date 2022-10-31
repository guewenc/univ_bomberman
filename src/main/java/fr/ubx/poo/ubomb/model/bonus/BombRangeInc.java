package fr.ubx.poo.ubomb.model.bonus;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;

import static fr.ubx.poo.ubomb.game.EntityCode.BombRangeInc;

public class BombRangeInc extends BombRange {
    public BombRangeInc(Position position) {
        super(position, BombRangeBonus.INC);
    }

    @Override
    public boolean isEntity(EntityCode entity) { return entity == BombRangeInc; }
}