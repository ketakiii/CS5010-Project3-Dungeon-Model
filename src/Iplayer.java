import java.util.Map;

/**
 * This interface represents the player and its attributes.
 */
public interface Iplayer {

  /**
   * Returns the name of the player.
   * @return string name
   */
  public String getName();

  /**
   * Adds the treasure in the treasure bag of the player.
   * @param treasure treasure
   */
  public void addTreasures(Itreasure treasure);

  /**
   * Gets the treasure bag of the player.
   * @return treasure bag
   */
  public Map<TreasureType, Integer> getTreasureBag();

  /**
   * Gets the current location of the player.
   * @return current location
   */
  public Ilocation getCurrentLocation();

  /**
   * Sets the current location of the player in the location we require.
   * @param location location
   */
  public void setCurrentLocation(Ilocation location);

  /**
   * Gives us the Player's details of the location coordinates and the treasure bag.
   * @return description of player
   */
  public String toStringStatus();

}
