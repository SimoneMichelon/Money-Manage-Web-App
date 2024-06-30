package osiride.vitt_be.error;

public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = 3L;
	
	public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
