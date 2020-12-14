package xahla.server.eloquent;

/**
 * The CRUD interface is the generic way to use resource for database.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public interface CRUD {
	
	/** Display a listing of the resource. */
	void index();
	
	/** Show the form for creating a new resource. */
	void create();
	
	/** Store a newly created resource in storage. */
	void store();
	
	/** Display the specified resource. */
	void show(Model model);
	
	/** Show the form for editing the specified resource. */
	void edit(Model model);
	
	/** Update the specified resource in storage. */
	void update(Model model);
	
	/** Remove the specified resource from the storage. */
	void destroy(Model model);

}
