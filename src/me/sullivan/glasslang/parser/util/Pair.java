package me.sullivan.glasslang.parser.util;

import java.util.Iterator;

public class Pair<L, R> implements Iterable<Pair<L, R>> {

	private L left;
	private R right;
	
	public Pair(L left, R right)
	{
		this.left = left;
		this.right = right;
	}

	public L getLeft()
	{
		return left;
	}
	
	public R getRight()
	{
		return right;
	}

	@Override
	public Iterator<Pair<L, R>> iterator()
	{
		return null;
	}
}
