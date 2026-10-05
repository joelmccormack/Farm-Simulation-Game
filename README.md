# Farm Simulation Game

A Java-based farming simulation game where players manage a virtual farm, grow crops, raise animals, and manage resources through different seasons.

## Features

- **Farm Management** — Plant crops, manage fields, and organize farm layout
- **Animal Husbandry** — Raise and care for farm animals
- **Seasonal System** — Experience different seasons affecting crop growth and game dynamics
- **Resource Management** — Track and manage crops, animals, and supplies
- **Economic System** — Buy and sell goods to grow your farm
- **Day/Night Cycle** — Experience realistic farming time progression

## Tech Stack

- **Language:** Java
- **Build System:** Maven (or Gradle)
- **Data Storage:** CSV files in `data/` folder
- **Testing:** JUnit

## Project Structure

```
farmSimulationGame/
├── src/
│   ├── main/java/         # Main game code
│   └── test/java/         # Unit tests
├── data/                   # Game data files (crops, animals, farm data)
├── lib/                    # External dependencies
├── out/                    # Compiled output
├── run.sh                  # Script to run the game
├── test.sh                 # Script to run tests
└── README.md              # This file
```

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or later
- Maven or similar build tool (optional)

### Running the Game

**On Linux/Mac:**
```bash
./run.sh
```

**On Windows:**
```bash
bash run.sh
# or compile and run directly with Java
```

### Running Tests

**On Linux/Mac:**
```bash
./test.sh
```

## How to Play

1. Start the game and name your farm
2. Begin with a small plot of land and limited resources
3. Plant crops in your fields
4. Buy animals to raise on your farm
5. Complete tasks and manage your farm through each season
6. Sell crops and animal products for profit
7. Expand your farm and become a successful farmer!

## What I Learned

This project demonstrates:
- Game simulation mechanics and time management
- Data persistence and CSV file handling
- Object-oriented design with multiple interacting systems
- Resource and inventory management
- Season-based game logic
- Testing complex game state

## License

[Add your preferred license here]
