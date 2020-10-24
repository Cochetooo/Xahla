package xahla.core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import xahla.utils.logger.Logger;
import xahla.utils.system.System;

/**
 * This class handles exception throughout the API and the Context.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class ExceptionHandler {
	
	private Context Context;
	
	/**
	 * @param Context	The context that must handle the exception.
	 */
	public ExceptionHandler(Context Context) {
		this.Context = Context;
	}
	
	/**
	 * Report an exception with no additional message and no log file.
	 * @param e	The Exception.
	 */
	public void reportException(Exception e) { reportException(e, ""); }
	
	/**
	 * Report an exception with no log file.
	 * @param e			The Exception.
	 * @param message	Additional Message in the debug output.
	 */
	public void reportException(Exception e, String message) { reportException(e, message, false); }
	
	/**
	 * @param e				The Exception.
	 * @param message		Additional Message in the output.
	 * @param stackTrace	True if a log file need to be created with the report in it.
	 */
	public void reportException(Exception e, String message, boolean stackTrace) {
		String log = String.format("""
				An error has been reported:
				------------ %1$s ------------
				Exception type: %2$s
				Exception Localized Message: %3$s
				Source: %4$s
				Additional Message: %5$s
				--------------------------------------------------
				""", 
				System.today().toInstant().toString(), 
				e.getClass().getSimpleName(),
				e.getLocalizedMessage(),
				e.getCause() == null ? "Unknown Source" : e.getCause().getMessage(),
				message.isBlank() ? "No additional information." : message		
			);
		
		if (stackTrace) {
			try (PrintWriter writer = new PrintWriter("hs-error-" + System.today().toString() + ".log", "UTF-8")) {
				writer.println(log);
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				reportException(e1, "Unable to write error log file.");
			}
		}
		
		Logger.eLog(log);
		
		Context.request("dispose");
	}
	
}
