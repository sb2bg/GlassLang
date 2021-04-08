package me.sullivan.glasslang.interpreter.primitives;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.interpreter.runtime.tables.VariableTable;
import me.sullivan.glasslang.parser.nodes.Node;

import java.text.MessageFormat;
import java.util.List;

public class StringPrimitive extends Primitive<String> {

	public StringPrimitive(String value, Context context)
	{
		super(value, Type.STRING, context);
	}

	@Override
	public Primitive<?> add(Primitive<?> other)
	{
		if (other.type == Type.STRING)
		{
			StringPrimitive value = other.getValue(Type.STRING);
			return new StringPrimitive(this.value + value.getValue(), context);
		}
		else if (other.type == Type.NUMBER)
		{
			NumberPrimitive value = other.getValue(Type.NUMBER);
			return new StringPrimitive(this.value + NumberPrimitive.doubleToString(value.getValue()), context);
		}
		else if (other.type == Type.BOOLEAN)
		{
			BooleanPrimitive value = other.getValue(Type.BOOLEAN);
			return new StringPrimitive(this.value + value.getValue(), context);
		}
		
		throw new RuntimeError("Cannot concatenate " + getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
	}
	
	@Override
	public Primitive<?> mul(Primitive<?> other)
	{
		if (other.type == Type.NUMBER)
		{
			NumberPrimitive multi = other.getValue(Type.NUMBER);
			
			return new StringPrimitive(multiply(this.value, multi.getValue()), context);
		}
		
		throw new RuntimeError("Right hand of operation '*' must be a number");
	}
	
	public static String multiply(String string, double iterations)
	{
		StringBuilder value = new StringBuilder();
		
		for (int i = 0; i < iterations; i++)
		{
			value.append(string);
		}
		
		return value.toString();
	}

	@Override
	public Primitive<?> call(List<Node> argNodes)
	{
		if (argNodes.size() > 2 || 1 > argNodes.size())
		{
			throw new RuntimeError("Expected one argument");
		}

		Interpreter interpreter = new Interpreter(new Context(context, MessageFormat.format("func<{0}>", "get-string.index"), new VariableTable(context.getTable())));
		Primitive<?> start = interpreter.visitNode(argNodes.get(0));
		Primitive<?> end;

		if (start.getType() != Type.NUMBER)
		{
			throw new RuntimeError("Expected number, not " + start.getType());
		}

		NumberPrimitive indexStart = start.getValue(Type.NUMBER);
		int valueStart = indexStart.getValue().intValue();
		end = new NumberPrimitive(valueStart + 1, context);

		if (argNodes.size() > 1)
		{
			end = interpreter.visitNode(argNodes.get(1));

			if (end.getType() != Type.NUMBER)
			{
				throw new RuntimeError("Expected number, not " + end.getType());
			}
		}

		NumberPrimitive indexEnd = end.getValue(Type.NUMBER);
		int valueEnd = indexEnd.getValue().intValue();

		if (valueStart >= this.value.length() || valueStart < 0 || valueEnd > this.value.length() || valueEnd < valueStart)
		{
			throw new RuntimeError(MessageFormat.format(valueStart > valueEnd ? "Start index {0} must be less than end index {1}" : "Start index {0} or end index {1} out of bounds for range {2}.", valueStart, valueEnd, this.value.length()));
		}

		return new StringPrimitive(this.value.substring(valueStart, valueEnd), context);
	}
}
