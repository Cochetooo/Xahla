package xahla.core;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Target;

/**
 * The Object Priority tells the API whenever an object (or component) 
 * must be called for update and rendering by adding the annotation above the class name.
 * 
 * @author Cochetooo
 * @version 1.0
 */
@Target(TYPE)
public @interface ObjectPriority {
	public Priority priority() default Priority.NORMAL;
}
