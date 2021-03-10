package me.sullivan.glasslang.interpreter.runtime;

public class Context {

	private Context parent;
	private String context;
	private VariableTable varTable;
	
	public Context(Context parent, String context, VariableTable varTable)
	{
		this.parent = parent;
		this.context = context;
		this.varTable = varTable;
	}
	
	public Context getParent()
	{
		return parent;
	}

	public String getContext()
	{
		return context;
	}
	
	public VariableTable getTable()
	{
		return varTable;
	}
}
