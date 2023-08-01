package cs3500.pa04.model;

/**
 * this class is responsible for representing a cell
 * this is what the board is mad of
 */
public class Cell {

  private final Coord location;
  private Ship ship;
  private CellState cellState;
  private Direction direction;
  private double probability;

  /**
   * Cell constructor
   */
  public Cell(Coord location) {
    this.location = location;
    this.ship = null;
    this.cellState = CellState.EMPTY;
    this.direction = Direction.UNKNOWN;
  }

  /**
   * @param ship which ship is occupying this cell (if there is one)
   */
  public void setShip(Ship ship) {
    this.ship = ship;
  }

  /**
   * @return the ship occupying this cell
   */
  public Ship getShip() {
    return ship;
  }

  /**
   * @return the cellState of this cell
   */
  public CellState getCellState() {
    return cellState;
  }

  /**
   * @param state the new state of the cell
   */
  public void updateCellState(CellState state) {
    cellState = state;
  }

  public Coord getLocation() {
    return location;
  }

  public void setProbability(double probability) {
    this.probability = probability;
  }

  public double getProbability() {
    return this.probability;
  }

  /**
   * @return returns direction of cell
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * @param direction sets direction of cell
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
