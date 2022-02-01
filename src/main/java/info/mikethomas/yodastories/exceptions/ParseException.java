package info.mikethomas.yodastories.exceptions;

import java.io.IOException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParseException extends IOException {

    /**
     * Constructs an {@code ParseException} with the specified detail message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     */
    public ParseException(String message) {
        super(message);
    }
}
