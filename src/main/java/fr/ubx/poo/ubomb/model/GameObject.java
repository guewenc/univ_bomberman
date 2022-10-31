package fr.ubx.poo.ubomb.model;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.character.Player;

import java.util.List;

/**
 * A GameObject is an entity in the grid, it may know its position
 */
public abstract class GameObject implements Takeable, Explodable, Walkable {
    public final Game game;
    private boolean deleted = false;
    private boolean modified = true;
    private Position position;

    private final List<EntityCode> authorizedWalk;

    public GameObject(Game game, Position position, List<EntityCode> authorizedWalk) {
        this.game = game;
        this.position = position;
        this.authorizedWalk = authorizedWalk;
    }

    public GameObject(Position position, List<EntityCode> authorizedWalk) {
        this(null, position, authorizedWalk);
    }

    /** *********************************************************************** **/

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        setModified(true);
    }

    public boolean isModified() {
        return modified;
    }
    public void setModified(boolean modified) {
        this.modified = modified;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void remove() {
        deleted = true;
    }

    public boolean isWalkable(GameObject object) {
        return authorizedWalk.stream().anyMatch(object::isEntity);
    }

    public boolean isEntity(EntityCode entity) {
        return false;
    }

    @Override
    public void playerTake(Player player) {
    }

    public boolean canExplode() {
        return true;
    }
    public void onExplode() {
        remove();
    }
}