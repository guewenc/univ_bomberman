package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.exception.GridException;

public enum EntityCode {
    Bomb('/'),
    BombBagCapacityDec('-'),
    BombBagCapacityInc('+'),
    BombRangeDec('<'),
    BombRangeInc('>'),
    Box('B'),
    DoorNextClosed('n'),
    DoorNextOpened('N'),
    DoorPrevOpened('V'),
    Empty('_'),
    Explosion('*'),
    Heart('H'),
    Key('K'),
    Monster('M'),
    Player('P'),
    Princess('W'),
    Stone('S'),
    Tree('T');

    private final char code;

    EntityCode(char code) {
        this.code = code;
    }

    public static EntityCode fromCode(char c) throws GridException {
        for (EntityCode entity : values()) {
            if (entity.getCode() == c)
                return entity;
        }
        throw new GridException("Invalid character " + c);
    }

    private char getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "" + code;
    }
}