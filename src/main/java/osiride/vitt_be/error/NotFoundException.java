package osiride.vitt_be.error;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2L;
	
	public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
