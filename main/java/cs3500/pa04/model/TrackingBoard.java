package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;

/**
 * the tracking board is the most basic board, it contains none of it's own felds or methods
 */
public class TrackingBoard extends Board {

  public TrackingBoard(int height, int width) {
    super(height, width);
  }


  /**
   * @param coord the coord of the cell we want
   * @return the four cells djacent to it
   */
  public List<Cell> getSurroundingCells(Coord coord) {
    int x = coord.getX();
    int y = coord.getY();

    List<Cell> adjacentCells = new ArrayList<>();

    // Check the cell above
    if (x - 1 >= 0) {
      adjacentCells.add(getCell(x - 1, y));
    }

    // Check the cell below
    if (x + 1 < height) {
      adjacentCells.add(getCell(x + 1, y));
    }

    // Check the cell to the left
    if (y - 1 >= 0) {
      adjacentCells.add(getCell(x, y - 1));
    }

    // Check the cell to the right
    if (y + 1 < width) {
      adjacentCells.add(getCell(x, y + 1));
    }

    return adjacentCells;
  }
}
