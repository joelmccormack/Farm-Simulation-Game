# Farm Simulation Game

## Overview

This project extends a provided object-oriented Java framework to develop a command-driven farm simulation featuring interconnected production systems. Rather than building from scratch, the focus was on understanding the framework's architecture and implementing custom farm entities (producers, converters, and consumers) that interact through shared resources. The simulation models a realistic agricultural economy where raw resources like wheat, eggs, and milk feed into processing stages such as milling, baking, and carpentry, creating an interdependent ecosystem where the output of one building becomes the input of another.

## Simulation Concept

The farm operates as a **tick-based simulation** where each time step represents a period of production and consumption. During each tick:

- **Producers** (ChickenFarm, CowFarm, WheatField, Forest) generate raw resources at regular intervals
- **Converters** (Mill, Bakery, Carpentry) consume input resources to produce output resources
- **Consumers** (Restaurant) use finished products to generate revenue

This creates interconnected production chains. For example:

```
WheatField → Wheat → Mill → Flour → Bakery → Bread → Restaurant → Credits
CowFarm → Milk ↗      ↓
ChickenFarm → Eggs ↗  └→ Carpentry (requires Wood) → Tableware
Forest → Wood ↗
```

The Bakery depends on flour (from the Mill), milk (from the CowFarm), and eggs (from the ChickenFarm) before it can produce bread. The Restaurant then consumes bread and tableware (from the Carpentry) to generate revenue. This interdependency means players must plan and balance their operations—building one entity without sufficient upstream resources will halt production.

## Key Features

- **Command-Driven Simulation** — Control the farm entirely through text commands parsed and executed in real-time
- **Multiple Custom Farm Entities** — ChickenFarm, CowFarm, WheatField, Forest, Mill, Bakery, Carpentry, and Restaurant
- **Resource Production** — Producers automatically generate raw materials (eggs, milk, wheat, wood) each simulation tick
- **Resource Conversion** — Converters transform raw materials into processed goods (flour, bread, tableware)
- **Resource Consumption** — Consumers use finished products and generate credits as revenue
- **Interconnected Production Chains** — Resource dependencies force careful planning; one building's output is another's required input
- **Tick-Based Simulation** — Advance time with simple commands; all producers, converters, and consumers act in sequence
- **Resource Tracking** — Real-time inventory display showing current quantities of all resources
- **Resource History & Graphs** — Visualize resource accumulation and depletion over time with command-driven graph generation
- **Saving and Loading** — Persist simulation state to CSV files; resume previous games
- **Harvesting/Progression** — Increase simulation level to unlock bonuses and progression mechanics

## Farm Entities

| Entity | Type | Purpose | Resources |
|--------|------|---------|-----------|
| **ChickenFarm** | Producer | Generates eggs each tick (3 eggs/tick). Cost: 250 credits | Produces: Eggs |
| **CowFarm** | Producer | Generates milk each tick (10 milk/tick). Cost: 250 credits | Produces: Milk |
| **WheatField** | Producer | Generates wheat each tick (10 wheat/tick). Cost: 250 credits | Produces: Wheat |
| **Forest** | Producer | Generates wood each tick. Cost: 250 credits | Produces: Wood |
| **Mill** | Converter | Converts wheat into flour (10 wheat → 1 flour/tick). Cost: 25 wood | Requires: Wheat; Produces: Flour |
| **Bakery** | Converter | Converts flour, milk, and eggs into bread (10 flour + 10 milk + 10 eggs → 1 bread/tick). Cost: 50 wood | Requires: Flour, Milk, Eggs; Produces: Bread |
| **Carpentry** | Converter | Converts wood into tableware (10 wood → 1 tableware/tick). Cost: 25 credits | Requires: Wood; Produces: Tableware |
| **Restaurant** | Consumer | Consumes bread and tableware to generate credits (10 bread + 10 tableware → 10 × level credits/tick) | Consumes: Bread, Tableware; Produces: Credits |

## Resource System

The simulation tracks the following resources:

**Raw Materials:** Eggs, Milk, Wheat, Wood, ORE, Metal  
**Processed Goods:** Flour, Bread, Tableware  
**Currency:** Credits

Resource flows follow these confirmed chains:

- **Wheat → Flour → Bread** (Wheat Field → Mill → Bakery → Restaurant)
- **Eggs + Milk + Flour → Bread** (ChickenFarm + CowFarm + Mill → Bakery)
- **Wood → Tableware** (Forest → Carpentry)
- **Bread + Tableware → Credits** (Bakery + Carpentry → Restaurant)

When a converter lacks required input resources, it pauses production until resources become available. This dependency system ensures no entity can operate in isolation—careful resource planning and balancing is essential to maintain steady production.

## Commands & Gameplay

| Command | Shorthand | Syntax | Description |
|---------|-----------|--------|-------------|
| **build** | `b` | `build <entity_name>` | Build a farm entity (e.g., `build ChickenFarm`). Consumes the entity's cost in resources. |
| **tick** | `t` | `tick` or leave blank | Advance simulation by one time step. All producers generate resources, converters attempt conversion, consumers use resources. |
| **info** | `i` | `info` | Display current resource inventory, entities built, and simulation level. |
| **graph** | `g` | `graph <resource_name>` | Display a text-based graph of resource quantities over time (requires resource history). |
| **save** | `s` | `save <filename>` | Save current simulation state to a CSV file. |
| **load** | `l` | `load <filename>` | Load a previously saved simulation state from a CSV file. |
| **harvest** | — | `harvest <resource_type>` | Manually harvest a resource for progression bonuses (if applicable). |
| **help** | — | `help` | Display available commands and their syntax. |
| **cheat** | — | `cheat <resource> <amount>` | Add resources to inventory (development/testing only). |
| **quit** | — | `quit` | Exit the simulation. |

**Example gameplay sequence:**
```
> build WheatField          (WheatField now builds; costs 250 credits)
> build ChickenFarm         (ChickenFarm builds)
> build CowFarm             (CowFarm builds)
> tick                       (Producers generate: 10 wheat, 3 eggs, 10 milk)
> info                       (Display inventory)
> build Mill                (Mill builds; costs 25 wood. Wait—you don't have wood yet!)
> build Forest              (Build Forest first)
> tick                       (Forest generates wood, WheatField/ChickenFarm/CowFarm generate more)
> tick                       (Repeat)
> build Mill                (Now you have enough wood)
> tick                       (Mill converts 10 wheat → 1 flour)
> graph Flour               (View flour production history)
> save farm.csv             (Persist current state)
```

## Tick-Based Simulation

Each tick represents one cycle of the farm's operation:

1. **Producers tick()** → Generate resources (e.g., ChickenFarm produces 3 eggs)
2. **Converters tick()** → Attempt conversion (e.g., Mill checks if 10+ wheat available; if yes, remove wheat and add flour)
3. **Consumers tick()** → Attempt consumption (e.g., Restaurant checks if 10+ bread and 10+ tableware available; if yes, remove them and add credits based on level)
4. **Resource history updated** → Current inventory snapshot added to history for graphing

If a converter or consumer lacks required resources, it skips that tick and tries again next time. This means players must maintain sufficient resource stockpiles to keep operations running smoothly.

## Object-Oriented Design

The project demonstrates key OOP principles through its use of the provided framework:

**Base Architecture (Framework):**
- **Entity** (abstract) — Base class for all farm buildings with name and cost properties
- **Producer** (abstract) — Extends Entity; defines `produce()` contract and inherits Tickable interface
- **Converter** (abstract) — Extends Entity; defines input/output resource pairs and `convert()` contract
- **Consumer** (abstract) — Extends Entity; defines consumed resource and `consume()` contract
- **Tickable** (interface) — Contract for entities that act during simulation ticks; `tick(Context)` method
- **SimulationState** — Centralized state management for inventory, entities, level, and history
- **Engine** — Orchestrates entity behavior and state updates; contains build, tick, save, load logic

**Custom Implementations (Project Work):**
- **ChickenFarm, CowFarm, WheatField, Forest** — Concrete Producers; override `produce()` to generate specific resources
- **Mill, Bakery, Carpentry** — Concrete Converters; override `convert()` with input validation and multi-resource dependencies
- **Restaurant** — Concrete Consumer; override `consume()` with bonus calculation based on simulation level

**OOP Concepts in Action:**
- **Inheritance** — Custom entities extend Producer/Converter/Consumer, inheriting common behavior while providing specialized logic
- **Polymorphism** — The Engine iterates through lists of Producers/Converters/Consumers and calls `tick()` on each; behavior varies by concrete type
- **Encapsulation** — SimulationState manages inventory privately; entities access resources only through provided methods (addResource, removeResource, getResourceAmount)
- **Interfaces** — Tickable interface ensures all entities conform to the same time-step contract
- **Abstraction** — Abstract base classes and interfaces hide implementation details; Entity subclasses focus only on their specific production/conversion/consumption logic

This architecture allows new entities to be added by simply extending the appropriate base class and implementing its production/conversion/consumption logic—minimal coupling, maximum flexibility.

## Saving and Loading

The simulation state can be persisted using CSV format:

- **save <filename>** — Writes current resource inventory, all built entities, and simulation level to a CSV file
- **load <filename>** — Restores previous simulation state from a CSV file

This allows players to save progress and resume later without loss of data.

## Resource History & Graphs

The simulation maintains a history of resource amounts at each tick:

- **graph <resource_name>** — Generates a text-based ASCII graph showing resource quantity over time (e.g., `graph Flour` displays how much flour the farm has accumulated/consumed across all ticks)

Resource history is automatically updated after each tick, enabling players to visualize production trends and identify bottlenecks in their farming operation.

## Project Structure

```
farmSimulationGame/
├── src/org/uob/a2/
│   ├── Main.java                    # Entry point; sets up simulation loop
│   ├── engine/                      # Framework base classes and simulation logic
│   │   ├── Entity.java              # Abstract base for all entities
│   │   ├── Producer.java            # Abstract producer
│   │   ├── Converter.java           # Abstract converter
│   │   ├── Consumer.java            # Abstract consumer
│   │   ├── Tickable.java            # Interface for time-step behavior
│   │   ├── SimulationState.java     # Centralized state: inventory, entities, level, history
│   │   ├── Engine.java              # Simulation engine: build, tick, save, load, graph
│   │   └── Context.java             # Context object passed to commands
│   ├── model/                       # Custom farm entities and resources
│   │   ├── ChickenFarm.java         # Produces eggs
│   │   ├── CowFarm.java             # Produces milk
│   │   ├── WheatField.java          # Produces wheat
│   │   ├── Forest.java              # Produces wood
│   │   ├── Mill.java                # Converts wheat → flour
│   │   ├── Bakery.java              # Converts flour+milk+eggs → bread
│   │   ├── Carpentry.java           # Converts wood → tableware
│   │   ├── Restaurant.java          # Consumes bread+tableware → credits
│   │   └── ResourceType.java        # Enum of all resources
│   └── parser/                      # Command parsing and execution
│       ├── Command.java             # Abstract command base
│       ├── Parser.java              # Parses user input into Command objects
│       ├── BuildCommand.java        # Handles build <entity> commands
│       ├── TickCommand.java         # Handles tick commands
│       ├── InfoCommand.java         # Handles info (display inventory)
│       ├── GraphCommand.java        # Handles graph <resource> commands
│       ├── SaveCommand.java         # Handles save <file> commands
│       ├── LoadCommand.java         # Handles load <file> commands
│       ├── HarvestCommand.java      # Handles harvest <resource> commands
│       ├── HelpCommand.java         # Displays command help
│       ├── CheatCommand.java        # Debug/testing command
│       ├── QuitCommand.java         # Exits simulation
│       └── InvalidCommand.java      # Fallback for unrecognized input
├── test/org/uob/a2/
│   ├── ProducerTest.java
│   ├── ConverterTest.java
│   ├── ConsumerTest.java
│   ├── EngineTest.java
│   ├── ParserTest.java
│   └── SimulationStateTest.java
├── lib/                             # JUnit libraries for testing
├── out/                             # Compiled .class files (generated by build)
├── data/                            # Saved simulation files (CSV)
├── run.sh                           # Script to compile and run
├── test.sh                          # Script to compile and run tests
└── README.md                        # This file
```

## Running the Project

### Prerequisites
- Java Development Kit (JDK) 8 or later
- Bash shell (for `.sh` scripts)

### Compiling and Running

On Linux, macOS, or Windows (with Git Bash or WSL):

```bash
./run.sh
```

This compiles all source files in `src/` and runs `Main`. You'll see a welcome prompt and can begin entering commands.

Alternatively, compile and run manually:

```bash
javac -d out -Xlint:none src/org/uob/a2/*.java src/org/uob/a2/engine/*.java src/org/uob/a2/parser/*.java src/org/uob/a2/model/*.java
java -cp out org.uob.a2.Main
```

### Running Tests

To run all JUnit tests:

```bash
./test.sh
```

This compiles both source and test files with the JUnit libraries and executes the test suite. Output displays which tests passed/failed.

Alternatively, run manually:

```bash
javac -d out -Xlint:none -cp .:lib/junit-jupiter-api-5.11.1.jar:lib/hamcrest-core-3.0.jar src/org/uob/a2/*.java src/org/uob/a2/engine/*.java src/org/uob/a2/parser/*.java src/org/uob/a2/model/*.java test/org/uob/a2/*.java
java -jar lib/junit-platform-console-standalone-1.9.0.jar --details=none --class-path ./out/ --scan-classpath
```

## Testing

The project includes JUnit 5 test coverage for core components:

- **ProducerTest** — Validates that producers generate correct resource quantities each tick
- **ConverterTest** — Ensures converters correctly consume inputs and produce outputs; handles insufficient resources
- **ConsumerTest** — Verifies consumers correctly remove and process resources; tests revenue generation
- **EngineTest** — Tests the simulation engine's tick loop, entity registration, and state updates
- **ParserTest** — Confirms command parsing correctly maps input to Command objects
- **SimulationStateTest** — Tests resource inventory operations, entity lists, level progression, and history tracking

These tests validate the core simulation mechanics in isolation, ensuring builders, producers, converters, and consumers work correctly before integration.

## Skills Demonstrated

This project showcases:
- **Java** — Object-oriented programming, enums, collections (List, Map, EnumMap)
- **Object-Oriented Programming** — Inheritance hierarchies, abstract classes, interface contracts, polymorphic behavior
- **Working with and Extending Existing Codebases** — Understanding and building upon a provided framework rather than starting from scratch
- **Design Patterns** — Template method pattern (abstract classes with abstract methods), command pattern (command parsing)
- **Software Architecture** — Separation of concerns across engine, model, and parser packages; centralized state management
- **State and Resource Management** — Tracking complex simulation state (inventory, entities, history) and managing inter-entity dependencies
- **Command Parsing** — Tokenizing user input and dispatching to appropriate command handlers
- **Simulation Logic** — Implementing tick-based systems with producer/converter/consumer interactions
- **Data Persistence** — Saving and loading game state to/from CSV files
- **Unit Testing with JUnit** — Writing and running tests for framework components
- **Problem Solving** — Designing interconnected resource systems and debugging complex state management

## License

[Add your preferred license here]
