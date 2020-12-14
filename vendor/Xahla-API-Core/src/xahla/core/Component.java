package xahla.core;

import java.lang.annotation.Annotation;

import xahla.utils.ValidationException;

/**
 * A Component is a object property of a XObject.<br>
 * Each XObject contains a collection of Component that you can manage.<br>
 * Each component contains extra properties that can be used in several XObject.<br><br>
 * 
 * A Component has a priority, that will be handled by the API whenever the collection is called.<br>
 * Higher priority components will be updated and rendered before lower priority ones.<br>
 * It is useful for pre-rendering or pre-update components such as Projections.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public abstract class Component implements IAppCore, Comparable<Component> {
	
	private String name;
	private int id;
	private static int auto_increment;
	
	private XObject object;
	
	/**
	 * Attach a new component to the XObject.<br>
	 * The ID of this component will be determined by auto increment.
	 * 
	 * @param object	The XObject to work for.
	 * @param name		The name of the component.
	 */
	public Component(XObject object, String name) {
		this.name = name;
		this.id = (auto_increment += 1);
		
		if (this.getPriority().priority > object.getPriority().priority)
			throw new ValidationException("Not Enough priority for object: " + object.getName() + " as component: " + name);
		
		this.object = object;
	}
	
	/** Compares the priority of the two components. */
	@Override
	public int compareTo(Component object) {
		return this.getPriority().priority - object.getPriority().priority;
	}
	
	/** @return The priority of the component. */
	public Priority getPriority() {
		Annotation[] annotations = this.getClass().getAnnotations();
		for (Annotation a : annotations) {
			if (a instanceof ObjectPriority op)
				return op.priority();
		}
		
		return Priority.NORMAL;
	}
	
	/** @return The name of the component. */
	public String getName() { return name; }
	/** @return The ID of the component. */
	public int getID() { return id; }
	
	/** @return The XObject that is attached to this component. */
	protected XObject getObjectToWorkFor() { return object; }
	/** @return The Context of the XObject that is attached to this component. */
	protected Context getContext() { return this.getObjectToWorkFor().getContext(); }

}
