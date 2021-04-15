package me.sullivan.glasslang.shell;

import me.sullivan.glasslang.interpreter.primitives.Primitive;
import me.sullivan.glasslang.lexer.token.Token;
import me.sullivan.glasslang.parser.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class RunOption
{

    private List<OptionType> options = new ArrayList<>();

    public RunOption(String[] values)
    {
        for (String value : values)
        {
            OptionType type = OptionType.parseOptionType(value);

            if (value != null)
            {
                options.add(type);
            }
        }
    }

    public void printLex(List<Token> tokens)
    {
        if (!options.contains(OptionType.G_OUT_LEX))
        {
            return;
        }

        System.out.println("Lexed Result -> " + tokens.toString());
    }

    public void printParse(Node node)
    {
        if (!options.contains(OptionType.G_OUT_P))
        {
            return;
        }

        System.out.println("Parsed Result -> " + node.toString());
    }

    // TEMP wont have to print once we have print method
    public void printInt(Primitive<?> result)
    {
        if (!options.contains(OptionType.G_OUT_INT))
        {
            return;
        }

        System.out.println("Interpreted Result -> " + result);
    }
}
