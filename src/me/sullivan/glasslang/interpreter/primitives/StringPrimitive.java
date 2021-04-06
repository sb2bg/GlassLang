package me.sullivan.glasslang.interpreter.primitives;

import java.util.List;

import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.parser.nodes.Node;

public class StringPrimitive extends Primitive<String> {

	public StringPrimitive(String value, Context context)
	{
		super(value, Type.STRING, context);
	}

	@Override
	public Primitive<?> add(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> min(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> div(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> mul(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> pow(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> less(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> lessEqual(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> greater(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> greaterEqual(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> equal(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> notEqual(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> and(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> or(Primitive<?> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Primitive<?> not() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Primitive<?> call(List<Node> argNodes)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
