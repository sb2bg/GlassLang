package me.sullivan.glasslang;

import java.util.List;
import java.util.Scanner;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitves.NumberPrimitive;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.interpreter.runtime.VariableTable;
import me.sullivan.glasslang.lexer.Lexer;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.Parser;
import me.sullivan.glasslang.parser.nodes.Node;

public class Shell {

	private static final String G_DEBUG = "gdebug";
	private static final String G_STOP = "gstop";
	private static final Scanner SCANNER = new Scanner(System.in);

	public static void main(String[] args)
	{
		if (debug(args))
		{
			String curr;
			System.out.print("Glass Debug > ");
			Context context = new Context(null, "<glmain>", new VariableTable());
			context.getTable().set("null", new NumberPrimitive(0));
			
			while (SCANNER.hasNext())
			{
				curr = SCANNER.nextLine();

				if (curr.equalsIgnoreCase(G_STOP))
				{
					System.out.println("Debugging ending...");
					return;
				}

				List<Token> tokens = new Lexer(curr).lex();
				System.out.println(tokens);
				
				Node rootNode = new Parser(tokens).parse();
				System.out.println(rootNode);
				
				System.out.println(new Interpreter().visitNode(rootNode, context));
				
				System.out.print("Glass Debug > ");
			}
		}
		else
		{
			String filename = getFilename(args);

			if (filename == null)
			{
				System.out.println("Please enter the correct amount of arguments.");
			}
		}
	}

	private static boolean debug(String[] args)
	{
		if (args.length > 0)
		{
			return args[0].equalsIgnoreCase(G_DEBUG);
		}

		return false;
	}

	private static String getFilename(String[] args)
	{
		if (args.length > 0)
		{
			return args[0];
		}

		return null;
	}
}
