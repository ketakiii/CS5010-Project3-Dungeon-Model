import java.util.Random;
import java.util.Scanner;

/**
 * Main class.
 */

public class Driver {

  /**
   * Main method.
   * @param args String
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Design your Dungeon: ");
    System.out.print("Enter the number of rows- ");
    int row = sc.nextInt();
    System.out.println("row " + row + " entered");
    System.out.print("Enter the number of columns- ");
    int column = sc.nextInt();
    System.out.println("column " + column + " entered");
    System.out.print("Enter the desired interconnectivity- ");
    int interconnectivity = sc.nextInt();
    System.out.println("interconnectivity " + interconnectivity + " entered");
    System.out.print("Enter the percentage of caves to assign treasure to- ");
    double cavesWithTreasure = sc.nextDouble();
    System.out.println("% of caves with more treasure " + cavesWithTreasure + " % entered");
    System.out.print("Enter true/false for wrapping of the dungeon- ");
    boolean isWrapped = sc.nextBoolean();
    if (isWrapped) {
      System.out.println("The Dungeon is Wrapped");
    } else {
      System.out.println("The Dungeon is not Wrapped");
    }
    Random random = new Random(12);
    IgameState gameState = new GameState(row, column, interconnectivity,
        cavesWithTreasure, isWrapped, random);
    System.out.println("Player's Start Position- " + gameState.getPlayerStartPosition());
    System.out.println("Player's End Position- " + gameState.getPlayerEndPosition());
    System.out.println("Player's Current Position- " + gameState.getPlayerCurrentPosition());
    System.out.println("--------------------------------------------------------------");

    if (!gameState.isGameOver()) {
      System.out.println("The game is starting!");
    }

    System.out.println("\nThe player can move " + gameState.getAvailableStartPosition());
    gameState.movePlayer(Direction.WEST);
    System.out.println(gameState.toStringStatus());
    System.out.println("\nThe player can move " + gameState.getAvailableStartPosition());

    gameState.movePlayer(Direction.SOUTH);
    System.out.println(gameState.toStringStatus());
    System.out.println("\nThe player can move " + gameState.getAvailableStartPosition());

    gameState.movePlayer(Direction.SOUTH);
    System.out.println(gameState.toStringStatus());
    System.out.println("\nThe player can move " + gameState.getAvailableStartPosition());

    gameState.movePlayer(Direction.SOUTH);
    System.out.println(gameState.toStringStatus());
    System.out.println("\nThe player can move " + gameState.getAvailableStartPosition());

    gameState.movePlayer(Direction.WEST);
    System.out.println(gameState.toStringStatus());
    System.out.println("\nThe player can move " + gameState.getAvailableStartPosition());
    if (gameState.isGameOver()) {
      System.out.println("The game is Over!");
    }
    System.out.println("--------------------------------------------------------------");
  }
}
