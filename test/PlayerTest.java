import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * This class test the Player class and its methods.
 */
class PlayerTest {

  Random random;

  @Test public void testPlayer() {
    //assertThrows(IllegalArgumentException.class, () -> new Player(null));
    assertThrows(IllegalArgumentException.class, () -> new Player(""));
  }

  @Test public void testGetName() {
    Player player = new Player("Player 1");
    assertEquals("Player 1", player.getName());
  }

  @Test public void testAddTreasure() {
    random = new Random(12);
    Player player = new Player("Player 1");
    Map m = new HashMap();
    assertEquals(m.size(), player.getTreasureBag().size());
    Itreasure treasure1 = new Treasure(TreasureType.DIAMONDS, random);
    player.addTreasures(treasure1);
    m.put(TreasureType.DIAMONDS, 1);
    assertEquals(m.size(), player.getTreasureBag().size());
    Itreasure treasure2 = new Treasure(TreasureType.RUBIES, random);
    player.addTreasures(treasure2);
    m.put(TreasureType.RUBIES, 1);
    assertEquals(m.size(), player.getTreasureBag().size());
  }

  @Test public void testGetTreasureBag() {
    random = new Random(12);
    Player player = new Player("Player 1");
    Map m1 = new HashMap();
    assertEquals(m1, player.getTreasureBag());
    Itreasure treasure1 = new Treasure(TreasureType.DIAMONDS, random);
    player.addTreasures(treasure1);
    m1.put(TreasureType.DIAMONDS, 1);
    assertEquals(m1, player.getTreasureBag());
    Itreasure treasure2 = new Treasure(TreasureType.RUBIES, random);
    player.addTreasures(treasure2);
    m1.put(TreasureType.RUBIES, 1);
    assertEquals(m1, player.getTreasureBag());
    Itreasure treasure3 = new Treasure(TreasureType.RUBIES, random);
    player.addTreasures(treasure3);
    m1.put(TreasureType.RUBIES, 2);
    assertEquals(m1, player.getTreasureBag());
  }

  @Test public void testGetCurrentLocation() {
    random = new Random(12);
    Player player = new Player("Player 1");
    assertEquals(null, player.getCurrentLocation());
    Ilocation location = new Location(1, 2, random);
    player.setCurrentLocation(location);
    assertEquals("Row: 1 Column: 2",
        player.getCurrentLocation().toString());
    Ilocation location1 = new Location(3, 2, random);
    player.setCurrentLocation(location1);
    assertEquals("Row: 3 Column: 2",
        player.getCurrentLocation().toString());
    Ilocation location2 = new Location(5, 8, random);
    player.setCurrentLocation(location2);
    assertEquals("Row: 5 Column: 8",
        player.getCurrentLocation().toString());
  }

  @Test public void testToString() {
    random = new Random(12);
    Player player = new Player("Player 1");
    Itreasure treasure1 = new Treasure(TreasureType.DIAMONDS, random);
    player.addTreasures(treasure1);
    Itreasure treasure2 = new Treasure(TreasureType.RUBIES, random);
    player.addTreasures(treasure2);
    Itreasure treasure3 = new Treasure(TreasureType.RUBIES, random);
    player.addTreasures(treasure3);
    Ilocation location2 = new Location(5, 8, random);
    player.setCurrentLocation(location2);
    assertEquals("Player{name='Player 1', treasure={DIAMONDS=1, RUBIES=2}, "
        + "currentLocation=Row: 5 Column: 8}", player.toString());
  }

}