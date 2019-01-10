/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.mikethomas.yodastories.exceptions;

import java.io.IOException;

/**
 *
 * @author Mike
 */
public class ParseException extends IOException {

    /**
     * Constructs an {@code IOException} with {@code null}
     * as its error detail message.
     */
    public ParseException() {
        super();
    }

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
