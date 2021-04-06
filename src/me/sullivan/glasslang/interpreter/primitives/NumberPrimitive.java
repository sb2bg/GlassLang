package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;

public class NumberPrimitive extends Primitive<Double> {

	public NumberPrimitive(double value, Context context)
	{
		super(value, Type.NUMBER, context);
	}

	@Override
	public Primitive<?> add(Primitive<?> other)
	{
		if (other.type == Type.NUMBER)
		{
			NumberPrimitive value = other.getValue(Type.NUMBER);
			return new NumberPrimitive(this.value + value.getValue(), context);
		}
		else if (other.type == Type.STRING)
		{
			StringPrimitive value = other.getValue(Type.STRING);
			return new StringPrimitive(this.value + value.getValue(), context);
		}
		
		throw new RuntimeError("Cannot add " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}

	@Override
	public Primitive<?> min(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value - value.getValue(), context);
	}

	@Override
	public Primitive<?> div(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);

		if (value.getValue() == 0)
		{
			throw new RuntimeError("Division by 0");
		}

		return new NumberPrimitive(this.value / value.getValue(), context);
	}

	@Override
	public Primitive<?> mul(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value * value.getValue(), context);
	}

	@Override
	public Primitive<?> pow(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(Math.pow(this.value, value.getValue()), context);
	}

	@Override
	public Primitive<?> less(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value < value.getValue() ? 1 : 0, context);
	}

	@Override
	public Primitive<?> lessEqual(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value <= value.getValue() ? 1 : 0, context);
	}

	@Override
	public Primitive<?> greater(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value > value.getValue() ? 1 : 0, context);
	}

	@Override
	public Primitive<?> greaterEqual(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value >= value.getValue() ? 1 : 0, context);
	}

	@Override
	public Primitive<?> equal(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value.equals(value.getValue()) ? 1 : 0, context);
	}

	@Override
	public Primitive<?> notEqual(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value.equals(value.getValue()) ? 0 : 1, context);
	}

	@Override
	public Primitive<?> and(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value == 0 ? 0 : value.getValue() == 0 ? 0 : value.getValue(), context);
	}

	@Override
	public Primitive<?> or(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value != 0 ? this.value : value.getValue() != 0 ? value.getValue() : 0, context);
	}

	@Override
	public Primitive<?> not()
	{
		return new NumberPrimitive(value == 0 ? 1 : 0, context);
	}

	@Override
	public boolean isTrue()
	{
		return value != 0;
	}
}
