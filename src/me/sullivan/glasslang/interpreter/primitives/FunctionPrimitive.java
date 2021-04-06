package me.sullivan.glasslang.interpreter.primitives;

import java.text.MessageFormat;
import java.util.List;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.errors.RuntimeError;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.interpreter.runtime.tables.VariableTable;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.nodes.Node;

public class FunctionPrimitive extends Primitive<Node> {

	private String name;
	private List<Token> args;
	
	public FunctionPrimitive(String name, Node body, List<Token> args, Context context)
	{
		super(body, Type.FUNCTION, context);
		this.name = name;
		this.args = args;
	}
	
	@Override
	public Primitive<?> call(List<Node> argNodes)
	{
		Interpreter interpreter = new Interpreter(new Context(context, MessageFormat.format("func<{0}>", name), new VariableTable(context.getTable())));
		
		int passed = args.size();
		int expected = argNodes.size();
		
		if (passed != expected)
		{
			throw new RuntimeError(Math.abs(passed - expected) + " too " + (passed > expected ? "few " : "many ") + "arguments were passed in");
		}
		
		for (int i = 0; i < args.size(); i++)
		{
			Token argName = args.get(i);
			Node argValue = argNodes.get(i);
			interpreter.currentContext().getTable().set(argName.getValue(), interpreter.visitNode(argValue));
		}
		
		return interpreter.visitNode(value);
	}
}
