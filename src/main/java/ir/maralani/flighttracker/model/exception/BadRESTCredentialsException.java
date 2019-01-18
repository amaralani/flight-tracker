package ir.maralani.flighttracker.model.exception;

public class BadRESTCredentialsException extends Exception {
    public BadRESTCredentialsException(String message) {
        super(message);
    }

    public BadRESTCredentialsException() {
    }
}
