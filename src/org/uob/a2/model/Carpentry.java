package org.uob.a2.engine;

import org.uob.a2.model.ResourceType;

public class Carpentry extends Converter implements Tickable {
    
    public Carpentry() 
    {
        super("Carpentry", ResourceType.WOOD, 10, ResourceType.TABLEWARE, 1);
        costs.put(ResourceType.CREDITS, 25);
    }

    public void convert(Context ctx)
    {
        if(ctx.state().removeResource(this.input, this.inputAmount))
        {
            ctx.state().addResource(this.output, this.outputAmount);
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
        