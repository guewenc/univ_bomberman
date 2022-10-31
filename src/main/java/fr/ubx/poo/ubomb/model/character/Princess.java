package fr.ubx.poo.ubomb.model.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.GameObject;

import java.util.List;

import static fr.ubx.poo.ubomb.game.EntityCode.Player;

public class Princess extends Character {
    public Princess(Game game, Position position) {
        super(game, null, 1, position, List.of(Player));
    }

    @Override
    public void playerTake(Player player) {
        player.setWinner();
    }

    @Override
    public boolean canExplode() {
        return false;
    }

    @Override
    public void doMove(Direction direction) {

    }
}
