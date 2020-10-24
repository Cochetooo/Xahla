package xahla.core;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An XObject is an element of the context that has a init / update logic.<br>
 * It owns a unique name and a unique identifier.
 * 
 * @author Cochetooo
 * @version 1.2
 */
public abstract class XObject implements IAppCore, Comparable<XObject> {
	
	private List<Component> components;
	
	private String name;
	private int id;
	private static int auto_increment = 0;
	
	private boolean destroyed;
	private Context Context;
	
	/**
	 * Append a XObject to the Context.<br>
	 * Its ID will be defined by an inner auto increment.
	 * 
	 * @param name		The name of the XObject.
	 * @param Context	The context where the XObject will be added.
	 */
	public XObject(String name, Context Context) {
		this.name = name;
		this.id = (auto_increment += 1);
		this.Context = Context;
		
		this.destroyed = false;
		
		this.components = new ArrayList<>();
	}
	
	/** Remove the object. */
	public void destroy() {
		this.destroyed = true;
	}
	
	protected void add(Component newComponent) {
		this.components.add(newComponent);
		Collections.sort(components);
	}
	
	protected void set(String name, Component newComponent) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).getName().equals(name)) {
				components.set(i, newComponent);
				return;
			}
		}
	}
	
	/** Compare the priority of the two objects. */
	@Override
	public int compareTo(XObject obj) {
		return this.getPriority().priority - obj.getPriority().priority;
	}

	@Override public void awake() { components.forEach((c)->c.awake()); }
	@Override public void init() { components.forEach((c)->c.init()); }
	@Override public void update() { components.forEach((c)->c.update()); }
	@Override public void post_update() { components.forEach((c)->c.post_update()); }
	@Override public void pre_render() { components.forEach((c)->c.pre_render()); }
	@Override public void client_update() { components.forEach((c)->c.client_update()); }
	@Override public void server_update() { components.forEach((c)->c.server_update()); }
	@Override public void render() { components.forEach((c)->c.render()); }
	@Override public void second() { components.forEach((c)->c.second()); }
	@Override public void dispose() { components.forEach((c)->c.dispose()); }
	
	/** @return The priority of the XObject. */
	public Priority getPriority() {
		Annotation[] annotations = this.getClass().getAnnotations();
		for (Annotation a : annotations) {
			if (a instanceof ObjectPriority op)
				return op.priority();
		}
		
		return Priority.NORMAL;
	}
	
	/**
	 * @param name	The name of the component.
	 * @return		The component with the given name.
	 */
	public Component getComponent(String name) {
		for (Component c : components)
			if (name.equals(c.getName())) return c;
		
		return null;
	}
	
	/**
	 * @param id	The ID of the component.
	 * @return		The component with the given ID.
	 */
	public Component getComponent(int id) {
		for (Component c : components)
			if (id == c.getID()) return c;
		
		return null;
	}
	
	/** @return	True if the object has been destroyed. */
	public boolean isDestroyed() { return destroyed; }
	/** @return The name of the object. */
	public String getName() { return name; }
	/** @return The ID of the object. */
	public int getID() { return id; }
	
	/** @return The context attached to the object. */
	public Context getContext() { return Context; }

}
