package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * represents a ship
 */
public class Ship {
  private final ShipType shipType;
  private ArrayList<Cell> cells;
  private final ArrayList<Cell> hitSquares;
  private boolean isHorizontal;
  private final boolean isSunk;
  private final Random random;

  /**
   * Constructor for ship
   */
  public Ship(ShipType shipType, OceanBoard oceanBoard, Random random) {
    this.shipType = shipType;
    this.random = random;
    isHorizontal = random.nextBoolean();
    isSunk = false;
    cells = new ArrayList<>();
    hitSquares = new ArrayList<>();
  }

  /**
   * Second constructor for ship
   */
  public Ship(boolean isHorizontal, ShipType shipType, ArrayList<Cell> cells) {
    this.isHorizontal = isHorizontal;
    this.shipType = shipType;
    this.cells = cells;
    this.isSunk = false;
    this.hitSquares = new ArrayList<>();
    this.random = new Random();
  }


  /**
   * @return the cells that this ship occupies
   */
  public ArrayList<Cell> getCells() {
    return cells;
  }

  /**
   * @return the ship type
   */
  public ShipType getShipType() {
    return shipType;
  }


  public void addHit(Cell cell) {
    hitSquares.add(cell);
  }

  public boolean isHorizontal() {
    return isHorizontal;
  }

  /**
   * @return checks if ship is sunk
   */
  public boolean checkSunk() {

    int shipSize = shipType.getLength();
    return (hitSquares.size() == (shipSize));
  }

  /**
   * @return gets status of the ship
   */
  public boolean getShipSunkStatus() {
    return isSunk;
  }

  /**
   * rotates the ship
   */
  public void rotateShip() {
    this.isHorizontal = !this.isHorizontal;
  }

  public Random getRandom() {
    return random;
  }

  public void setCells(ArrayList<Cell> cells) {
    this.cells = cells;
  }
}
