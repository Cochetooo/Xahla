package xahla.utils;

/**
 * <p>
 * This exception is thrown when a parameter value isn't what the Context has expected.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -45814339378896697L;
	
	/**
	 * <p>
	 * @see ValidationException
	 * @type Constructor
	 */
	public ValidationException() {
		this("An unexpected value has been given in parameter.");
	}
	
	/**
	 * <p>
	 * @param message The error message to print.
	 * @see ValidateException
	 * @type Constructor
	 */
	public ValidationException(String message) {
		super(message);
	}

}