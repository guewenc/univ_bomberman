package fr.ubx.poo.ubomb.exception;

public class GameException extends GeneralException {
    public GameException(String error) {
        super("Game", error);
    }
}