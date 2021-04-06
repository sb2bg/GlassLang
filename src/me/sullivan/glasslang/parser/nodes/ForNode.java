package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public class ForNode extends Node {

	private Node startValue;
	private Node endValue;
	private Node step;
	private Node eval;
	
	public ForNode(Token token, Node startValue, Node endValue, Node step, Node eval)
	{
		super(token);
		this.startValue = startValue;
		this.endValue = endValue;
		this.step = step;
		this.eval = eval;
	}

	@Override
	public String getValue()
	{
		return token.getValue();
	}

	@Override
	public String toString()
	{
		return "For";
	}

	@Override
	public Primitive<?> visitor(Interpreter interpreter)
	{
		return interpreter.visitForNode(this);
	}

	public Node getStartValue()
	{
		return startValue;
	}

	public Node getEndValue()
	{
		return endValue;
	}

	public Node getStep()
	{
		return step;
	}

	public Node getEval()
	{
		return eval;
	}
}
