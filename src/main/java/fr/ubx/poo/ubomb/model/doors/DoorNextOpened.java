package fr.ubx.poo.ubomb.model.doors;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;

public class DoorNextOpened extends Door {
    public DoorNextOpened(Position position) {
        super(position, DoorOrientation.NEXT, DoorLock.OPENED);
    }

    @Override
    public boolean isEntity(EntityCode entity) {
        return entity == EntityCode.DoorNextOpened;
    }
}