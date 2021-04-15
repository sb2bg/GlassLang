package me.sullivan.glasslang.shell;

import java.util.Arrays;

public enum OptionType
{

    // OPTIONS -> gdebug goutlex goutp goutint
    G_DEBUG("gdebug"), G_OUT_LEX("goutlex"), G_OUT_P("goutp"), G_OUT_INT("goutint"), UNKNOWN(null);

    private String value;

    OptionType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public static OptionType parseOptionType(String value)
    {
        return Arrays.stream(OptionType.values()).filter(o -> o.value.equals(value)).findAny().orElse(UNKNOWN);
    }
}
