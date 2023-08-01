package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * abstract class for Boards
 */
public abstract class Board {

  protected Cell[][] cells;
  protected int height;
  protected int width;

  /**
   *  board constructor
   */
  public Board(int height, int width) {
    this.height = height;
    this.width = width;

    cells = new Cell[height][width];
    // automatically set the cell's location
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        cells[i][j] = new Cell(new Coord(i, j));
      }
    }
    // Set initial cell probabilities
    setInitialProbabilities();
  }



  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public Cell getCell(int row, int col) {
    return cells[row][col];
  }

  /**
   * @return turns the board into one arrayList
   *         this gets used when creating ships because it's very easy to sort
   */
  public ArrayList<Cell> boardAsList() {
    ArrayList<Cell> boardAsList = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      boardAsList.addAll(Arrays.asList(cells[i]).subList(0, width));
    }
    return boardAsList;
  }

  /**
   * set the initial values of the cells, so we can take shots at them wisely.
   */
  private void setInitialProbabilities() {
    // Calculate the maximum distance from the center
    double maxDistance = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));

    // Set the initial probability for each cell
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // Calculate the distance from the center
        double distance = Math.sqrt(Math.pow(i - height / 2.0, 2) + Math.pow(j - width / 2.0, 2));

        // Set the initial probability (higher for cells closer to the center)
        int initialProbability = (int) (100 * (1 - distance / maxDistance));

        // Boost the probability for cells that fall on the parity grid
        if ((i + j) % 3 == 0) {
          initialProbability += 5;
        }

        cells[i][j].setProbability(initialProbability);
      }
    }
  }
}
