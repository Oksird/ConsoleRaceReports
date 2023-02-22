package ua.foxminded.muzychenko.formulaone.exception;

import java.net.URISyntaxException;

public class WrongPathException extends URISyntaxException {
    public WrongPathException(String input, String reason) {
        super(input, reason);
    }
}
