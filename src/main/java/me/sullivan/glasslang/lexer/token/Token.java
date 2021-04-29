package me.sullivan.glasslang.lexer.token;

import java.text.MessageFormat;
import java.util.Objects;

public class Token
{
    private TokenType tokenType;
    private String value;

    public Token(TokenType type, String value)
    {
        this.tokenType = type;
        this.value = value;
    }

    public Token(TokenType type)
    {
        this.tokenType = type;
    }

    public String getValue()
    {
        return value;
    }

    public TokenType getType()
    {
        return tokenType;
    }

    @Override
    public String toString()
    {
        return tokenType.getName() + (value == null ? "" : MessageFormat.format("({0})", value));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Token token))
        {
            return false;
        }
        return tokenType == token.tokenType && Objects.equals(value, token.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(tokenType, value);
    }
}
