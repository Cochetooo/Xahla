package xahla.utils.math;

/**
 * This class is used to compare elapsed time as nanoseconds.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class Timer {
	
	private long startTime;
	
	/**
	 * Reset the timer.
	 * @see Timer
	 */
	public Timer() {
		reset();
	}
	
	/** Set the timer at the current nanosecond of the system. */
	public void reset() {
		startTime = System.nanoTime();
	}
	
	/** @return The elapsed time between the creation of the timer and the current time (in nanoseconds). */
	public long elapsed_time() {
		return System.nanoTime() - startTime;
	}

}
