package fr.ubx.poo.ubomb.model;

import fr.ubx.poo.ubomb.game.Direction;

public interface Movable {
    boolean canMove(Direction direction);
    void doMove(Direction direction);
}