package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Cell;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Direction;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.util.ArrayList;

/**
 * To represent the transformation of a ship to a shipJson and vice versa
 */
public record ShipAdapterJson(@JsonProperty ("coord") Coord coord,
                              @JsonProperty ("length") int length,
                              @JsonProperty ("direction") String direction) {

  /**
   * @return a ship from a JSONShip
   */
  public Ship convertToShip() {
    boolean isHorizontal;
    ShipType shipType;
    ArrayList<Cell> cells;



    if (direction.equals(Direction.HORIZONTAL.toString())) {
      isHorizontal = true;
    } else {
      isHorizontal = false;
    }

    if (length == 6) {
      shipType = ShipType.CARRIER;
    } else if (length == 5) {
      shipType = ShipType.BATTLESHIP;
    } else if (length == 4) {
      shipType = ShipType.DESTROYER;
    } else if (length == 3) {
      shipType = ShipType.SUBMARINE;
    } else {
      throw new RuntimeException("Invalid Ship Size");
    }


    // this is to create the List of cells the ship occupies,
    // given the length, orientation, and starting coord
    cells = new ArrayList<>();

    int startingX = coord.getX();
    int startingY = coord.getY();

    for (int i = 0; i < length; i++) {
      int newX = isHorizontal ? startingX + i : startingX;
      int newY = isHorizontal ? startingY : startingY + i;

      Coord c = new Coord(newX, newY);
      cells.add(new Cell(c));
    }


    Ship ship = new Ship(isHorizontal, shipType, cells);
    return ship;
  }

  /**
   * @param ship a ship
   * @return the Ship as a JSON object
   */
  public static ShipAdapterJson shipToShipJson(Ship ship) {
    String direction =
        ship.isHorizontal() ? Direction.HORIZONTAL.toString() : Direction.VERTICAL.toString();
    Coord coord = ship.getCells().get(0).getLocation();
    int length = ship.getShipType().getLength();

    return new ShipAdapterJson(coord, length, direction);
  }
}

