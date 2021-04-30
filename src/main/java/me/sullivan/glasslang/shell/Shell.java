package me.sullivan.glasslang.shell;

import me.sullivan.glasslang.interpreter.Interpreter;
import me.sullivan.glasslang.interpreter.primitives.types.Primitive;
import me.sullivan.glasslang.interpreter.runtime.Context;
import me.sullivan.glasslang.lexer.Lexer;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.Parser;
import me.sullivan.glasslang.parser.nodes.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Shell
{
    public static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws IOException
    {
        RunOption runOption = new RunOption(args);

        List<Token> tokens = new Lexer(Files.readString(Path.of("src/main/resources/Main.gls"))).lex();
        runOption.printLex(tokens);

        Node node = new Parser(tokens).parse();
        runOption.printParse(node);

        Primitive<?> result = new Interpreter(Context.getGlobalContext()).visitNode(node);
        runOption.printInt(result);
    }
}