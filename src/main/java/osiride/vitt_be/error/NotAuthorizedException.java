package osiride.vitt_be.error;

public class NotAuthorizedException extends Exception {

	private static final long serialVersionUID = 6L;
	
	public NotAuthorizedException() {
        super();
    }

    public NotAuthorizedException(String message) {
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
