package soemardjo;

public class TradeNotFoundException extends RuntimeException {

    public TradeNotFoundException() {
        super();
    }

    public TradeNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TradeNotFoundException(final String message) {
        super(message);
    }

    public TradeNotFoundException(final Throwable cause) {
        super(cause);
    }
}