package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.exception.FileException;
import fr.ubx.poo.ubomb.exception.GameException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameConfig {
    private final String levelPrefix;
    private final int levels;
    private final int initBombBagCapacity;
    private final int initBombRange;

    private final int initPlayerLives;
    private final long playerInvisibilityTime;
    private final Position initPlayerPosition;

    private final long monsterInvisibilityTime;
    private final int monsterVelocity;
    private final int monsterLives;

    private final long timeBeforeBombExplode;
    private final long timeBeforeExplosionRemove;

    public GameConfig(String worldPath) {
        try (InputStream input = new FileInputStream(new File(worldPath, "config.properties"))) {
            Properties prop = new Properties();

            prop.load(input);

            /* Game */
            levelPrefix = prop.getProperty("prefix");
            levels = Integer.parseInt(prop.getProperty("levels", "1"));
            initBombBagCapacity = Integer.parseInt(prop.getProperty("bombBagCapacity", "1"));
            initBombRange = Integer.parseInt(prop.getProperty("bombRange", "1"));

            /* Player */
            initPlayerLives = Integer.parseInt(prop.getProperty("playerLives", "1"));
            playerInvisibilityTime = toMilliseconds(Double.parseDouble(prop.getProperty("playerInvisibilityTime", "1")));

            String[] tokens = prop.getProperty("player").split("[ :x]+");
            if (tokens.length != 2)
                throw new FileException("Invalid configuration format");
            initPlayerPosition = new Position(1, Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));

            /* Monsters */
            monsterInvisibilityTime = toMilliseconds(Double.parseDouble(prop.getProperty("monsterInvisibilityTime", "4")));
            monsterVelocity = Integer.parseInt(prop.getProperty("monsterVelocity", "10"));
            monsterLives = Integer.parseInt(prop.getProperty("monsterLives", "2"));

            /* Bombs */
            timeBeforeBombExplode = toMilliseconds(Double.parseDouble(prop.getProperty("timeBeforeBombExplode", "4")));
            timeBeforeExplosionRemove = toMilliseconds(Double.parseDouble(prop.getProperty("timeBeforeExplosionRemove", "1")));

            /* Exceptions */
            if(levels < 1 || levels > 9)
                throw new FileException("Levels must be >= 1 and <= 9");

            if(initPlayerLives < 1)
                throw new FileException("playerLives must be >= 1");

        } catch (IOException | FileException e) {
            throw new RuntimeException("Invalid configuration format : " + e.getMessage());
        }
    }

    private long toMilliseconds(double val) {
        return (long) (val * 1000);
    }

    public String getLevelPrefix() {
        return levelPrefix;
    }
    public int getLevels() {
        return levels;
    }
    public int getInitBombBagCapacity() {
        return initBombBagCapacity;
    }
    public int getInitBombRange() {
        return initBombRange;
    }

    public int getInitPlayerLives() {
        return initPlayerLives;
    }
    public Position getInitPlayerPosition() {
        return initPlayerPosition;
    }
    public long getPlayerInvisibilityTime() {
        return playerInvisibilityTime;
    }

    public long getMonsterInvisibilityTime() {
        return monsterInvisibilityTime;
    }
    public int getMonsterVelocity() {
        return monsterVelocity;
    }
    public int getMonsterLives() {
        return monsterLives;
    }

    public long getTimeBeforeBombExplode() {
        return timeBeforeBombExplode;
    }
    public long getTimeBeforeExplosionRemove() {
        return timeBeforeExplosionRemove;
    }
}