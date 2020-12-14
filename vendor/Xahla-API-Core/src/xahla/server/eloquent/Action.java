package xahla.server.eloquent;

public enum Action {
	
	CREATE("edit"),
	UPDATE("edit"),
	STORE("store"),
	INDEX("index"),
	SHOW("show"),
	DELETE("delete");
	
	private final String action;
	
	private Action(String act) {
		this.action = act;
	}
	
	public String getName() { return this.action; }

}
