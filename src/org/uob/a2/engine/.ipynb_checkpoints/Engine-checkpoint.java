package org.uob.a2.engine;

import org.uob.a2.model.*;
import java.nio.file.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Engine {

    //attributes
    private SimulationState state;
    private boolean quit = false;
    private int currentTick = 0;
    private Context ctx;

    //constructor - instantuate the SimulationState and Context, set CREDITS to 1000 to start
    public Engine(SimulationState state)
    {
        this.state = state;
        state.addResource(ResourceType.CREDITS, 1000);   
        this.ctx = new Context(this, state);
    }

    public int getCurrentTick() //returns currentTick integer
    {
        return currentTick;
    }

    public String nextTick() //increments currentTick and loops through all entities calling there tick method, to update the simulation, updates the resource history and returns a string
    {
        currentTick++;

        for(Producer p : state.getProducers())
            {
                p.tick(ctx);
            }
        for(Converter c : state.getConverters())
            {
                c.tick(ctx);
            }
        for(Consumer co : state.getConsumers())
            {
                co.tick(ctx);
            }
        state.updateHistory();
        return "Tick-" + currentTick + " (complete)";
    }

    public String save(String filename) //creates a Path to save information and a BufferedWriter to write to the file, writes the currentTick, and loops through all entities and resources writing this to the file
    {
        Path file = Paths.get("data", filename);
        try
        {
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(file, CREATE, TRUNCATE_EXISTING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

            writer.write("CurrentTick," + getCurrentTick());
            writer.newLine();

            for(Producer p : state.getProducers())
                {
                    writer.write(p.getName());
                    writer.newLine();
                }

            for(Converter c : state.getConverters())
                {
                    writer.write(c.getName());
                    writer.newLine();
                }

            for(Consumer c : state.getConsumers())
                {
                    writer.write(c.getName());
                    writer.newLine();
                }

            for(ResourceType r : ResourceType.values())
                {
                    writer.write(r.name() + "," + state.getResourceAmount(r));
                    writer.newLine();
                }

            writer.flush();
            writer.close();
        }
        catch(Exception e)
            {
                return "couldn't save";
            }
        return "Saved to " + filename;
    }

    public String load(String filename) //method to load a file to the current simulation and update all entities and resources
    {
        Path file = Paths.get("data", filename);

        try
        {
            InputStream input = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String s;
            int num = 0;
            
            while((s = reader.readLine()) != null) //loops through file until end of file is reached
              {
                  if(s.trim().isEmpty()) continue; //skips any potential empty lines
                  
                  String[] parts = s.split(","); //splits each line at a potential comma, incase extra info is stored
                  if(parts.length > 1)
                  {
                      num = Integer.parseInt(parts[1]);
                  }
                  
                  switch(parts[0]) //switches through all possible existing entities or resources and then will update this to the current simulation
                    {
                      case"CurrentTick": currentTick = num; break;
                              
                      case"ChickenFarm": state.addProducer(new ChickenFarm()); break;

                      case"WheatField": state.addProducer(new WheatField()); break;

                      case"CowFarm": state.addProducer(new CowFarm()); break;

                      case"Forest": state.addProducer(new Forest()); break;

                      case"Mill": state.addConverter(new Mill()); break; 
                              
                      case"Bakery": state.addConverter(new Bakery()); break;
                              
                      case"Carpentry": state.addConverter(new Carpentry()); break;
                              
                      case"Restaurant": state.addConsumer(new Restaurant()); break;

                      case"CREDITS": state.updateResource(ResourceType.CREDITS, num); break;

                      case"EGGS": state.updateResource(ResourceType.EGGS, num); break;

                      case"MILK": state.updateResource(ResourceType.MILK, num); break;

                      case"WHEAT": state.updateResource(ResourceType.WHEAT, num); break;

                      case"WOOD": state.updateResource(ResourceType.WOOD, num); break;

                      case"FLOUR": state.updateResource(ResourceType.FLOUR, num); break;

                      case"BREAD": state.updateResource(ResourceType.BREAD, num); break;

                      case"TABLEWARE": state.updateResource(ResourceType.TABLEWARE, num); break;
                      
                      case"METAL": state.updateResource(ResourceType.METAL, num); break;

                      case"ORE": state.updateResource(ResourceType.ORE, num); break;

                      default: break;
                    }
            }
            reader.close();
        }
        catch(Exception e)
            {
                return "couldnt load";
            }
        return "Simulation Loaded";
    }

    public String build(String entityName) //method for building entities
    {
        Entity entity; //creates an entity for a potential entity
        switch(entityName) //switches through all possible entities comparing them to the string entered by user and then instantuates this entity
        {
            case"chickenfarm":
                entity = new ChickenFarm();               
                break;
            case"wheatfield":
                entity = new WheatField();
                break;
            case"cowfarm":
                entity = new CowFarm();
                break;
            case"forest":
                entity = new Forest();
                break;
            case"mill":
                entity = new Mill();
                break;
            case"bakery":
                entity = new Bakery();
                break;
            case"carpentry":
                entity = new Carpentry();
                break;
            case"restaurant":
                entity = new Restaurant();
                break;
            default:
                return"Invalid entity";
        }
        
        Map<ResourceType, Integer> costs = entity.getCosts(); //Map for storing the cost of the entity being built
        
        for(Map.Entry<ResourceType, Integer> entry : costs.entrySet()) //loop through all of the entries in the map, which will only be one because each entity i created only requires 1 resource type to build
            {
                ResourceType resource = entry.getKey();
                Integer cost = entry.getValue();
                if(!state.removeResource(resource, cost))//tries to remove the cost from the resource
                {
                    return "Not enough resources";
                }                 
            }
        
        if(entity instanceof Producer) //if the resource was succesfuly removed then will go through if statement to find which entity type the entity id then will add this entity to its correct list and then return message is returned
            {
                state.addProducer((Producer)entity);
            }
            else if(entity instanceof Converter)
            {
                state.addConverter((Converter)entity);
            }
            else if(entity instanceof Consumer)
            {
                state.addConsumer((Consumer)entity);
            }
        
        return "Built " + entityName.toUpperCase();        
    }   
    
    public String info(String infoType) //depending on string passed through parameter returns which entities have been built or how many of each resource type their is
    {
        String list = "";
        switch(infoType)
        {
            case"producers":
                for(Producer p : state.getProducers())
                    {
                        list += p.getName() + "\n";
                    }
                return "List of Producers:\n" + list;
            case"converters":
                for(Converter c : state.getConverters())
                    {
                        list += c.getName() + "\n";
                    }
                return "List of Converters:\n" + list;
            case"consumers":
                for(Consumer co : state.getConsumers())
                    {
                        list += co.getName() + "\n";
                    }
                return "List of Consumers:\n" + list;
            case"resources":
                return "CREDITS --> " + state.getResourceAmount(ResourceType.CREDITS) + 
                    "\nEGGS --> " + state.getResourceAmount(ResourceType.EGGS) + 
                    "\nMILK --> " + state.getResourceAmount(ResourceType.MILK) + 
                    "\nWHEAT --> " + state.getResourceAmount(ResourceType.WHEAT) + 
                    "\nWOOD --> " + state.getResourceAmount(ResourceType.WOOD) + 
                    "\nFLOUR --> " + state.getResourceAmount(ResourceType.FLOUR) + 
                    "\nBREAD --> " + state.getResourceAmount(ResourceType.BREAD) + 
                    "\nTABLEWARE --> " + state.getResourceAmount(ResourceType.TABLEWARE);
            default:
                return"Invalid request";
        }
    }

    public String graph(String resource) //displays text-based horizontal graph of the resource passed through parameter
        {
            switch(resource) //switch case through all potential resourceTypes then calls the buildGraph method
                {
                    case"credits":
                        return buildGraph(ResourceType.CREDITS);
                    case"eggs":
                        return buildGraph(ResourceType.EGGS);
                    case"wheat":
                        return buildGraph(ResourceType.WHEAT);
                    case"milk":
                        return buildGraph(ResourceType.MILK);
                    case"wood":
                        return buildGraph(ResourceType.WOOD);
                    case"flour":
                        return buildGraph(ResourceType.FLOUR);
                    case"bread":
                        return buildGraph(ResourceType.BREAD);
                    case"tableware":
                        return buildGraph(ResourceType.TABLEWARE);
                    default:
                        return "invalid resource";  
                }
        }
    public String buildGraph(ResourceType resource) //logic for builidng the graph
        {
            String graph = "";
            int num = 0;
            for(Map<ResourceType, Integer> m : state.getHistory()) //loops through the different maps of the resourceTypes and their values in the resource history list, getting the value of the amount of the specified resource per loop and then adding the correct amount of '*' to that row in the graph
                {
                    String row = "";
                    for(int i = 0; i < m.get(resource); i++)
                        {
                            row += "*";
                        }
                    graph += "Tick-" + num + "|" + row + "\n";
                    num++;
                }
            return"graph of " + resource + "\n" + graph;           
        }

        
    public String help(String topic) //returns helpful information about the command passed in as a parameter
    {
        switch(topic)
        {
            case"build":
                return "type 'build <producer|converter|consumer>' and if you have enough resources for the particular entity, it will be built for you.\nDifferent Entities:\nProducers: ChickenFarm, WheatField, CowFarm, Forest\nConverters: Mill, Bakery, Carpentry\nConsumers: Restaurant";
            case"info": 
                return "type 'info <resources|producerS|converters|consumers>' exactly as shown and information will be displayed about all which come under that title in the simulation";
            case"graph":
                return "type 'graph <resource>' and a text-based graph will be displayed about the specified resource over time";
            case"save":
                return"Will save your progress in the simulation to the specified file";
            case"load":
                return "Will load the progress from a previous simulation to the current simulation, from a chosen file";
            case"tick":
                return "All producers, converters and Consumers will execute their roles, which will update your resources";
            case"cheat": 
                return "Will give out a large amount of resources";
            case"quit":
                return"Will end the simulation";
            default:
                return"Invalid topic";
        }
    }
    public String harvest() //my final command which will half the amount of each resource type apart from credits, if and only if they have 10+ of each resource, then the total resources subtracted divided by 100 is how much the user well level up
    {
        for(ResourceType r : ResourceType.values())
                {                    
                    if(!(r.name().equals("CREDITS")))
                    {
                        int num = state.getResourceAmount(r);
                        if(num < 10)
                        {
                            return "Not enough resources to harvest";
                        }
                    }
                }        
        int total = 0;
        for(ResourceType r : ResourceType.values())
                {                    
                    if(!(r.name().equals("CREDITS")))
                    {
                        int num = state.getResourceAmount(r);
                        total += (num/2);
                        state.removeResource(r, (num/2));
                    }
                }
        state.increaseLevel(total/100);
        
        return "Harvest Complete\nNew Level = " + state.getLevel();
    }

    public String cheat() //adds a lot of resources to each resource type
    {
         for(ResourceType r : ResourceType.values())
                {
                    state.addResource(r, 100000);
                }
        return"Resources updated";
    }

    public String quit() //sets the simulation boolean quit to true so will end simulation, after loop
    {
        state.quitSimulation();
        return "Quitting...";
    }

    
    
    // USED FOR TESTING
    public void initialiseDefaults() 
    {
        Producer chickenfarm = new ChickenFarm();
        Producer forest = new Forest();
        Producer wheatfield = new WheatField();
        Producer cowfarm = new CowFarm();

        state.addProducer(chickenfarm);
        state.addProducer(forest);
        state.addProducer(wheatfield);
        state.addProducer(cowfarm);

        Converter mill = new Mill();
        Converter bakery = new Bakery();
        Converter carpentry = new Carpentry();

        state.addConverter(mill);
        state.addConverter(bakery);
        state.addConverter(carpentry);

        Consumer restaurant = new Restaurant();
        
        state.addConsumer(restaurant);
        
        
        
        //ADD ALL THE ENTITIES YOU CREATE TO THE PRODUCER, CONVERTER AND CONSUMER LISTS IN SIMULATIONSTATE HERE.
    }

    

    

}