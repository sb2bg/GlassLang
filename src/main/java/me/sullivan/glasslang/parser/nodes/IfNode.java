package me.sullivan.glasslang.parser.nodes;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;
import org.javatuples.Pair;

public class IfNode extends Node {

    private Pair<Node, Node> ifCase;
    private Node elseCase;

    public IfNode(Token token, Pair<Node, Node> ifCase, Node elseCase)
    {
        super(token);
        this.ifCase = ifCase;
        this.elseCase = elseCase;
    }

    @Override
    public Pair<Node, Node> getValue()
    {
        return ifCase;
    }

    public Node getElseCase()
    {
        return elseCase;
    }

    @Override
    public String toString()
    {
        return "If(" + ifCase + ") else => " + elseCase;
    }

    @Override
    public Primitive<?> visitor(Interpreter interpreter)
    {
        return interpreter.visitIfNode(this);
    }
}