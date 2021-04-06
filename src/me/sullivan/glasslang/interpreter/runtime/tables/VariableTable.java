package me.sullivan.glasslang.interpreter.runtime.tables;

import me.sullivan.glasslang.interpreter.primitives.Primitive;

public class VariableTable extends Table<VariableTable, Primitive<?>> {

	public VariableTable() 
	{
		super();
	}
	
	public VariableTable(VariableTable table)
	{
		super(table);
	}
}