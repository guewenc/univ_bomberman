package fr.ubx.poo.ubomb.model.doors;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;

public class DoorNextClosed extends Door {
    public DoorNextClosed(Position position) {
        super(position, DoorOrientation.NEXT, DoorLock.CLOSED);
    }

    @Override
    public boolean isEntity(EntityCode entity) { return entity == EntityCode.DoorNextClosed; }
}