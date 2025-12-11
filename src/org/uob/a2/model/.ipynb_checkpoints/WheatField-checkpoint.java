package org.uob.a2.engine;

public class WheatField extends Producer implements tickable
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
    public void tick(Contect ctx)
    {
        produce(ctx);
    }
}