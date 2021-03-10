package me.sullivan.glasslang.parser;

import java.util.Arrays;
import java.util.List;

import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.lexer.token.TokenType;
import me.sullivan.glasslang.parser.errors.InvalidSyntaxError;
import me.sullivan.glasslang.parser.nodes.AssignmentNode;
import me.sullivan.glasslang.parser.nodes.BinOpNode;
import me.sullivan.glasslang.parser.nodes.Node;
import me.sullivan.glasslang.parser.nodes.NumberNode;
import me.sullivan.glasslang.parser.nodes.UnaryOpNode;
import me.sullivan.glasslang.parser.nodes.VariableNode;

public class Parser {

	private interface NodeMethod
	{
		public Node get();
	}

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
			throw new InvalidSyntaxError("Expected '+', '-', '*', '/' or '**'");
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

	private static final TokenType[] TERM = new TokenType[] { TokenType.TIMES, TokenType.DIVIDE };

	private static final TokenType[] EXPRESSION = new TokenType[] { TokenType.PLUS, TokenType.MINUS };
	
	private Node expression()
	{
		if (current.getType() == TokenType.MONEY_SIGN)
		{
			advance();
			
			if (current.getType() != TokenType.IDENTIFIER)
			{
				throw new InvalidSyntaxError("Expected identifier");
			}
			
			Token identifier = current;
			advance();
			
			if (current.getType() != TokenType.EQUALS)
			{
				throw new InvalidSyntaxError("Expected '='");
			}
			
			advance();
			
			return new AssignmentNode(identifier, expression());
		}
		
		return mathOp(() -> term(), null, EXPRESSION);
	}
	
	private Node term()
	{
		return mathOp(() -> factor(), null, TERM);
	}
	
	private Node factor()
	{
		Token token = current;

		if (isMatch(EXPRESSION))
		{
			advance();
			Node factor = factor();

			return new UnaryOpNode(token, factor);
		}

		return power();
	}
	
	private Node power()
	{
		return mathOp(() -> atom(), () -> factor(), new TokenType[] { TokenType.POWER });
	}
	
	private Node atom()
	{
		Token token = current;
		
		if (current.getType() == TokenType.NUMBER)
		{
			advance();
			return new NumberNode(token);
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
				throw new InvalidSyntaxError("Expected ')'");
			}
		}
		
		throw new InvalidSyntaxError("Expected int, float, identifier, '+', '-' or '('");
	}

	private Token advance()
	{
		index++;
		current = index >= tokens.size() ? current : tokens.get(index);

		return current;
	}

	private boolean isMatch(TokenType[] types)
	{
		return Arrays.stream(types).anyMatch(current.getType()::equals);
	}
}
