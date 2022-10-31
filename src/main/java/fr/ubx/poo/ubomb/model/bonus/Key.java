package fr.ubx.poo.ubomb.model.bonus;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.character.Player;

import static fr.ubx.poo.ubomb.game.EntityCode.Key;

public class Key extends Bonus {
    public Key(Position position) {
        super(position);
    }

    public void playerTake(Player player) {
        player.walkOnKey();
        remove();
    }

    @Override
    public boolean isEntity(EntityCode entity) {
        return entity == Key;
    }

    @Override
    public boolean canExplode() {
        return false;
    }
}