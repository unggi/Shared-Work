package soemardjo;


public class TradeIdMismatchException extends RuntimeException {

    public TradeIdMismatchException() {
        super();
    }

    public TradeIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TradeIdMismatchException(final String message) {
        super(message);
    }

    public TradeIdMismatchException(final Throwable cause) {
        super(cause);
    }
}