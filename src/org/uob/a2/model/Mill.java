package org.uob.a2.engine;

import org.uob.a2.model.ResourceType;

public class Mill extends Converter implements Tickable {
    
    public Mill() 
    {
        super("Mill", ResourceType.WHEAT, 10, ResourceType.FLOUR, 1);
        costs.put(ResourceType.WOOD, 25);
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
        