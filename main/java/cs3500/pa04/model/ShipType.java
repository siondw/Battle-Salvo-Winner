package cs3500.pa04.model;

/**
 * To represent a type of ship
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int length;

  ShipType(int length) {
    this.length = length;
  }

  /**
   * @return the legnth of the ShipType
   */
  public int getLength() {
    return length;
  }
}
