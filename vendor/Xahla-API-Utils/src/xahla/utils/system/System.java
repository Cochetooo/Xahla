package xahla.utils.system;

import java.util.Date;

/**
 * This class contains several system utils functions.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class System {
	
	private static final Date today = new Date();
	private static String OS = java.lang.System.getProperty("os.name").toLowerCase();
	
	/** @return The date of today. */
	public static Date today() { return today; }
	
	/** @return True if the operating system is Windows. */
	public static boolean isWindows() { return OS.contains("win"); }
	
	/** @return True if the operating system is Mac. */
	public static boolean isMac() { return OS.contains("mac"); }
	
	/** @return True if the operating system is Unix. */
	public static boolean isUnix() { return OS.contains("nix") || OS.contains("nux") || OS.contains("aix"); }
	
	/** @return True if the operating system is Solaris. */
	public static boolean isSolaris() { return OS.contains("sunos"); }
	
	/** @return The current amount of memory used by the Context. */
	public static double getMemoryUsage() { return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024; }
	
	/** @return The number of available core of the CPU. */
	public static int getAvailableProcessors() { return Runtime.getRuntime().availableProcessors(); }

}
