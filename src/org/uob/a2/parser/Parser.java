package org.uob.a2.parser;

import org.uob.a2.*;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;


public class Parser 
{

    public Command parse(String command) //takes input and returns the correct command
    {
        command = command.trim().toLowerCase(); //removing trailing spaces and makes input case insensitive
        if(command.isEmpty()) //if emopty command, simulation takes it as a tick command
        {
            return new TickCommand(new ArrayList<String>(Arrays.asList("tick"))); 
        }
        
        List<String> words = new ArrayList<String>();
        for(String word : command.split("\\s+")) //splits the input at a space or multiple spaces
            {
                words.add(word);
            }
        
        switch (words.get(0)) //switches between each posible command and returns the correct command, or invalid command if it doesnt match any commands
        {
            case"build":
            case"b":
                return new BuildCommand(words);
            
            case"info":
            case"i":
                return new InfoCommand(words);

            case"graph":
            case"g":
                return new GraphCommand(words);
            
            case"save":
            case"s":
                return new SaveCommand(words);
            
            case"load":
            case"l":
                return new LoadCommand(words);
            
            case"tick":
            case"t":
                return new TickCommand(words);

            case"help":
                return new HelpCommand(words);
            
            case"harvest":
                return new HarvestCommand(words);

            case"cheat":
                return new CheatCommand(words);
            
            case"quit":
                return new QuitCommand(words);

            default:
                return new InvalidCommand(words);
        }
         
    }
   
}