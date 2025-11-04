package me.wilhelm.boardgames.games;

public class InvalidPlayerCountException extends RuntimeException {
    public InvalidPlayerCountException(String message) {
        super(message);
    }
}
