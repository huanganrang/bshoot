package component.redis.exception;

/**
 * Created by zhou on 2015/12/29.
 */
public class CounterException extends  RuntimeException{
    private static final long serialVersionUID = 3583566093089790852L;

    public CounterException() {
        super();
    }

    public CounterException(String message) {
        super(message);
    }

    public CounterException(Throwable cause) {
        super(cause);
    }

    public CounterException(String message, Throwable cause) {
        super(message, cause);
    }
}
