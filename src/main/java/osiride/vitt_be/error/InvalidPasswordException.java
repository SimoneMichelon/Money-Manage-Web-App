package osiride.vitt_be.error;

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = 4L;
	
	public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String message) {
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
