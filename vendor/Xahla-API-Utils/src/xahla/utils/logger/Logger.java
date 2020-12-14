package xahla.utils.logger;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logger class handles information that needs to be displayed on the console.<br>
 * It uses a simple concept of level severity to manage which information has to be displayed.
 * 
 * @author Cochetooo
 * @version 1.2
 */
public class Logger {
	
	/** Show timestamp during logs. */
	public static boolean timestamp = false;
	/** The stream that outputs the logs. */
	public static PrintStream printer = System.out;
	/** The maximum message level that the logger will display. All for everything, Off for nothing. */
	public static Level debugShow = Level.ALL;
	
	/** Log a severe message. 
	 * @param message The message to display. 
	 */
	public final static void eLog(Object message) 	{ log(Level.SEVERE, message); 	}
	/** Log a warning message. 
	 * @param message The message to display. 
	 */
	public final static void wLog(Object message) 	{ log(Level.WARNING, message); 	}
	/** Log an informative message. 
	 * @param message The message to display. 
	 */
	public final static void log(Object message) 	{ log(Level.INFO, message); 	}
	/** Log a message with an signifiance level.<br>
	 *  Some symbols will be replaced such as:<br>
	 *  <ul>
	 *  	<li><b>%dt</b> Will be shown as the today datetime.</li>
	 *  	<li><b>%hr</b> Will be shown as the current hour.</li>
	 *  </ul>
	 * @param level 	The message importance.
	 * @param message 	The message to display. 
	 */
	public final static void log(Level level, Object message) {
		if (debugShow.ordinal() < level.ordinal()) return;
		
		String text = message.toString();
		text = "[" + level.toString() + "] ".concat(text);
		if (timestamp)
			text = "%dt ".concat(text);
			
		text = text.replace("%dt", new SimpleDateFormat("<yyyy-MM-dd> [H:mm:ss]").format(new Date()));
		text = text.replace("%hr", new SimpleDateFormat("[H:mm:ss]").format(new Date()));
		
		printer.println(text);
	}

}
