package me.sullivan.glasslang.interpreter.runtime.tables;

import me.sullivan.glasslang.interpreter.primitives.types.Primitive;

public class VariableTable extends Table<VariableTable, Primitive<?>>
{
    public VariableTable()
    {
        super();
    }

    public VariableTable(VariableTable table)
    {
        super(table);
    }

    @Override
    public String toString()
    {
        return "VariableTable(" + super.table + ")";
    }
}