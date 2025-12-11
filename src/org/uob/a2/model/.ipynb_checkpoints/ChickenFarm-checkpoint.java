package org.uob.a2.engine;

public class ChickenFarm extends Producer implements tickable
{
    public ChickenFarm()
    {
        super("ChickenFarm", ResourceType.EGGS, 3);
        costs.put(ResourceType.CREDITS, 250);
    }

    public void produce(Context ctx)
    {
        ctx.state().addResource(product, amount);
    }

    public String toCSV()
    {
        return "CHICKENFARM,EGGS,3";
    }
    public void tick(Contect ctx)
    {
        produce(ctx);
    }
}