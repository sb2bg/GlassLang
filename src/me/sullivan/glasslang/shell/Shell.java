package me.sullivan.glasslang.shell;

import java.util.List;
import java.util.Scanner;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.Lexer;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.Parser;
import me.sullivan.glasslang.parser.nodes.Node;

public class Shell {

	private static final Scanner SCANNER = new Scanner(System.in);
	private static final Context G_CONTEXT = Context.getGlobalContext();
	private static RunOption runOption;
	
	// TODO parent variable tables not setting correctly
	
	public static void main(String[] args)
	{
		runOption = new RunOption(args);
		acceptIn();
		
		while (SCANNER.hasNext())
		{
			String current = SCANNER.nextLine();
			
			List<Token> tokens = new Lexer(current).lex();
			runOption.printLex(tokens);
			
			Node node = new Parser(tokens).parse();
			runOption.printParse(node);
			
			Primitive<?> result = new Interpreter(G_CONTEXT).visitNode(node);
			runOption.printInt(result);
			
			acceptIn();
		}
	}
	
	// TEMP no need once parsing text files
	private static void acceptIn()
	{
		System.out.print("GLang >>> ");
	}
}