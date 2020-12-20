package xahla.core;

/**
 * The Object Priority tells the API whenever an object (or component) 
 * must be called for update and rendering.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public enum Priority {
	
	LOWEST(-2), LOW(-1), NORMAL(0), HIGH(1), HIGHEST(2);
	
	public final int priority;
	
	private Priority(int prio) {
		this.priority = prio;
	}
	
}