package ua.foxminded.muzychenko.formulaone.exception;

import java.io.IOException;

public class DamagedFileException extends IOException {
    public DamagedFileException(String message) {
        super(message);
    }
}
