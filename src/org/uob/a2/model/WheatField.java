package org.uob.a2.engine;

import org.uob.a2.model.ResourceType;

public class WheatField extends Producer implements Tickable
{
    public WheatField()
    {
        super("WheatField", ResourceType.WHEAT, 10);
        costs.put(ResourceType.CREDITS, 250);
    }

    public void produce(Context ctx)
    {
        ctx.state().addResource(product, amount);
    }

    public String toCSV()
    {
        return "WHEATFIELD,WHEAT,10";
    }
    public void tick(Context ctx)
    {
        produce(ctx);
    }
}