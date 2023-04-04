import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * This class defines the Grid and its methods.
 */
public class Grid {

  private Random random;
  private final Ilocation[][] dungeon;
  private Ilocation startLocation;
  private Ilocation endLocation;
  private final int[][] matrixRepresentation;
  private Direction direction;
  private List<TreasureType> treasureList;
  private List<int[]> connectedInMst;
  private List<int[]> notConnectedInMst;
  private int newConnections;
  private double cavesWithMoreTreaure;
  private final Set<Ilocation> neighbourList;
  private boolean isWrapped = false;

  /**
   * Constructor of the Grid class.
   * @param row row
   * @param column column
   * @param newConnections new Connections to be made in the grid
   * @param cavesWithMoreTreasure Percentage of caves to add treasure in
   * @param isWrapped if the wrapping ability is available
   * @param random random
   */
  public Grid(int row, int column, int newConnections,
      double cavesWithMoreTreasure, boolean isWrapped, Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random is null!");
    }
    if (row < 0 || column < 0) {
      throw new IllegalArgumentException("Check the coordinates, less than zero!");
    }
    if (newConnections < 0) {
      throw new IllegalArgumentException("new connections made can not be negative!");
    }
    if (cavesWithMoreTreasure < 0) {
      throw new IllegalArgumentException("% of caves that should have treasures added "
          + "should be more than 0!");
    }
    random = new Random(12);
    this.isWrapped = isWrapped;
    this.newConnections = newConnections;
    this.cavesWithMoreTreaure = cavesWithMoreTreasure;
    this.dungeon = new Ilocation[row][column];
    for (int i = 0; i < this.dungeon.length; i++) {
      for (int j = 0; j < this.dungeon[i].length; j++) {
        this.dungeon[i][j] = new Location(i, j, random);
      }
    }
    this.matrixRepresentation = new int[row * column][row * column];
    this.buildMatrix();
    this.matrixToMst();
    this.addInterconnectivity(newConnections, random);
    this.assignTreasureToCaves(cavesWithMoreTreasure, random);
    this.neighbourList = new HashSet<>();
    this.startAndEndLocation(random);
  }

  private int getNumberFromCoordinate(int i, int j, int col) {
    return i * col + j;
  }

  private int[] getCoordinateFromNumber(int num, int col) {
    int[] coordinate = new int[2];
    coordinate[0] = num / col;
    coordinate[1] = num % col;
    return coordinate;
  }

  private boolean isValidCoordinate(int i, int j, int row, int col) {
    return (i >= 0 && j >= 0 && i < row && j < col);
  }

  private List<int[]> getNeighbourForMaze(int i, int j, int row, int col, boolean isWrapped) {
    List<int[]> neighbourList = new ArrayList<>();
    int[] temp;
    // North
    temp = new int[2];
    if (isWrapped) {
      temp[0] = (i - 1 + row) % row;
    } else {
      temp[0] = i - 1;
    }
    temp[1] = j;
    if (isValidCoordinate(temp[0], temp[1], row, col)) {
      neighbourList.add(temp);
    }
    // South
    temp = new int[2];
    if (isWrapped) {
      temp[0] = (i + 1 + row) % row;
    } else {
      temp[0] = i + 1;
    }
    temp[1] = j;
    if (isValidCoordinate(temp[0], temp[1], row, col)) {
      neighbourList.add(temp);
    }
    // East
    temp = new int[2];
    temp[0] = i;
    if (isWrapped) {
      temp[1] = (j + 1 + col) % col;
    } else {
      temp[1] = j + 1;
    }
    if (isValidCoordinate(temp[0], temp[1], row, col)) {
      neighbourList.add(temp);
    }
    // West
    temp = new int[2];
    temp[0] = i;
    if (isWrapped) {
      temp[1] = (j - 1 + col) % col;
    } else {
      temp[1] = j - 1;
    }
    if (isValidCoordinate(temp[0], temp[1], row, col)) {
      neighbourList.add(temp);
    }
    return neighbourList;
  }

  /**
   * Method for building the matrix.
   */
  public void buildMatrix() {
    for (int i = 0; i < this.dungeon.length; i++) {
      for (int j = 0; j < this.dungeon[i].length; j++) {
        int num = getNumberFromCoordinate(i, j, dungeon[0].length); // current maze number
        List<int[]> neighbourList = getNeighbourForMaze(i, j, dungeon.length,
            dungeon[0].length, this.isWrapped);
        for (int[] neighbour : neighbourList) {
          int neighbourNumber = getNumberFromCoordinate(neighbour[0], neighbour[1],
              dungeon[0].length);
          this.matrixRepresentation[num][neighbourNumber] = 1;
        }
      }
    }
  }

  private boolean isNotInList(List<int[]> connectedInList, int row, int col) {
    for (int[] elem : connectedInList) {
      if (elem[0] == row && elem[1] == col) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method converts the matrix to MST.
   */
  public void matrixToMst() {
    int[][] matrix = this.matrixRepresentation;
    Mst mst = new Mst(matrix.length);
    connectedInMst = mst.primMst(matrix);
    notConnectedInMst = new ArrayList<>();
    for (int i = 0; i < this.matrixRepresentation.length; i++) {
      for (int j = 0; j < this.matrixRepresentation[i].length; j++) {
        if (this.matrixRepresentation[i][j] == 1) {
          if (!isNotInList(connectedInMst, i, j)) {
            notConnectedInMst.add(new int[]{i, j});
          }
        }
      }
    }
    for (int[] i : connectedInMst) {
      //      System.out.println("connectedInMST -> " + Arrays.toString(i));
      int[] coord1 = this.getCoordinateFromNumber(i[0], this.dungeon[0].length);
      int[] coord2 = this.getCoordinateFromNumber(i[1], this.dungeon[0].length);
      //System.out.println(Arrays.toString(this.getCoordinateFromNumber(i[0], this.dungeon.length))
      //System.out.println(Arrays.toString(this.getCoordinateFromNumber(i[1], this.dungeon.length)
      Ilocation location1 = this.dungeon[coord1[0]][coord1[1]];
      Ilocation location2 = this.dungeon[coord2[0]][coord2[1]];
      location1.attachLocation(this.getDirection(coord1, coord2), location2);
      location2.attachLocation(this.getDirection(coord2, coord1), location1);
    }
    //for (int[] j : notConnectedInMST) {
    //System.out.println("notConnectedInMST -> " + Arrays.toString(j));
    //System.out.println(Arrays.toString(this.getCoordinateFromNumber(j[0], this.dungeon.length)));
    //System.out.println(Arrays.toString(this.getCoordinateFromNumber(j[1], this.dungeon.length)));
    //}
  }

  private Direction getDirection(int[] i, int[] j) {
    if (j[0] - i[0] > 0) {
      this.direction = Direction.SOUTH;
    }  else if (j[0] - i[0] < 0) {
      this.direction = Direction.NORTH;
    } else if (j[0] - i[0] == 0) {
      if (j[1] - i[1] > 0) {
        this.direction = Direction.EAST;
      } else if (j[1] - i[1] < 0) {
        this.direction = Direction.WEST;
      } else {
        throw new IllegalArgumentException("Check coordinates!");
      }
    }
    return this.direction;
  }

  private void addInterconnectivity(int connections, Random random) {
    for (int i = 0; i < connections; i++) {
      int randomIndex = this.helperInit(0, this.notConnectedInMst.size(), random);
      int[] randomConnections = notConnectedInMst.get(randomIndex);
      int[] coord1 = this.getCoordinateFromNumber(randomConnections[0], this.dungeon[0].length);
      int[] coord2 = this.getCoordinateFromNumber(randomConnections[1], this.dungeon[0].length);
      Ilocation location1 = this.dungeon[coord1[0]][coord1[1]];
      Ilocation location2 = this.dungeon[coord2[0]][coord2[1]];
      location1.attachLocation(this.getDirection(coord1, coord2), location2);
      location2.attachLocation(this.getDirection(coord2, coord1), location1);
      this.notConnectedInMst.remove(randomIndex);
    }
  }

  private List<Ilocation> getAllCaves() {
    List<Ilocation> caveList = new ArrayList<>();
    for (Ilocation[] locations : this.dungeon) {
      for (Ilocation location : locations) {
        if (location.getLocationType() == null) {
          throw new IllegalArgumentException("Location type is null!");
        } else if (location.getLocationType() == LocationType.CAVE) {
          caveList.add(location);
        }
      }
    }
    return caveList;
  }

  /**
   * Assigns treasures to random caves.
   * @param percentage percentage
   */
  private void assignTreasureToCaves(double percentage, Random random) {
    treasureList = new ArrayList<>();
    treasureList.add(TreasureType.DIAMONDS);
    treasureList.add(TreasureType.RUBIES);
    treasureList.add(TreasureType.SAPPHIRES);
    List<Ilocation> caveList1 = this.getAllCaves();
    int noOfCavesToAssignTreasure = (int) Math.round((percentage / 100
        * this.getAllCaves().size()));
    for (int i = 0; i < noOfCavesToAssignTreasure; i++) {
      int randomIndex = this.helperInit(0, caveList1.size(), random);
      int treasureIndex = this.helperInit(0, treasureList.size(), random);
      Itreasure treasure = new Treasure(this.treasureList.get(treasureIndex), random);
      caveList1.get(randomIndex).setTreasure(treasure);
      caveList1.remove(randomIndex);
    }
  }

  private int helperInit(int constant, int upperBound, Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random is initialized wrong!");
    } else {
      int intRandom = random.nextInt(upperBound);
      return intRandom + constant;
    }
  }

  private void startAndEndLocation(Random random) {
    List<Ilocation> caveList1 = this.getAllCaves();
    int randomIndex = this.helperInit(0, caveList1.size(), random);
    this.startLocation = caveList1.get(randomIndex);
    getNnearestNeighbours(this.startLocation, 5);
    List<Ilocation> caveList2 = new ArrayList<>();
    for (Ilocation ilocation : caveList1) {
      if (!this.neighbourList.contains(ilocation)) {
        caveList2.add(ilocation);
      }
    }
    randomIndex = this.helperInit(0, caveList2.size(), random);
    this.endLocation = caveList2.get(randomIndex);
  }

  private void getNnearestNeighbours(Ilocation ilocation, int depth) {
    this.neighbourList.add(ilocation);
    if (depth == 0) {
      return;
    }
    Map<Direction, Ilocation> neighbours = ilocation.getNeighbours();
    for (Direction neighbourDirection : neighbours.keySet()) {
      Ilocation neighbourLocation = neighbours.get(neighbourDirection);
      if (!this.neighbourList.contains(neighbourLocation)) {
        if (neighbourLocation.getLocationType() == LocationType.CAVE) {
          getNnearestNeighbours(neighbourLocation, depth - 1);
        } else {
          getNnearestNeighbours(neighbourLocation, depth);
        }
      }
    }
  }

  protected Ilocation getPlayerStartLocation() {
    return this.startLocation;
  }

  protected Ilocation getPlayerEndLocation() {
    return this.endLocation;
  }

  protected Map<Direction, Ilocation> getNeighbours(Ilocation location) {
    if (location == null) {
      throw new IllegalArgumentException("Location is null!");
    } else {
      return this.dungeon[location.getRowNumber()][location.getColumnNumber()].getNeighbours();
    }
  }

  protected Ilocation[][] getGrid(Random random) {
    Ilocation[][] gridCopy = new Ilocation[this.dungeon.length][this.dungeon[1].length];
    for (int i = 0; i < this.dungeon.length; i++) {
      for (int j = 0; j < this.dungeon[i].length; j++) {
        gridCopy[i][j] = new Location(this.dungeon[i][j]);
      }
    }
    return gridCopy;
  }


}

