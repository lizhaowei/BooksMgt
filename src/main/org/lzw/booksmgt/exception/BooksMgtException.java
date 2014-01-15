package org.lzw.booksmgt.exception;

/**
 * created by zwli on 2014-1-10 Detailed comment
 */
public class BooksMgtException extends Exception {

    private static final long serialVersionUID = -5982832685544286274L;

    public BooksMgtException() {
        super();
    }

    public BooksMgtException(String message, Throwable cause) {
        super(message, cause);
    }

    public BooksMgtException(String message) {
        super(message);
    }

    public BooksMgtException(Throwable cause) {
        super(cause);
    }
}
