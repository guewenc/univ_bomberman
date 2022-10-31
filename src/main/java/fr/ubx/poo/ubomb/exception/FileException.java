package fr.ubx.poo.ubomb.exception;

public class FileException extends GeneralException {
    public FileException(String error) {
        super("File", error);
    }
}