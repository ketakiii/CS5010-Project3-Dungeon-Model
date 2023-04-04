import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * This class test the Location class and its methods.
 */
class LocationTest {
  Random random;

  @Test public void testLocation() {
    random = new Random();
    assertThrows(IllegalArgumentException.class, () -> new Location(-3, 3, random));
    assertThrows(IllegalArgumentException.class, () -> new Location(2, 4, null));
  }

  @Test public void testGetRowNumber() {
    random = new Random(12);
    Ilocation location = new Location(2, 4, random);
    assertEquals(2, location.getRowNumber());
  }

  @Test public void testGetColumnNumber() {
    random = new Random(12);
    Ilocation location = new Location(2, 4, random);
    assertEquals(4, location.getColumnNumber());
  }

  @Test public void testAttachLocation() {
    random = new Random(12);
    Ilocation location = new Location(2, 4, random);
    location.attachLocation(Direction.NORTH, location);
    Map m = new HashMap();
    m.put(Direction.NORTH, location);
    assertEquals(m, location.getNeighbours());
    Ilocation location1 = new Location(2, 4, random);
    location.attachLocation(Direction.SOUTH, location);
    m.put(Direction.SOUTH, location1);
    assertEquals(m, location.getNeighbours());
  }

  @Test public void testGetLocationType() {
    random = new Random(12);
    Ilocation location = new Location(2, 4, random);
    location.attachLocation(Direction.NORTH, location);
    LocationType locationType = LocationType.CAVE;
    assertEquals(locationType, location.getLocationType());
  }

  @Test public void setTreasure() {
    random = new Random(12);
    Ilocation location = new Location(2, 4, random);
    location.attachLocation(Direction.NORTH, location);
    TreasureType treasureType = TreasureType.DIAMONDS;
    Itreasure treasure = new Treasure(treasureType, random);
    location.setTreasure(treasure);
    assertEquals(1, location.getTreasure().getTreasure().size());
  }

  @Test public void getTreasure() {
    random = new Random(12);
    Ilocation location = new Location(2, 4, random);
    location.attachLocation(Direction.NORTH, location);
    TreasureType treasureType = TreasureType.DIAMONDS;
    Itreasure treasure = new Treasure(treasureType, random);
    location.setTreasure(treasure);
    assertEquals("Treasure{treasury={DIAMONDS=2}, MAX_TREASURE_QUANTITY=1, "
        + "MAX_TREASURE_QUANTITY=5}", location.getTreasure().toString());
  }

  @Test public void testGetNeighbours() {
    random = new Random(12);
    Ilocation location = new Location(2, 4, random);
    location.attachLocation(Direction.NORTH, location);
    Map m = new HashMap();
    m.put(Direction.NORTH, location);
    assertEquals(m, location.getNeighbours());
    Ilocation location1 = new Location(2, 0, random);
    location.attachLocation(Direction.WEST, location1);
    Map m1 = new HashMap();
    m1.put(Direction.SOUTH, location);
    Ilocation location2 = new Location(2, 2, random);
    location.attachLocation(Direction.EAST, location2);
    Map m2 = new HashMap();
    m2.put(Direction.EAST, location2);
    Ilocation location3 = new Location(2, 3, random);
    location.attachLocation(Direction.SOUTH, location3);
  }

  @Test public void testToString() {
    random = new Random(12);
    Ilocation location = new Location(2, 4, random);
    assertEquals("Row: 2 Column: 4", location.toString());
  }
}