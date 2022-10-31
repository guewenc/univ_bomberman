package fr.ubx.poo.ubomb.exception;

public class GridException extends GeneralException {
    public GridException(String error) {
        super("Grid", error);
    }
}