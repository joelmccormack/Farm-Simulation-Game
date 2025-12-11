package org.uob.a2.parser;

import org.uob.a2.*;
import org.uob.a2.engine.*;
import java.util.List;

public class HarvestCommand extends Command
{
    public HarvestCommand(List<String> words)
    {
        super(words);
    }

    public String execute(Context ctx)
    {
        return ctx.engine().harvest();
    }
}