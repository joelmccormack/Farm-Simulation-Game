package org.uob.a2.engine;

public class CowFarm extends Producer implements tickable
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
    public void tick(Contect ctx)
    {
        produce(ctx);
    }
}