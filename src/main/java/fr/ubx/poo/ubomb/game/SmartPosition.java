package fr.ubx.poo.ubomb.game;

import java.util.Random;

public abstract class SmartPosition {
    private static final int GO_TO_PLAYER_DIST_10 = 85;
    private static final int GO_TO_PLAYER_DIST_15 = 90;
    private static final int GO_TO_PLAYER_DIST_20 = 50;

    private static int getProbability(Position pos1, Position pos2) {
        int distance = pos1.distance(pos2);
        if(distance <= 5)
            return GO_TO_PLAYER_DIST_10;
        if(distance <= 10)
            return GO_TO_PLAYER_DIST_15;
        return GO_TO_PLAYER_DIST_20;
    }

    public static Direction getNextDirection(Position pos1, Position pos2) {
        if(pos1.getLevel() != pos2.getLevel())
            return Direction.random();

        Random random = new Random();
        int probabilityRandom = random.nextInt(99) + 1;
        int probabilityPosition = getProbability(pos1, pos2);

        if(pos1.getX() < pos2.getX())
            if(probabilityRandom <= probabilityPosition)
                return Direction.LEFT;

        if(pos1.getX() > pos2.getX())
            if(probabilityRandom <= probabilityPosition)
                return Direction.RIGHT;

        if(pos1.getY() < pos2.getY())
            if(probabilityRandom <= probabilityPosition)
                return Direction.UP;

        if(pos1.getY() > pos2.getY())
            if(probabilityRandom <= probabilityPosition)
                return Direction.DOWN;

        return Direction.random();
    }
}