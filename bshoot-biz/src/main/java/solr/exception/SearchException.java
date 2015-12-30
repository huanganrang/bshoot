package solr.exception;

/**
 * Created by zhou on 2015/12/29.
 */
public class SearchException extends  RuntimeException{
    private static final long serialVersionUID = 3583566093089790852L;

    public SearchException() {
        super();
    }

    public SearchException(String message) {
        super(message);
    }

    public SearchException(Throwable cause) {
        super(cause);
    }

    public SearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
