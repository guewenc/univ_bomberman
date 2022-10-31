package fr.ubx.poo.ubomb.model.bonus;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.character.Player;

import static fr.ubx.poo.ubomb.game.EntityCode.Heart;

public class Heart extends Bonus {
    public Heart(Position position) {
        super(position);
    }

    public void playerTake(Player player) {
        player.walkOnHeart();
        remove();
    }

    @Override
    public boolean isEntity(EntityCode entity) {
        return entity == Heart;
    }
}