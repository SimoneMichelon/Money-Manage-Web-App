package osiride.vitt_be.error;

public class DuplicatedValueException extends Exception {

	private static final long serialVersionUID = 2L;
	
	public DuplicatedValueException() {
        super();
    }

    public DuplicatedValueException(String message) {
        super(message);
    }

    public DuplicatedValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
