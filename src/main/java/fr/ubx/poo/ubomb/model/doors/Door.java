package fr.ubx.poo.ubomb.model.doors;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.GameObject;
import fr.ubx.poo.ubomb.model.character.Player;
import fr.ubx.poo.ubomb.model.decor.Decor;

import static fr.ubx.poo.ubomb.game.EntityCode.*;

import java.util.List;

public abstract class Door extends Decor {
    private final DoorOrientation doorOrientation;
    private final DoorLock doorLock;

    public enum DoorOrientation {PREV, NEXT}
    public enum DoorLock {OPENED, CLOSED}

    public Door(Position position, DoorOrientation doorOrientation, DoorLock doorLock) {
        super(position, doorLock == DoorLock.CLOSED ? List.of() : List.of(Player));
        this.doorOrientation = doorOrientation;
        this.doorLock = doorLock;
    }

    @Override
    public boolean canExplode() {
        return false;
    }

    @Override
    public void playerTake(Player player) {
        try {
            player.takeDoor(doorOrientation, doorLock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}