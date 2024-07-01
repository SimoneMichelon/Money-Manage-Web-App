package osiride.vitt_be.error;

public class OperationNotPermittedException extends Exception {

	private static final long serialVersionUID = 5L;

	public OperationNotPermittedException() {
		super();
	}

	public OperationNotPermittedException(String message) {
		super(message);
	}

	public OperationNotPermittedException(String message, Throwable cause) {
		super(message, cause);
	}
}
