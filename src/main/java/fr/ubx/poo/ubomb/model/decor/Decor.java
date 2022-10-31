package fr.ubx.poo.ubomb.model.decor;

import fr.ubx.poo.ubomb.game.EntityCode;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.model.GameObject;

import java.util.List;

public abstract class Decor extends GameObject {
    public Decor(Position position, List<EntityCode> authorizedWalk) {
        super(position, authorizedWalk);
    }

    public Decor(Position position) {
        this(position, List.of());
    }

    @Override
    public boolean canExplode() { return false; }

    @Override
    public void onExplode() { remove(); }
}