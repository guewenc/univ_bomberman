package fr.ubx.poo.ubomb.model.others;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.GameObject;
import fr.ubx.poo.ubomb.model.Movable;
import fr.ubx.poo.ubomb.model.character.Player;

import java.util.List;

import static fr.ubx.poo.ubomb.game.EntityCode.*;

public class Box extends GameObject implements Movable {
    public Box(Game game, Position position) {
        super(game, position, List.of(Player));
    }

    @Override
    public boolean isWalkable(GameObject object) {
        if(object.isEntity(Player))
            if(game.getPlayer().isMoveRequested() && getPosition().getLevel() == game.getPlayer().getPlayerLevel())
                return checkBoxMove(game.getPlayer());
        return false;
    }

    private boolean checkBoxMove(Player player) {
        Direction movementDirection = player.getDirection();

        if(game.isInsidePosition(movementDirection.nextPosition(getPosition()))) {
            for(GameObject object : game.getGameObjects(movementDirection.nextPosition(getPosition()))) {
                if(!object.isWalkable(this))
                    return false;
            }

            doMove(movementDirection);
            return true;
        }
        return false;
    }

    @Override
    public boolean canMove(Direction direction) {
        return false;
    }

    @Override
    public void doMove(Direction direction) {
        Box newBox = new Box(game, direction.nextPosition(getPosition()));
        game.addNewObjects(newBox);
        newBox.setModified(true);
        game.removeObject(this);
    }

    @Override
    public void onExplode() {
        game.removeObject(this);
    }

    @Override
    public boolean isEntity(EntityCode entity) { return entity == EntityCode.Box; }
}