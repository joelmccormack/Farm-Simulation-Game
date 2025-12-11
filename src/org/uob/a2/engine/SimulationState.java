package org.uob.a2.engine;

import org.uob.a2.model.*;

import java.util.List;
import java.util.ArrayList;

import java.util.EnumMap;
import java.util.Map;

public class SimulationState {

    //attributes
    private List<Producer> producers;
    private List<Converter> converters;
    private List<Consumer> consumers;
    private Map<ResourceType, Integer> inventory = new EnumMap<>(ResourceType.class);
    private List<Map<ResourceType, Integer>> resourceHistory = new ArrayList<>();
    private int level;
    private boolean quit;

    //constructor - instantuates all entity lists sets level to 0 and quit to false
    public SimulationState()
    {
        producers = new ArrayList<Producer>();
        converters = new ArrayList<Converter>();
        consumers = new ArrayList<Consumer>();
        level = 0;
        quit = false;

        for(ResourceType r : ResourceType.values())
            {
                inventory.put(r, 0);
            }
    }
    public int getLevel() //returns level
    {
        return level;
    }

    public void increaseLevel(int num) //adds the int passed in paramter to the level
    {
        level += num;
    }

    public boolean hasQuit() //returns the boolean quit 
    {
        return quit;
    }

    public void quitSimulation() //sets boolean wuit to true
    {
        quit = true;
    }

    //methods to retrn the list of each type of entity
    public List<Producer> getProducers() {
        return producers;
    }

    public List<Converter> getConverters() {
        return converters;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void addResource(ResourceType resource, int amount) //adds a specified amount of resources to the specified resource type
    {
        int value = inventory.getOrDefault(resource, 0);
        inventory.put(resource, value + amount);
    }

    public void updateResource(ResourceType resource, int amount) //sets a resource types resource amount
    {
        int value = inventory.getOrDefault(resource, 0);
        inventory.put(resource, amount);
    }

    public int getResourceAmount(ResourceType resource)//returns the amount of resources a resource type has
    {
        return inventory.getOrDefault(resource, 0);
    }

    public boolean removeResource(ResourceType resource, int amount)//tries to remove a resource, returns true or false depending if it was successful
    {
        int value = inventory.getOrDefault(resource, 0);
        if(value < amount)
        {
            return false;
        }
        inventory.put(resource, value - amount);
        return true;
    }

    public void updateHistory() //adds all the resources each resourceType has to the resource history list
    {
        Map<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
        for(Map.Entry<ResourceType, Integer> entry : inventory.entrySet())
            {
                resources.put(entry.getKey(), entry.getValue());
            }
        resourceHistory.add(resources);
    }

    public List<Map<ResourceType, Integer>> getHistory() //returns the resource history list
    {
        return this.resourceHistory;
    }

    //methods for adding enitities
    public void addProducer(Producer producer)
    {
        producers.add(producer);
    }
   
    public void addConverter(Converter converter)
    {
        converters.add(converter);
    }
    
    public void addConsumer(Consumer consumer)
    {
        consumers.add(consumer);
    }
}