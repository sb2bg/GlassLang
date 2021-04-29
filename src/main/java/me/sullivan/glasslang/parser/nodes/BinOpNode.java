package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.types.Primitive;
import me.sullivan.glasslang.lexer.token.Token;

public class BinOpNode extends Node
{
    private Node left;
    private Node right;

    public BinOpNode(Node left, Token token, Node right)
    {
        super(token);
        this.left = left;
        this.right = right;
    }

    @Override
    public Token getValue()
    {
        return token;
    }

    @Override
    public String toString()
    {
        return "BinOp(" + left + ", " + getValue().getType() + ", " + right + ")";
    }

    public Node getLeft()
    {
        return left;
    }

    public Node getRight()
    {
        return right;
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitBinOp(this);
    }
}