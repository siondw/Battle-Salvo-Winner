package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * represents a board with ships
 */
public class OceanBoard extends Board {
  private final ArrayList<Ship> ships;
  private int shipsLeft;

  public OceanBoard(int height, int width) {
    super(height, width);
    ships = new ArrayList<>();
  }

  /**
   * @param ship a ship for the board to add
   */
  public void addShip(Ship ship) {
    ships.add(ship);
    shipsLeft++;
    for (Cell c : ship.getCells()) {
      c.setShip(ship);
      c.updateCellState(CellState.OCCUPIED);
    }
  }

  /**
   * @return the number of ships alive on this board
   */
  public int getShipsLeft() {
    int shipsAlive = shipsLeft;
    for (Ship s : ships) {
      if (s.checkSunk()) {
        shipsAlive--;
      }
    }
    return shipsAlive;
  }

  public void setShipsLeft(int shipsLeft) {
    this.shipsLeft = shipsLeft;
  }

  /**
   * @param ship takes in a ship
   *             finds an empty spot for it on the board and sets it there
   */
  public void setShipCells(Ship ship) {
    ArrayList<Cell> cellsAvailable = this.boardAsList().stream()
        .filter(cell -> cell.getCellState() == CellState.EMPTY)
        .collect(Collectors.toCollection(ArrayList::new));

    Collections.shuffle(cellsAvailable, ship.getRandom());

    int attempts = 0;

    ArrayList<Cell> shipCells = ship.getCells();

    while (shipCells.isEmpty() && attempts < cellsAvailable.size()) {
      Cell rootCell = cellsAvailable.get(attempts);
      shipCells = fitShip(ship, rootCell);

      if (shipCells.isEmpty()) {
        ship.rotateShip();
        shipCells = fitShip(ship, rootCell);
      }

      attempts++;
    }

    if (shipCells.isEmpty()) {
      throw new RuntimeException("No valid spot to place the ship");
    } else {
      ship.setCells(shipCells);
    }

  }


  /**
   * @param ship     the ship that needs to be placed on the board
   * @param rootCell which cell the ship starts from
   * @return if the ship will fit on the current board, it will return an arrayList
   *          of all the coordinates
   *          otherwise, it will return an empty ArrayList
   */
  private ArrayList<Cell> fitShip(Ship ship, Cell rootCell) {
    ArrayList<Cell> shipCells = new ArrayList<>();
    int shipSize = ship.getShipType().getLength();

    int rootRow = rootCell.getLocation().getX();
    int rootCol = rootCell.getLocation().getY();

    for (int i = 0; i < shipSize; i++) {
      int newCol = ship.isHorizontal() ? rootRow : rootRow + i;
      int newRow = ship.isHorizontal() ? rootCol + i : rootCol;

      if (newRow < this.getHeight() && newCol < this.getWidth()) {
        Cell newCell = this.getCell(newRow, newCol);

        if (newCell.getCellState() == CellState.EMPTY) {
          shipCells.add(newCell);
        } else {
          return new ArrayList<>();
        }
      } else {
        return new ArrayList<>();
      }
    }

    return shipCells;
  }
}


