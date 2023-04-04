import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class represents the class that implements the interface ILocation and has all its
 * methods.
 */

public class Location implements Ilocation {

  private final String locationName;
  private final int[][] coordinates = new int[1][2];
  private final HashMap<Direction, Ilocation> neighbours;
  private Itreasure treasure;
  private Random random = new Random(12);
  private LocationType locationType;

  /**
   * This method represents the constructor of the location class.
   * @param row row of the coordinates
   * @param col column of the coordinates
   * @param random random
   */

  public Location(int row, int col, Random random) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Coordinates are negative!");
    }
    
    if (random == null) {
      throw new IllegalArgumentException("Random wrong!");
    }
    this.coordinates[0][0] = row;
    this.coordinates[0][1] = col;
    this.locationName = " " + this.coordinates[0][0] + " " + this.coordinates[0][1];
    this.neighbours = new HashMap<>();
    this.random = random;
    this.treasure = null;
  }

  /**
   * Another constructor of the Location.
   * @param location location
   */
  public Location(Ilocation location) {
    if (location == null) {
      throw new IllegalArgumentException("Location is null!");
    }
    this.locationName = location.getName();
    this.coordinates[0][0] = location.getRowNumber();
    this.coordinates[0][1] = location.getColumnNumber();
    this.neighbours = (HashMap<Direction, Ilocation>) location.getNeighbours();
    this.treasure = location.getTreasure();
    this.locationType = location.getLocationType();
  }

  @Override public String getName() {
    return this.locationName;
  }

  @Override public int getRowNumber() {
    return this.coordinates[0][0];
  }

  @Override public int getColumnNumber() {
    return this.coordinates[0][1];
  }

  @Override public void attachLocation(Direction d, Ilocation location) {
    this.neighbours.put(d, location);
    this.locationType = helperLocation();
  }

  @Override public LocationType getLocationType() {
    return this.locationType;
  }

  @Override public void setTreasure(Itreasure treasure) {
    if (this.getLocationType() == LocationType.CAVE) {
      this.treasure = treasure;
    } else {
      throw new IllegalStateException("Can not set Treasure for a Tunnel!");
    }
  }

  @Override public Itreasure getTreasure() {
    return this.treasure;
  }

  @Override public Map<Direction, Ilocation> getNeighbours() {
    return this.neighbours;
  }

  @Override public String toString() {
    return "Row: " + this.coordinates[0][0] + " Column: "
        + this.coordinates[0][1];
  }

  private LocationType helperLocation() {
    if (this.neighbours.size() == 1 || this.neighbours.size() == 3 || this.neighbours.size() == 4) {
      return LocationType.CAVE;
    } else {
      return LocationType.TUNNEL;
    }
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Location location = (Location) o;
    return location.getRowNumber() == this.getRowNumber()
        && location.getColumnNumber() == this.getColumnNumber();
  }

  @Override public int hashCode() {
    return this.getRowNumber() * 10 + this.getColumnNumber();
  }
}
