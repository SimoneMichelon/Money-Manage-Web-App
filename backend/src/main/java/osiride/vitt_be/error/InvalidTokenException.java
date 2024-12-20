package osiride.vitt_be.error;

public class InvalidTokenException extends Exception {

	private static final long serialVersionUID = 5L;
	
	public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String message) {
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
