package org.uob.a2.parser;

import org.uob.a2.*;
import org.uob.a2.engine.*;
import java.util.List;

public class CheatCommand extends Command
{
    public CheatCommand(List<String> words)
    {
        super(words);
    }

    public String execute(Context ctx)
    {
        return ctx.engine().cheat();
    }
}