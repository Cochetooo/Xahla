package xahla.utils.logger;

/**
 * Level indicates the severity of a message.<br>
 * The more severe a level is, the less his ordinal value is. (Off as 0 and All as 8).
 * 
 * @author Cochetooo
 * @version 1.0
 */
public enum Level {
	
	/** OFF is a special level that can be used to turn off logging. */
	OFF,
	/** SEVERE is a message level indicating a serious failure. */
	SEVERE,
	/** WARNING is a message level indicating a potential problem. */
	WARNING,
	/** INFO is a message level for informational messages. */
	INFO,
	/** CONFIG is a message level for static configuration messages. */
	CONFIG,
	/** FINE is a message level providing tracing information. */
	FINE,
	/** FINER indicates a fairly detailed tracing message. */
	FINER,
	/** FINEST indicates a highly detailed tracing message. */
	FINEST,
	/** ALL indicates that all messages should be logged. */
	ALL;
	
}
