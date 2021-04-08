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

			return new StringPrimitive(doubleToString(this.value) + value.getValue(), context);
		}

		throw new RuntimeError("Right hand of operation '+' must be number or string");
	}

	@Override
	public NumberPrimitive min(Primitive<?> other)
	{
		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(this.value - value.getValue(), context);
	}

	@Override
	public NumberPrimitive div(Primitive<?> other)
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
		if (other.type == Type.NUMBER)
		{
			NumberPrimitive value = other.getValue(Type.NUMBER);
			return new NumberPrimitive(this.value * value.getValue(), context);
		}
		else if (other.type == Type.STRING)
		{
			StringPrimitive value = other.getValue(Type.STRING);
			return new StringPrimitive(StringPrimitive.multiply(value.getValue(), this.value), context);
		}
		
		throw new RuntimeError("Right hand of operation '*' must be a number or string");
	}

	@Override
	public Primitive<?> mod(Primitive<?> other)
	{
		if (other.type == Type.NUMBER)
		{
			NumberPrimitive value = other.getValue(Type.NUMBER);
			return new NumberPrimitive(this.value % value.getValue(), context);
		}

		throw new RuntimeError("Right hand of operation '%' must be a number");
	}

	@Override
	public NumberPrimitive pow(Primitive<?> other)
	{
		if (other.type != Type.NUMBER)
		{
			throw new RuntimeError("Right hand of operation '**' must be a number");
		}

		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new NumberPrimitive(Math.pow(this.value, value.getValue()), context);
	}

	@Override
	public BooleanPrimitive less(Primitive<?> other)
	{
		if (other.type != Type.NUMBER)
		{
			throw new RuntimeError("Right hand of operation '<' must be a number");
		}

		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new BooleanPrimitive(this.value < value.getValue(), context);
	}

	@Override
	public BooleanPrimitive lessEqual(Primitive<?> other)
	{
		if (other.type != Type.NUMBER)
		{
			throw new RuntimeError("Right hand of operation '<=' must be a number");
		}

		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new BooleanPrimitive(this.value <= value.getValue(), context);
	}

	@Override
	public BooleanPrimitive greater(Primitive<?> other)
	{
		if (other.type != Type.NUMBER)
		{
			throw new RuntimeError("Right hand of operation '>' must be a number");
		}

		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new BooleanPrimitive(this.value > value.getValue(), context);
	}

	@Override
	public BooleanPrimitive greaterEqual(Primitive<?> other)
	{
		if (other.type != Type.NUMBER)
		{
			throw new RuntimeError("Right hand of operation '>=' must be a number");
		}

		NumberPrimitive value = other.getValue(Type.NUMBER);
		return new BooleanPrimitive(this.value >= value.getValue(), context);
	}

	@Override
	public BooleanPrimitive notEqual(Primitive<?> other)
	{
		return equal(other).not();
	}

	public static String doubleToString(Double value)
	{
		String stringVal = value.toString();
		return value % 1 == 0 ? stringVal.substring(0, stringVal.length() - 2) : stringVal;
	}
}
