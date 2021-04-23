package me.sullivan.glasslang.parser;

import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.errors.InvalidSyntaxError;
import me.sullivan.glasslang.parser.nodes.*;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class Parser
{
    private final List<Token> tokens;
    private Token current;
    private int index;

    public Parser(List<Token> tokens)
    {
        this.tokens = tokens;
        this.index = 0;
        this.current = tokens.get(index);
    }

    public Node parse()
    {
        Node exp = expression();

        if (current.getType() != TokenType.EOL && current.getType() != TokenType.EOF)
        {
            throw new InvalidSyntaxError(new TokenType[]{
                    TokenType.PLUS, TokenType.MINUS, TokenType.TIMES,
                    TokenType.DIVIDE, TokenType.POWER, TokenType.EQUAL_OP,
                    TokenType.NOT_EQUAL, TokenType.LESS, TokenType.GREATER,
                    TokenType.LESS_EQUAL, TokenType.GREATER_EQUAL, TokenType.AND,
                    TokenType.OR
            });
        }

        return exp;
    }

    private Node mathOp(NodeMethod a, NodeMethod b, TokenType[] types)
    {
        b = b == null ? a : b;

        Node left = a.get();

        while (current.getType() != TokenType.EOF && isMatch(types))
        {
            Token operator = current;
            advance();
            Node right = b.get();

            left = new BinOpNode(left, operator, right);
        }

        return left;
    }

    private Node expression()
    {
        if (current.getType() == TokenType.VAR)
        {
            return new AssignmentNode(assignment(), expression());
        }

        return mathOp(this::compExpression, null, new TokenType[]{TokenType.AND, TokenType.OR});
    }

    private Token assignment()
    {
        advance();

        if (current.getType() != TokenType.IDENTIFIER)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.IDENTIFIER});
        }

        Token identifier = current;
        advance();

        if (current.getType() != TokenType.EQUALS)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.EQUALS});
        }

        advance();

        return identifier;
    }

    private Node compExpression()
    {
        if (current.getType() == TokenType.NOT)
        {
            Token token = current;
            advance();

            return new UnaryOpNode(token, compExpression());
        }

        return mathOp(this::arithmeticExpression, null, new TokenType[]{
                TokenType.LESS, TokenType.LESS_EQUAL,
                TokenType.GREATER, TokenType.GREATER_EQUAL,
                TokenType.EQUAL_OP, TokenType.NOT_EQUAL
        });
    }

    private Node arithmeticExpression()
    {
        return mathOp(this::term, null, new TokenType[]{TokenType.PLUS, TokenType.MINUS});
    }

    private Node term()
    {
        return mathOp(this::factor, null, new TokenType[]{TokenType.TIMES, TokenType.DIVIDE, TokenType.MOD});
    }

    private Node factor()
    {
        Token token = current;

        if (isMatch(new TokenType[]{TokenType.PLUS, TokenType.MINUS}))
        {
            advance();

            return new UnaryOpNode(token, factor());
        }

        return power();
    }

    private Node power()
    {
        return mathOp(this::call, this::factor, new TokenType[]{TokenType.POWER});
    }

    private Node call()
    {
        Node atom = atom();
        List<Node> argNodes = new ArrayList<>();

        if (current.getType() == TokenType.LPAREN)
        {
            advance();

            if (current.getType() != TokenType.RPAREN)
            {
                argNodes.add(expression());

                while (current.getType() == TokenType.COMMA)
                {
                    advance();

                    argNodes.add(expression());
                }

                if (current.getType() != TokenType.RPAREN)
                {
                    throw new InvalidSyntaxError(new TokenType[]{TokenType.RPAREN});
                }
            }

            advance();

            return new CallNode(atom, argNodes);
        }

        return atom;
    }

    private Node atom()
    {
        Token token = current;

        if (current.getType() == TokenType.NUMBER)
        {
            advance();
            return new NumberNode(token);
        }
        else if (current.getType() == TokenType.STRING)
        {
            advance();
            return new StringNode(token);
        }
        else if (current.getType() == TokenType.IDENTIFIER)
        {
            advance();
            return new VariableNode(token);
        }
        else if (current.getType() == TokenType.LPAREN)
        {
            advance();
            Node expression = expression();

            if (current.getType() == TokenType.RPAREN)
            {
                advance();
                return expression;
            }
            else
            {
                throw new InvalidSyntaxError(new TokenType[]{TokenType.LPAREN});
            }
        }
        else if (current.getType() == TokenType.IMPORT)
        {
            return importExpression();
        }
        else if (current.getType() == TokenType.LBRACKET)
        {
            return listExpression();
        }
        else if (current.getType() == TokenType.IF)
        {
            return ifExpression();
        }
        else if (current.getType() == TokenType.FOR)
        {
            return forExpression();
        }
        else if (current.getType() == TokenType.WHILE)
        {
            return whileExpression();
        }
        else if (current.getType() == TokenType.FUNC)
        {
            return funcDefinition();
        }

        throw new InvalidSyntaxError(new TokenType[]{
                TokenType.NUMBER, TokenType.IDENTIFIER, TokenType.PLUS,
                TokenType.MINUS, TokenType.LPAREN});
    }

    // TODO implement import
    // import "module" => module (maybe just add functions to importing class instead of importing classes?)
    // import "module2" => module2
    // OR
    // import ("module" => module, "module2" => whateverModule2)
    private Node importExpression()
    {
        if (current.getType() != TokenType.IMPORT)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.IMPORT});
        }

        advance();

        if (current.getType() != TokenType.STRING)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.STRING});
        }

        Token string = current;
        advance();

        return new ImportNode(string);
    }

    private Node listExpression()
    {
        if (current.getType() != TokenType.LBRACKET)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.LBRACKET});
        }

        advance();

        List<Node> expressions = new ArrayList<>();

        if (current.getType() != TokenType.RBRACKET)
        {
            expressions.add(expression());

            while (current.getType() == TokenType.COMMA)
            {
                advance();
                expressions.add(expression());
            }

            if (current.getType() != TokenType.RBRACKET)
            {
                throw new InvalidSyntaxError(new TokenType[]{TokenType.RBRACKET});
            }
        }

        advance();

        return new ListNode(expressions);
    }

    private Node ifExpression()
    {
        Token token = current;

        if (current.getType() != TokenType.IF)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.IF});
        }

        advance();
        Node condition = expression();

        if (current.getType() != TokenType.LAMBDA)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.LAMBDA});
        }

        advance();
        Pair<Node, Node> ifCase = new Pair<>(condition, expression());

        Node elseCase = null;

        if (current.getType() == TokenType.ELSE)
        {
            advance();
            elseCase = expression();
        }

        return new IfNode(token, ifCase, elseCase);
    }

    private Node forExpression()
    {
        if (current.getType() != TokenType.FOR)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.FOR});
        }

        Token variable = assignment();
        Node startVal = expression();

        if (current.getType() != TokenType.TO)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.TO});
        }

        advance();
        Node endVal = expression();
        Node step = null;

        if (current.getType() == TokenType.STEP)
        {
            advance();
            step = expression();
        }

        if (current.getType() != TokenType.LAMBDA)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.LAMBDA});
        }

        advance();
        return new ForNode(variable, startVal, endVal, step, expression());
    }

    private Node whileExpression()
    {
        if (current.getType() != TokenType.WHILE)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.WHILE});
        }

        advance();
        Node condition = expression();

        if (current.getType() != TokenType.LAMBDA)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.LAMBDA});
        }

        advance();
        return new WhileNode(condition, expression());
    }

    private Node funcDefinition()
    {
        if (current.getType() != TokenType.FUNC)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.FUNC});
        }

        advance();

        Token func = new Token(TokenType.STRING, null);
        List<Token> args = new ArrayList<>();

        if (current.getType() == TokenType.IDENTIFIER)
        {
            func = current;
            advance();

            if (current.getType() != TokenType.LPAREN)
            {
                throw new InvalidSyntaxError(new TokenType[]{TokenType.LPAREN});
            }

        }
        else if (current.getType() != TokenType.LPAREN)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.LPAREN, TokenType.IDENTIFIER});
        }

        advance();

        if (current.getType() == TokenType.IDENTIFIER)
        {
            args.add(current);
            advance();

            while (current.getType() == TokenType.COMMA)
            {
                advance();

                if (current.getType() != TokenType.IDENTIFIER)
                {
                    throw new InvalidSyntaxError(new TokenType[]{TokenType.IDENTIFIER});
                }

                args.add(current);
                advance();
            }

            if (current.getType() != TokenType.RPAREN)
            {
                throw new InvalidSyntaxError(new TokenType[]{TokenType.COMMA, TokenType.RPAREN});
            }
        }
        else if (current.getType() != TokenType.RPAREN)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.IDENTIFIER, TokenType.RPAREN});
        }

        advance();

        if (current.getType() != TokenType.LAMBDA)
        {
            throw new InvalidSyntaxError(new TokenType[]{TokenType.LAMBDA});
        }

        advance();

        return new FunctionDefinitionNode(func, args, expression());
    }

    private void advance()
    {
        index++;
        current = index >= tokens.size() ? current : tokens.get(index);
    }

    private boolean isMatch(TokenType[] types)
    {
        return List.of(types).contains(current.getType());
    }

    private interface NodeMethod
    {
        Node get();
    }
}
