package org.uob.a2;
import org.uob.a2.engine.*;
import org.uob.a2.parser.*;

import java.util.Scanner;

public class Main {

    
    public static void main(String[] args)
    {
        //instantuating an object for all needed classes
        SimulationState state = new SimulationState();
        Engine engine = new Engine(state);
        Context ctx = new Context(engine, state);
        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);

        //welcome message to simulation
        System.out.println("Welcome To the Farm Simulation\n\nEnter a command to begin the simulation:");

        //loops simulation while the SimulationState boolean quit is false
        while(!(state.hasQuit()))
            {
                //user input is made into a command and then the output of this command is printed to terminal
                System.out.print(">");
                String command = scanner.nextLine();
                Command c = parser.parse(command);
                String output = c.execute(ctx);
                System.out.println(output);
            }
    }
   
} 