package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.model.GameObject;
import fr.ubx.poo.ubomb.model.bonus.*;
import fr.ubx.poo.ubomb.model.character.Monster;
import fr.ubx.poo.ubomb.model.character.Princess;
import fr.ubx.poo.ubomb.model.decor.Stone;
import fr.ubx.poo.ubomb.model.decor.Tree;
import fr.ubx.poo.ubomb.model.doors.DoorNextClosed;
import fr.ubx.poo.ubomb.model.doors.DoorNextOpened;
import fr.ubx.poo.ubomb.model.doors.DoorPrevOpened;
import fr.ubx.poo.ubomb.model.others.Box;

public abstract class GridRepo {
    protected final Game game;

    GridRepo(Game game) {
        this.game = game;
    }

    public abstract Grid load(int level, String name) throws Exception;

    GameObject processEntityCode(EntityCode entityCode, Position pos) {
        switch (entityCode) {
            case Stone:
                return new Stone(pos);
            case Tree:
                return new Tree(pos);
            case Heart:
                return new Heart(pos);
            case Key:
                return new Key(pos);
            case BombRangeInc:
                return new BombRangeInc(pos);
            case BombRangeDec:
                return new BombRangeDec(pos);
            case BombBagCapacityInc:
                return new BombBagCapacityInc(pos);
            case BombBagCapacityDec:
                return new BombBagCapacityDec(pos);
            case DoorPrevOpened:
                return new DoorPrevOpened(pos);
            case DoorNextOpened:
                return new DoorNextOpened(pos);
            case DoorNextClosed:
                return new DoorNextClosed(pos);
            case Princess:
                return new Princess(game, pos);
            case Box:
                return new Box(game, pos);
            case Monster:
                return new Monster(game, pos);
            default:
                return null;
        }
    }
}