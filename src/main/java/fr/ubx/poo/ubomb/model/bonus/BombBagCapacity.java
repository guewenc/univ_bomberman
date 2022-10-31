package fr.ubx.poo.ubomb.model.bonus;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.character.Player;

public abstract class BombBagCapacity extends Bonus {
    public enum BombNumberBonus { INC, DEC }

    private final BombNumberBonus bombNumberBonus;

    public BombBagCapacity(Position position, BombNumberBonus bombNumberBonus) {
        super(position);
        this.bombNumberBonus = bombNumberBonus;
    }

    @Override
     public void playerTake(Player player) {
        if (bombNumberBonus == BombNumberBonus.DEC && player.getBombBagCapacity() == 1)
            return;
        player.walkOnBombBagCapacity(bombNumberBonus);
        remove();
    }
}