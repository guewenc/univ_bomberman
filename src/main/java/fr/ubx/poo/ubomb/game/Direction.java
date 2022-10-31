package fr.ubx.poo.ubomb.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Direction {
    DOWN {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.getLevel(), pos.getX(), pos.getY() + delta);
        }
    },
    LEFT {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.getLevel(), pos.getX() - delta, pos.getY());
        }
    },
    RIGHT {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.getLevel(), pos.getX() + delta, pos.getY());
        }
    },
    UP {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.getLevel(), pos.getX(), pos.getY() - delta);
        }
    },
    NONE {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return pos;
        }
    },
    ;

    private static final Random randomGenerator = new Random();

    public static Direction random() {
        int i = randomGenerator.nextInt(values().length);
        return values()[i];
    }

    public abstract Position nextPosition(Position pos, int delta);

    final public Position nextPosition(Position pos) {
        return nextPosition(pos, 1);
    }

    public static List<Direction> getDirections() {
        List<Direction> allDirections = new ArrayList<>(Arrays.asList(values()));
        allDirections.remove(NONE);
        return allDirections;
    }
}