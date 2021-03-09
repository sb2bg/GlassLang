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
		Node statement = statement();

		if (current.getType() != TokenType.EOL && current.getType() != TokenType.EOF)
		{
			//throw new InvalidSyntaxError("Expected another statement");
		}

		return statement;
	}

	private Node mathOp(NodeMethod func, TokenType[] types)
	{
		Node left = func.get();

		while (current.getType() != TokenType.EOF && isMatch(types))
		{
			Token operator = current;
			advance();
			Node right = func.get();

			left = new BinOpNode(left, operator, right);
		}

		return left;
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
		else if (current.getType() == TokenType.NUMBER)
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

		throw new InvalidSyntaxError("Expected int or float");
	}

	private static final TokenType[] TERM = new TokenType[] { TokenType.TIMES, TokenType.DIVIDE };

	private Node term()
	{
		return mathOp(() -> factor(), TERM);
	}

	private static final TokenType[] EXPRESSION = new TokenType[] { TokenType.PLUS, TokenType.MINUS };

	private Node statement()
	{
		AssignmentNode assignNode = parseAssignment();

		if (assignNode != null)
		{
			return assignNode;
		}

		return expression();
	}

	private Node expression()
	{
		return mathOp(() -> term(), EXPRESSION);
	}

	private AssignmentNode parseAssignment()
	{
		if (current.getType() == TokenType.VAR)
		{
			advance();

			if (current.getType() != TokenType.IDENTIFIER)
			{
				throw new InvalidSyntaxError("Expected an identifier");
			}

			Token iden = current;
			advance();

			if (current.getType() != TokenType.EQUALS)
			{
				throw new InvalidSyntaxError("Expected '='");
			}

			advance();

			return new AssignmentNode(iden, expression());
		}

		return null;
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
