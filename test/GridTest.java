import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

/**
 * This class tests the Grid class and its methods.
 */
public class GridTest {
  Random random = new Random(12);

  @Test public void testGrid() {
    Grid grid = new Grid(7, 7, 3, 22.4, false,  random);

  }

  @Test public void testBuildMatrix() {
    Grid grid = new Grid(6, 6, 2, 20.5, false, random);
    grid.buildMatrix();
    grid.matrixToMst();
    Random random = new Random(12);
    Ilocation location = new Location(2, 2, random);
  }


}