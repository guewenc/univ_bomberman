package fr.ubx.poo.ubomb.exception;

public class GeneralException extends Exception {
    protected GeneralException(String location, String error) {
        super("[Error on \"" + location + "\"] : " + error);
    }
}