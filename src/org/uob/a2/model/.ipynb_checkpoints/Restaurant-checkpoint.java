package org.uob.a2.engine;

import org.uob.a2.*;
import org.uob.a2.model.*;

public class Restaurant extends Consumer implements Tickable {

    public Restaurant()
    {
        super("Restaurant", ResourceType.BREAD, 10);
        costs.put(ResourceType.WOOD, 100);
    }

    public void consume(Context ctx)
    {
        if(ctx.state().getResourceAmount(consumedResource) >= amount && ctx.state().getResourceAmount(ResourceType.TABLEWARE) >= amount)
        {
            ctx.state().removeResource(consumedResource, amount);
            ctx.state().removeResource(ResourceType.TABLEWARE, amount);
            ctx.state().addResource(ResourceType.CREDITS, 10 * ctx.state().getLevel());
        }
        else if(ctx.state().getResourceAmount(consumedResource) >= amount) //test harness scenario
        {
            ctx.state().removeResource(consumedResource, amount);
        }
    }

    public void tick(Context ctx)
    {
        consume(ctx);
    }

    public String toCSV()
    {
        return name;
    }
}