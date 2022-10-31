package fr.ubx.poo.ubomb.model.bonus;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.character.Player;

public abstract class BombRange extends Bonus {
    public enum BombRangeBonus {INC, DEC}

    private final BombRangeBonus bombRangeBonus;

    public BombRange(Position position, BombRangeBonus bombRangeBonus) {
        super(position);
        this.bombRangeBonus = bombRangeBonus;
    }

    @Override
    public void playerTake(Player player) {
        if (!(bombRangeBonus == BombRange.BombRangeBonus.DEC && player.getBombRange() == 1)) {
            player.walkOnBombRange(bombRangeBonus);
            remove();
        }
    }
}