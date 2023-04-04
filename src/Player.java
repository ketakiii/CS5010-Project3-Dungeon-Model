import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the class that implements the interface IPlayer and has all its
 * methods.
 */
public class Player implements Iplayer {

  private final String name;
  private final Map<TreasureType, Integer> treasureBag;
  private Ilocation currentLocation;

  /**
   * This method represents the constructor of the Player class.
   * @param name name of the player
   */
  public Player(String name) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Player is null");
    }
    this.name = name;
    this.treasureBag = new HashMap<>();
    this.currentLocation = null;
  }

  @Override public String getName() {
    return this.name;
  }

  @Override public void addTreasures(Itreasure treasure) {
    if (treasureBag.isEmpty()) {
      treasureBag.put(treasure.getTreasureType(), 1);
    } else {
      for (TreasureType t : this.treasureBag.keySet()) {
        if (treasure.getTreasureType().equals(t)) {
          treasureBag.put(treasure.getTreasureType(), treasureBag.get(t) + 1);
        } else {
          treasureBag.put(treasure.getTreasureType(), 1);
        }
      }
    }
  }

  @Override public Map<TreasureType, Integer> getTreasureBag() {
    return this.treasureBag;
  }

  @Override public Ilocation getCurrentLocation() {
    return this.currentLocation;
  }

  @Override public void setCurrentLocation(Ilocation location) {
    if (location != null) {
      if (location.getTreasure() != null) {
        addTreasures(location.getTreasure());
        location.setTreasure(null);
      }
      this.currentLocation = location;
    } else {
      throw new IllegalArgumentException("Location is null!");
    }
  }

  @Override public String toStringStatus() {
    return "Current Location: " + this.currentLocation + " Treasures: " + this.getTreasureBag();
  }

  @Override public String toString() {
    return "Player{" + "name='" + name + '\'' + ", treasure="
        + treasureBag + ", currentLocation=" + currentLocation + '}';
  }

}
