package fr.ubx.poo.ubomb.model.doors;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;

public class DoorPrevOpened extends Door {
    public DoorPrevOpened(Position position) {
        super(position, DoorOrientation.PREV, DoorLock.OPENED);
    }

    @Override
    public boolean isEntity(EntityCode entity) { return entity == EntityCode.DoorPrevOpened; }
}