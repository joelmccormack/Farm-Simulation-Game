package org.uob.a2.engine;

import org.uob.a2.model.ResourceType;

public class Forest extends Producer implements Tickable
{
    public Forest()
    {
        super("Forest", ResourceType.WOOD, 10);
        costs.put(ResourceType.CREDITS, 250);
    }

    public void produce(Context ctx)
    {
        ctx.state().addResource(product, amount);
    }

    public String toCSV()
    {
        return "Forest,WOOD,10";
    }
    public void tick(Context ctx)
    {
        produce(ctx);
    }
}