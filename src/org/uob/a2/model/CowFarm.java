package org.uob.a2.engine;

import org.uob.a2.model.ResourceType;

public class CowFarm extends Producer implements Tickable
{
    public CowFarm()
    {
        super("CowFarm", ResourceType.MILK, 10);
        costs.put(ResourceType.CREDITS, 250);
    }

    public void produce(Context ctx)
    {
        ctx.state().addResource(product, amount);
    }

    public String toCSV()
    {
        return "COWFARM,MILK,10";
    }
    public void tick(Context ctx)
    {
        produce(ctx);
    }
}