package org.uob.a2.parser;

import org.uob.a2.*;
import org.uob.a2.engine.*;
import java.util.List;

public class InfoCommand extends Command
{
    public InfoCommand(List<String> words)
    {
        super(words);
    }

    public String execute(Context ctx)
    {
        if(words.size() < 2)
        {
            return new InvalidCommand(words).execute(ctx);
        }
        String infoType = words.get(1); //resource or enitity
        return ctx.engine().info(infoType);
    }
}