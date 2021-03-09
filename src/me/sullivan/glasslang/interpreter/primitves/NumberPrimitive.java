package me.sullivan.glasslang.interpreter.primitves;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;

public class NumberPrimitive extends Primitive<Double> {

	public NumberPrimitive(double value)
	{
		super(value);
	}
	
	public NumberPrimitive add(NumberPrimitive other)
	{
		return new NumberPrimitive(value + other.getValue());
	}
	
	public NumberPrimitive min(NumberPrimitive other)
	{
		return new NumberPrimitive(value - other.getValue());
	}
	
	public NumberPrimitive div(NumberPrimitive other)
	{
		if (other.getValue() == 0)
		{
			throw new RuntimeError("Division by 0");
		}
		
		return new NumberPrimitive(value / other.getValue());
	}
	
	public NumberPrimitive mul(NumberPrimitive other)
	{
		return new NumberPrimitive(value * other.getValue());
	}
	
	public double getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(value);
	}
}
