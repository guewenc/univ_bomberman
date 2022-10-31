package fr.ubx.poo.ubomb.model.bonus;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;

import static fr.ubx.poo.ubomb.game.EntityCode.BombRangeDec;

public class BombRangeDec extends BombRange {
    public BombRangeDec(Position position) { super(position, BombRangeBonus.DEC); }

    @Override
    public boolean isEntity(EntityCode entity) { return entity == BombRangeDec; }
}