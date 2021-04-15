package me.sullivan.glasslang.lexer;

public class Position
{

    private int index;
    private int line;
    private int column;

    public Position(int index, int line, int column)
    {
        this.index = index;
        this.line = line;
        this.column = column;
    }

    public void advance(char current)
    {
        index++;
        column++;

        if (current == '\n')
        {
            line++;
            column = 0;
        }
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getLine()
    {
        return line;
    }

    public void setLine(int line)
    {
        this.line = line;
    }

    public int getColumn()
    {
        return column;
    }

    public void setColumn(int column)
    {
        this.column = column;
    }
}
