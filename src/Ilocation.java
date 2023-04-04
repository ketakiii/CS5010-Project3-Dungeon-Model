import java.util.Map;

/**
 * This interface represents the location and its attributes.
 */
public interface Ilocation {

  /**
   * Returns the name of the locations : coordinates.
   * @return string of coordinates
   */
  public String getName();

  /**
   * Returns the row coordinate.
   * @return row coordinate
   */
  public int getRowNumber();

  /**
   * Returns the column coordinate.
   * @return column coordinate
   */
  public int getColumnNumber();

  /**
   * Attaches the two locations in the direction mentioned.
   * @param d direction
   * @param location location
   */
  public void attachLocation(Direction d, Ilocation location);

  /**
   * Sets the treasure as mentioned in the parameter.
   * @param treasure treasure to be set
   */
  public void setTreasure(Itreasure treasure);

  /**
   * Gets the treasure in the location we need.
   * @return treasure
   */
  public Itreasure getTreasure();

  /**
   * Gets the neighbours of a location and their direction.
   * @return map of direction and location
   */
  public Map<Direction, Ilocation> getNeighbours();

  /**
   * Returns the location type of the current location.
   * @return location type
   */
  public LocationType getLocationType();
}
