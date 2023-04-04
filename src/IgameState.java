import java.util.List;
import java.util.Random;

/**
 * This is an interface of the GameState interface and its defined methods.
 */
public interface IgameState {

  /**
   * Gives us the player created in the game state.
   * @return player
   */
  public Iplayer getPlayer();

  /**
   * Checks if the game is over.
   * @return if game is over
   */
  public boolean isGameOver();

  /**
   * Checks all possible directions the player can move in.
   * @return list of directions
   */
  public List<Direction> getAvailableStartPosition();

  /**
   * Returns the start position of the player.
   * @return start location
   */
  public Ilocation getPlayerStartPosition();

  /**
   * Returns the end location of the player.
   * @return end location
   */
  public Ilocation getPlayerEndPosition();

  /**
   * Returns the current position of the player.
   * @return current location
   */
  public Ilocation getPlayerCurrentPosition();

  /**
   * Moves the player.
   * @param direction direction
   * @return location where the player moved
   */
  public Ilocation movePlayer(Direction direction);

  /**
   * Returns the dungeon of the game state.
   * @param random random
   * @return the grid
   */
  public Ilocation[][] getDungeon(Random random);

  /**
   * Returns the player's details.
   * @return string of player details
   */
  public String toStringStatus();

}
