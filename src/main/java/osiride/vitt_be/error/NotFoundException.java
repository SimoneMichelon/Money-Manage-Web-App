package osiride.vitt_be.error;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 4L;
	
	public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
