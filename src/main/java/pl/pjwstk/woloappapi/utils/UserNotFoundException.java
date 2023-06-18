package pl.pjwstk.woloappapi.utils;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
