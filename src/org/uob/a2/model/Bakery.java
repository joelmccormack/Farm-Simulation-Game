package org.uob.a2.engine;

import org.uob.a2.model.ResourceType;

public class Bakery extends Converter implements Tickable {
    
    public Bakery() 
    {
        super("Bakery", ResourceType.FLOUR, 10, ResourceType.BREAD, 1);
        costs.put(ResourceType.WOOD, 50);
    }

    public void convert(Context ctx)
    {
        if(ctx.state().getResourceAmount(this.input) >= this.inputAmount && ctx.state().getResourceAmount(ResourceType.MILK) >= this.inputAmount && ctx.state().getResourceAmount(ResourceType.EGGS) >= this.inputAmount)
        {
            ctx.state().addResource(this.output, this.outputAmount);
            ctx.state().removeResource(this.input, (this.inputAmount));
            ctx.state().removeResource(ResourceType.MILK, (this.inputAmount));
            ctx.state().removeResource(ResourceType.EGGS, (this.inputAmount));
        }          
    }

    public void tick(Context ctx)
    {
        convert(ctx);
    }

    public String toCSV()
    {
        return name;
    }
}