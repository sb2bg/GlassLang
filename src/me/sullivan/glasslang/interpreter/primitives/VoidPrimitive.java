package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;

public class VoidPrimitive extends Primitive<Void> {

	public VoidPrimitive()
	{
		super(null, Type.VOID, null);
	}
}
