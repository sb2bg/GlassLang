package me.sullivan.glasslang.interpreter;

import me.sullivan.glasslang.interpreter.primitives.types.NumberPrimitive;
import me.sullivan.glasslang.interpreter.primitives.types.Primitive;
import me.sullivan.glasslang.interpreter.primitives.types.StringPrimitive;
import me.sullivan.glasslang.interpreter.primitives.types.Type;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.nodes.BinOpNode;
import me.sullivan.glasslang.parser.nodes.NumberNode;
import me.sullivan.glasslang.parser.nodes.StringNode;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest
{
    private Interpreter interpreter;

    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        interpreter = new Interpreter(Context.getGlobalContext());
    }

    @org.junit.jupiter.api.Test
    void visitBinOp()
    {
        BinOpNode multiplyStringNode = new BinOpNode(new BinOpNode(new StringNode(new Token(TokenType.STRING, "appending the number ")), new Token(TokenType.TIMES),
                new NumberNode(new Token(TokenType.NUMBER, "3"))), new Token(TokenType.PLUS), new NumberNode(new Token(TokenType.NUMBER, "33.4")));
        Primitive<?> stringPrim = interpreter.visitBinOp(multiplyStringNode);
        assertInstanceOf(StringPrimitive.class, stringPrim);
        StringPrimitive string = stringPrim.getValue(Type.STRING);
        assertEquals(string.getValue(), "appending the number appending the number appending the number 33.4");

        BinOpNode addNumbersNode = new BinOpNode(new NumberNode(new Token(TokenType.STRING, "55")), new Token(TokenType.PLUS), new NumberNode(new Token(TokenType.NUMBER, "33")));
        Primitive<?> numPrim = interpreter.visitBinOp(addNumbersNode);
        assertInstanceOf(NumberPrimitive.class, numPrim);
        NumberPrimitive number = numPrim.getValue(Type.NUMBER);
        assertEquals(number.getValue(), 88);

        // TODO add tests for lists
    }

    @org.junit.jupiter.api.Test
    void visitNumberNode()
    {
        NumberNode numberNode = new NumberNode(new Token(TokenType.NUMBER, "323453453453.534543543"));
        Primitive<?> numPrim = interpreter.visitNumberNode(numberNode);
        assertInstanceOf(NumberPrimitive.class, numPrim);
        NumberPrimitive number = numPrim.getValue(Type.NUMBER);
        assertEquals(number.getValue(), 323453453453.534543543);
    }

    @org.junit.jupiter.api.Test
    void visitUnaryOpNode()
    {
        // TODO tests for not, or, and
    }

    @org.junit.jupiter.api.Test
    void visitVariableNode()
    {
    }

    @org.junit.jupiter.api.Test
    void visitAssignmentNode()
    {
    }

    @org.junit.jupiter.api.Test
    void visitIfNode()
    {
    }

    @org.junit.jupiter.api.Test
    void visitForNode()
    {
    }

    @org.junit.jupiter.api.Test
    void visitWhileNode()
    {
    }

    @org.junit.jupiter.api.Test
    void visitFuncDefinitionNode()
    {
    }

    @org.junit.jupiter.api.Test
    void visitCallNode()
    {
    }

    @org.junit.jupiter.api.Test
    void visitStringNode()
    {
    }

    @org.junit.jupiter.api.Test
    void visitListNode()
    {
    }

    @org.junit.jupiter.api.Test
    void visitImportNode()
    {
    }
}