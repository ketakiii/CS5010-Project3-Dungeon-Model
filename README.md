# Project Description

The purpose of this project is to create a game that consists of a dungeon, a network of tunnels and caves that are interconnected so that player can explore the entire world by travelling from cave to cave through the tunnels that connect them.

#UML

Updated UML
![UML](../res/uml/Project3updated.png)

Old UML
![UML](../res/uml/Project3uml.png)

# Changes Made to the UML
* Separated the Minimum Spanning Tree - Kruskal Algorithm logic from the Grid class to an independent class - Mst. This is done so that if we want to change/optimize the existing algorithm it would be easy to do so with just changing the Mst class.
* I have added the LocationType Enum which defines the Location into two types - a CAVE and a TUNNEL.


# How to run a program
## Create a Game State 
Take row, column, interconnectivity, cavesWithTreasure, isWrapped from the user as an input. 

``IgameState gameState = new GameState(row, column, interconnectivity, cavesWithTreasure, isWrapped, random);``

## Check the Player's Start Location
``gameState.getPlayerStartPosition()``

## Check the Player's End Location
``gameState.getPlayerEndPosition()``

##Check the Player's Current Location
This location at the start of the game should be equal to the start location
``gameState.getPlayerCurrentPosition()``

##Check the Available Directions the Player can move 
``gameState.getAvailableStartPosition()``

##Move the Player in the Available Direction
``gameState.movePlayer(Direction.SOUTH);``

##Check the Player's Current Location and Treasure Bag 
``gameState.toStringStatus()``

#Example Runs
* WrappingExample : This example run has the dungeon instance with wrapping ability.
````
Random random1 = new Random(12);
GameState gameState1 = new GameState(7, 7, 50, 20.5, true, random1);
System.out.println("The game is starting!");
System.out.println("This is a game with Wrapping.");
System.out.println("Player's Start Position- " + gameState1.getPlayerStartPosition());
System.out.println("Player's End Position- " + gameState1.getPlayerEndPosition());
System.out.println("Player's Current Position- " + gameState1.getPlayerCurrentPosition());
System.out.println("--------------------------------------------------------------");

if (!gameState1.isGameOver()) {
  System.out.println("The game is starting!");
}

System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());
gameState1.movePlayer(Direction.WEST);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.WEST);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());
if (gameState1.isGameOver()) {
  System.out.println("The game is Over!");
}
System.out.println("--------------------------------------------------------------");
}
````
* NonWrappingExample : This example has the dungeon instance with no wrapping ability.
````
Random random1 = new Random(12);
GameState gameState1 = new GameState(7, 7, 50, 20.5, false, random1);
System.out.println("The game is starting!");
System.out.println("This is a game with Non Wrapping.");
System.out.println("Player's Start Position- " + gameState1.getPlayerStartPosition());
System.out.println("Player's End Position- " + gameState1.getPlayerEndPosition());
System.out.println("Player's Current Position- " + gameState1.getPlayerCurrentPosition());
System.out.println("--------------------------------------------------------------");

if (!gameState1.isGameOver()) {
  System.out.println("The game is starting!");
}

System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());
gameState1.movePlayer(Direction.WEST);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.WEST);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());
if (gameState1.isGameOver()) {
  System.out.println("The game is Over!");
}
System.out.println("--------------------------------------------------------------");
}
````
* EveryLocationExample : This example run goes through every connected cave and checks if all the caves have been traversed since it is as expected. 
````
Random random1 = new Random(12);
GameState gameState1 = new GameState(7, 7, 0, 20.5, false, random1);
System.out.println("The game is starting!");
System.out.println("This is a game with Non Wrapping.");
System.out.println("Player's Start Position- " + gameState1.getPlayerStartPosition());
System.out.println("Player's End Position- " + gameState1.getPlayerEndPosition());
System.out.println("Player's Current Position- " + gameState1.getPlayerCurrentPosition());
System.out.println("--------------------------------------------------------------");
if (!gameState1.isGameOver()) {
  System.out.println("The game is starting!");
}
Set<Ilocation> locationsVisited =  new HashSet<>();
Direction[] directionList = new Direction[]{Direction.NORTH, Direction.EAST, Direction.NORTH,
    Direction.WEST, Direction.SOUTH, Direction.NORTH,
    Direction.WEST, Direction.SOUTH, Direction.NORTH,
    Direction.WEST, Direction.SOUTH, Direction.NORTH,
    Direction.WEST, Direction.SOUTH, Direction.NORTH,Direction.WEST
};

locationsVisited.add(gameState1.getPlayerCurrentPosition());

for (Direction d : directionList) {
  gameState1.movePlayer(d);
  System.out.println(gameState1.toStringStatus());
  locationsVisited.add(gameState1.getPlayerCurrentPosition());
}
System.out.println("-------------------------------------------------------------");
System.out.println("\nLocations Visited by the Player " + locationsVisited.size());

if (gameState1.isGameOver()) {
  System.out.println("The game is over!");
}

Ilocation[][] grid = gameState1.getDungeon(random1);
int caves = 0;
for (int i = 0; i < grid.length; i++) {
  for (int j = 0; j < grid[i].length; j++) {
    //System.out.println(i + " " + j + " " + grid[i][j].getLocationType());
    if (grid[i][j].getLocationType() == LocationType.CAVE) {
      caves += 1;
    }
  }
}
System.out.println("The total number of caves are " + caves);
if (locationsVisited.size() == caves) {
  System.out.println("All the locations have been traversed!");
}
}

````
* StartEndExample : This example run start playing at the start location and ends the game at the end location. 
````
Random random1 = new Random(12);
GameState gameState1 = new GameState(7, 7, 50, 20.5, false, random1);
System.out.println("The game is starting!");
System.out.println("This is a game with Non Wrapping.");
System.out.println("Player's Start Position- " + gameState1.getPlayerStartPosition());
System.out.println("Player's End Position- " + gameState1.getPlayerEndPosition());
System.out.println("Player's Current Position- " + gameState1.getPlayerCurrentPosition());
System.out.println("--------------------------------------------------------------");

if (!gameState1.isGameOver()) {
  System.out.println("The game is starting!");
}

System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());
gameState1.movePlayer(Direction.WEST);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.WEST);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());
if (gameState1.isGameOver()) {
  System.out.println("The game is Over!");
}
System.out.println("--------------------------------------------------------------");
}
````
* LocationDescriptionExample : This example run displays the description of the player and its location at every move.
````
Random random1 = new Random(12);
GameState gameState1 = new GameState(7, 7, 50, 20.5, false, random1);
System.out.println("The game is starting!");
System.out.println("This is a game with Non Wrapping.");
System.out.println("Player's Start Position- " + gameState1.getPlayerStartPosition());
System.out.println("Player's End Position- " + gameState1.getPlayerEndPosition());
System.out.println("Player's Current Position- " + gameState1.getPlayerCurrentPosition());
System.out.println("--------------------------------------------------------------");

if (!gameState1.isGameOver()) {
  System.out.println("The game is starting!");
}

System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());
gameState1.movePlayer(Direction.WEST);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.SOUTH);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());

gameState1.movePlayer(Direction.WEST);
System.out.println(gameState1.toStringStatus());
System.out.println("\nThe player can move " + gameState1.getAvailableStartPosition());
if (gameState1.isGameOver()) {
  System.out.println("The game is Over!");
}
System.out.println("--------------------------------------------------------------");
}
````


# Assumptions
* The grid length should be more than 5 since the minimum distance between the start and the end location is 5.
* The grid width should be more than 5 since the minimum distance between the start and the end location is 5.
* The interconnectivity can be equal to or greater than 0.
* The percentage of treasure to be added to the caves randomly should be a number greater than 0.
* Dungeon are of two types : wrapped and non-wrapped.

# Limitations
* Need Java version 11 or greater.
* Can not test for a grid smaller than 7x7 since a minimum distance of 5 needs to be maintained between the start and the end locations. 

#Citations
1. [Canvas Question](https://northeastern.instructure.com/courses/136753/assignments/1707745)
2. [MST Algorithm](https://www.geeksforgeeks.org/prims-minimum-spanning-tree-mst-greedy-algo-5/)
3. [GeeksForGeeks](https://www.geeksforgeeks.org/)
4. [Google](https://www.google.com/)
5. [StackOverflow](https://stackoverflow.com/)
6. [Java Documentation](https://docs.oracle.com/en/java/)

