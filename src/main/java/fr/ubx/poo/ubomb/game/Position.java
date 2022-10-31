package fr.ubx.poo.ubomb.game;

import java.util.Objects;

public class Position {
    private final int level;
    private final int x;
    private final int y;

    public Position(int level, int x, int y) {
        this.level = level;
        this.x = x;
        this.y = y;
    }

    public Position(int level, Position position) {
        this(level, position.x, position.y);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getLevel() {
        return level;
    }

    // Manhattan distance
    public int distance(Position position) {
        return Math.abs(position.getX() - getX()) + Math.abs(position.getY() - getY());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof Position) {
            Position position = (Position) o;
            return position.x == this.x && position.y == this.y && this.level == position.level;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, x, y);
    }

    @Override
    public String toString() {
        return "[level " + level + "] (" + x + "," + y + ")";
    }
}