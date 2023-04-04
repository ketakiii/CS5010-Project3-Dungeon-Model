import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class defines the GameState class and its methods.
 */
public class GameState implements IgameState {

  private final Grid dungeon;
  private Iplayer player;
  private final int row;
  private final int column;
  private final int newConnections;
  private final double cavesWithMoreTreaure;
  private final Random random;
  private final Map<Direction, Direction> complementMap;

  /**
   * Constructor of the class GameState.
   * @param row row
   * @param column column
   * @param newConnections new Connections to be made in the grid
   * @param cavesWithMoreTreaure Percentage of caves to add treasure in
   * @param isWrapped is the dungeon wrapped or not
   * @param random random
   */
  public GameState(int row, int column, int newConnections,
      double cavesWithMoreTreaure, boolean isWrapped, Random random) {
    if (row <= 6 || column <= 6) {
      throw new IllegalArgumentException("Check the coordinates!");
    }
    if (newConnections < 0) {
      throw new IllegalArgumentException("New connections can not be less than 0");
    }
    if (cavesWithMoreTreaure < 0) {
      throw new IllegalArgumentException("% of caves that should have treasures "
          + "added should be more than 0!");
    }
    if (random == null) {
      throw new IllegalArgumentException("Random is null!");
    }
    this.row = row;
    this.column = column;
    this.random = random;
    this.newConnections = newConnections;
    this.cavesWithMoreTreaure = cavesWithMoreTreaure;
    this.complementMap = new HashMap<>();
    complementMap.put(Direction.EAST, Direction.WEST);
    complementMap.put(Direction.WEST, Direction.EAST);
    complementMap.put(Direction.NORTH, Direction.SOUTH);
    complementMap.put(Direction.SOUTH, Direction.NORTH);
    this.dungeon = new Grid(row, column, newConnections, cavesWithMoreTreaure, false,  random);
    this.player = new Player("Player");
    player.setCurrentLocation(this.dungeon.getPlayerStartLocation());
  }

  @Override public Iplayer getPlayer() {
    player = new Player("Player");
    return player;
  }

  @Override public boolean isGameOver() {
    return this.player.getCurrentLocation() == this.dungeon.getPlayerEndLocation();
  }

  @Override public List<Direction> getAvailableStartPosition() {
    Map<Direction, Ilocation> neighbours =
        this.dungeon.getNeighbours(this.player.getCurrentLocation());
    List<Direction> listOfDirections = new ArrayList<>();
    for (Direction d : neighbours.keySet()) {
      listOfDirections.add(d);
    }
    return listOfDirections;
  }

  @Override public Ilocation getPlayerStartPosition() {
    return this.dungeon.getPlayerStartLocation();
  }

  @Override public Ilocation getPlayerEndPosition() {
    return this.dungeon.getPlayerEndLocation();
  }

  @Override public Ilocation getPlayerCurrentPosition() {
    return this.player.getCurrentLocation();
  }

  @Override public Ilocation movePlayer(Direction d) {
    Map<Direction, Ilocation> neighbours =
        this.dungeon.getNeighbours(this.player.getCurrentLocation());
    Ilocation location = neighbours.get(d);
    if (location == null) {
      return null;
    }
    this.player.setCurrentLocation(location);
    if (location.getLocationType() == LocationType.TUNNEL) {
      Direction complement = complementMap.get(d);
      for (Direction direction : location.getNeighbours().keySet()) {
        if (complement != direction) {
          return movePlayer(direction);
        }
      }
    }
    return location;
  }

  @Override public Ilocation[][] getDungeon(Random random) {
    return this.dungeon.getGrid(random);
  }

  @Override public String toString() {
    return "GameState{" + "dungeon=" + dungeon + ", player=" + player + ", row=" + row
        + ", column=" + column + ", newConnections=" + newConnections + ", cavesWithMoreTreaure="
        + cavesWithMoreTreaure + ", random=" + random + '}';
  }

  @Override public String toStringStatus() {
    return this.player.toStringStatus();
  }
}
