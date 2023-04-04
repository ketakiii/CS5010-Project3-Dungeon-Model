import static java.lang.Math.round;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

/**
 * This class tests the GameState class and its methods.
 */

public class GameStateTest {

  Random random;
  IgameState gameState;

  private void initialize() {
    random = new Random(12);
    gameState = new GameState(10, 10, 2, 20.5, false, random);
  }

  @Test public void testGameState() {
    initialize();
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(-2, 2, 3, 20.0, false,  random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(2, -3, 3, 30.0, false, random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(3, 3, -3, 20.5, false, random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(3, 3, 3, -30.0, false, random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(3, 3, 4, 12.5, false, null));
  }

  @Test public void testGetPlayer() {
    initialize();
    Iplayer player = new Player("Player");
    assertEquals(player.toString(), gameState.getPlayer().toString());
  }

  @Test public void testIsGameOver() {
    initialize();
    assertFalse(gameState.isGameOver());
    gameState.movePlayer(Direction.WEST);
    gameState.movePlayer(Direction.WEST);
    gameState.movePlayer(Direction.WEST);
    gameState.movePlayer(Direction.WEST);
    gameState.movePlayer(Direction.WEST);
    gameState.movePlayer(Direction.WEST);
    gameState.movePlayer(Direction.WEST);
    assertTrue(gameState.isGameOver());
  }

  @Test public void testGetAvailableStartPosition() {
    initialize();
    assertTrue(gameState.getAvailableStartPosition().contains(Direction.WEST));
    assertTrue(gameState.getAvailableStartPosition().contains(Direction.SOUTH));
    assertTrue(gameState.getAvailableStartPosition().contains(Direction.EAST));
  }

  @Test public void testGetPlayerStartPosition() {
    initialize();
    assertEquals("Row: 0 Column: 8", gameState.getPlayerStartPosition().toString());
    assertEquals(gameState.getPlayerCurrentPosition().toString(),
        gameState.getPlayerStartPosition().toString());
  }

  @Test public void testGetPlayerEndPosition() {
    initialize();
    assertEquals("Row: 0 Column: 1", gameState.getPlayerEndPosition().toString());
  }

  @Test public void testGetPlayerCurrentPosition() {
    initialize();
    assertEquals("Row: 0 Column: 8", gameState.getPlayerCurrentPosition().toString());
  }

  @Test public void testMovePlayer() {
    Random random = new Random(12);
    GameState gameState1 = new GameState(10, 10, 12, 20.5, false, random);
    assertEquals("Row: 9 Column: 4", gameState1.getPlayerCurrentPosition().toString());
    gameState1.movePlayer(Direction.NORTH);
    assertEquals("Row: 3 Column: 4", gameState1.getPlayerCurrentPosition().toString());
    gameState1.movePlayer(Direction.WEST);
    assertEquals("Row: 3 Column: 3", gameState1.getPlayerCurrentPosition().toString());
    gameState1.movePlayer(Direction.WEST);
    assertEquals("Row: 3 Column: 2", gameState1.getPlayerCurrentPosition().toString());
    gameState1.movePlayer(Direction.SOUTH);
    assertEquals("Row: 5 Column: 2", gameState1.getPlayerCurrentPosition().toString());
    gameState1.movePlayer(Direction.EAST);
    assertEquals("Row: 5 Column: 3", gameState1.getPlayerCurrentPosition().toString());
  }

  @Test public void testInterconnectivity() {
    initialize();
    GameState gameStateNoConnectivity = new GameState(10, 10, 0, 20.5, false, random);
    GameState gameStateConnectivity = new GameState(10, 10, 2, 20.5, false, random);
    int degreeNoConnectivity = 0;
    int degreeConnectivity = 0;
    Ilocation[][] gridNoConnectivity = gameStateNoConnectivity.getDungeon(random);
    Ilocation[][] gridConnectivity = gameStateConnectivity.getDungeon(random);
    for (int i = 0; i < gridNoConnectivity.length; i++) {
      for (int j = 0; j < gridNoConnectivity[i].length; j++) {
        degreeNoConnectivity += gridNoConnectivity[i][j].getNeighbours().size();
        degreeConnectivity += gridConnectivity[i][j].getNeighbours().size();
      }
    }
    degreeNoConnectivity = degreeNoConnectivity / 2;
    degreeConnectivity = degreeConnectivity / 2;
    assertEquals(degreeNoConnectivity, degreeConnectivity - 2);
  }

  @Test public void testTreasureInLocations() {
    initialize();
    Ilocation[][] grid = gameState.getDungeon(random);
    boolean isPresent = true;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].getLocationType() != LocationType.CAVE
            && grid[i][j].getTreasure() != null) {  //doubt
          isPresent = false;
        }
      }
      assertTrue(isPresent);
    }
  }

  @Test public void testTreasurePercent() {
    initialize();
    double caves = 0;
    double cavesWithTreasure = 0;
    Ilocation[][] grid = gameState.getDungeon(random);
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].getLocationType() == LocationType.CAVE) {
          caves += 1;
          if (grid[i][j].getTreasure() != null) {
            cavesWithTreasure += 1;
          }
        }
      }
    }
    int percentWithTreasure = (int) round(caves * 0.2);
    assertEquals(percentWithTreasure, cavesWithTreasure, 0.0);

  }

  @Test public void testPickingTreasure() {
    initialize();
    System.out.println(gameState.getPlayerCurrentPosition());
    Ilocation[][] grid = gameState.getDungeon(random);
    Ilocation playerLocation = gameState.getPlayerCurrentPosition();
    grid[playerLocation.getRowNumber()][playerLocation.getColumnNumber()].getNeighbours()
        .get(Direction.WEST).setTreasure(new Treasure(TreasureType.DIAMONDS, random));
    assertEquals("Current Location: Row: 0 Column: 8 Treasures: {}",
        gameState.toStringStatus());
    gameState.movePlayer(Direction.WEST);
    assertEquals("Current Location: Row: 0 Column: 7 Treasures: {DIAMONDS=1}",
        gameState.toStringStatus());
  }
  
  @Test public void testGrid() {
    initialize();
    int interconnectivity = 2;
    Ilocation[][] grid = gameState.getDungeon(random);
    int validPaths = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        validPaths += grid[i][j].getNeighbours().size();
      }
    }
    assertEquals(validPaths / 2, (long) grid.length * grid[1].length - 1 + interconnectivity);
  }

  @Test public void testPathLength() {
    initialize();
    assertTrue(distance(gameState.getPlayerStartPosition(),
        gameState.getPlayerEndPosition()) >= 5);
  }

  @Test public void testToStringStatus() {
    initialize();
    assertEquals("Current Location: Row: 0 Column: 8 Treasures: {}",
        gameState.toStringStatus());
  }

  private int distance(Ilocation start, Ilocation end) {
    initialize();
    Set<Ilocation> visitedLocations = new HashSet<>();
    Integer level = 0;
    Queue<Ilocation> objects = new LinkedList<>();
    Map<Ilocation, Integer> map = new HashMap<>();
    map.put(start, level);
    objects.add(start);

    while (!objects.isEmpty()) {
      Ilocation current = objects.remove();
      int currentLevel = map.get(current);
      if (current.equals(end)) {
        return currentLevel;
      }
      visitedLocations.add(current);
      for (Ilocation v : current.getNeighbours().values()) {
        if (v != null) {
          if (!visitedLocations.contains(v)) {
            map.put(v, currentLevel + 1);
            objects.add(v);
          }
        }
      }
    }
    return -1;
  }



}