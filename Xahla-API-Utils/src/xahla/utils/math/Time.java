package xahla.utils.math;

/**
 * <p>
 * The Time class allows a simple handling of time
 * operations. It uses hours, minutes and seconds.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class Time {
	
	private int seconds, minutes, hours;
	
	public Time() { this(0); }
	public Time(int seconds) { this(0, seconds); }
	public Time(int minutes, int seconds) { this (0, minutes, seconds); }
	/**
	 * Initializes the time as HH:MM:SS
	 * @param hours 	The hours.
	 * @param minutes 	The minutes.
	 * @param seconds 	The seconds.
	 * @see Time
	 */
	public Time(int hours, int minutes, int seconds) { 
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
		
		add(hours, minutes, seconds);
	}
	
	/**
	 * Add time to the current time.
	 * @param hours 	The hours.
	 * @param minutes 	The minutes.
	 * @param seconds 	The seconds.
	 * @return			The new time.
	 */
	public Time add(int hours, int minutes, int seconds) {
		if (this.seconds + seconds >= 60) minutes++;
		if (this.minutes + minutes >= 60) hours++;
		
		this.hours += hours;
		this.minutes = (this.minutes + minutes) % 60;
		this.seconds = (this.seconds + seconds) % 60;
		
		return this;
	}
	
	/** Add one second to the current time. */
	public void tick() { add(0, 0, 1); }
	
	/** @return Returns a representation of the time as H:MM:SS. */
	@Override
	public String toString() {
		return hours + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
	}
	
	/** @return The current number of hours. */
	public int getHours() { return hours; }
	/** @return The current number of minutes. */
	public int getMinutes() { return minutes; }
	/** @return The current number of seconds. */
	public int getSeconds() { return seconds; }
	
	/** @return The total number of seconds. */
	public int getTotalSeconds() {
		return seconds + minutes * 60 + hours * 3600;
	}

}
