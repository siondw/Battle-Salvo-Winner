package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.model.Cell;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * class to test shipAdapter
 */
public class ShipAdapterJsonTest {

  @Test
  public void testConvertToShip() {
    Coord coord = new Coord(0, 0);

    // Test horizontal and each ship type
    for (int length = 3; length <= 6; length++) {
      ShipAdapterJson adapterJson = new ShipAdapterJson(coord, length, "HORIZONTAL");
      Ship ship = adapterJson.convertToShip();

      assertEquals(length, ship.getShipType().getLength());
      assertEquals(true, ship.isHorizontal());
      assertCells(coord, length, true, ship.getCells());
    }

    // Test vertical and each ship type
    for (int length = 3; length <= 6; length++) {
      ShipAdapterJson adapterJson = new ShipAdapterJson(coord, length, "VERTICAL");
      Ship ship = adapterJson.convertToShip();

      assertEquals(length, ship.getShipType().getLength());
      assertEquals(false, ship.isHorizontal());
      assertCells(coord, length, false, ship.getCells());
    }

    // Test invalid length
    assertThrows(RuntimeException.class, () ->
        new ShipAdapterJson(coord, 7, "HORIZONTAL").convertToShip());
  }

  // Helper method to check the cells created for the ship
  private void assertCells(Coord coord, int length, boolean isHorizontal, List<Cell> cells) {
    for (int i = 0; i < length; i++) {
      int expectedX = isHorizontal ? coord.getX() + i : coord.getX();
      int expectedY = isHorizontal ? coord.getY() : coord.getY() + i;

      Cell cell = cells.get(i);
      assertEquals(expectedX, cell.getLocation().getX());
      assertEquals(expectedY, cell.getLocation().getY());
    }
  }

  @Test
  public void testShipToShipJson() {
    Coord coord = new Coord(0, 0);
    ArrayList<Cell> cells;

    // Test horizontal and each ship type
    for (ShipType type : ShipType.values()) {
      cells = generateCells(coord, type.getLength(), true);
      Ship ship = new Ship(true, type, cells);

      ShipAdapterJson adapterJson = ShipAdapterJson.shipToShipJson(ship);

      assertEquals(true, adapterJson.direction().equals("HORIZONTAL"));
      assertEquals(type.getLength(), adapterJson.length());
      assertEquals(coord, adapterJson.coord());
    }

    // Test vertical and each ship type
    for (ShipType type : ShipType.values()) {
      cells = generateCells(coord, type.getLength(), false);
      Ship ship = new Ship(false, type, cells);

      ShipAdapterJson adapterJson = ShipAdapterJson.shipToShipJson(ship);

      assertEquals(true, adapterJson.direction().equals("VERTICAL"));
      assertEquals(type.getLength(), adapterJson.length());
      assertEquals(coord, adapterJson.coord());
    }
  }

  // Helper method to generate a list of cells for a ship
  private ArrayList<Cell> generateCells(Coord startCoord, int length, boolean isHorizontal) {
    ArrayList<Cell> cells = new ArrayList<>();

    for (int i = 0; i < length; i++) {
      int x = isHorizontal ? startCoord.getX() + i : startCoord.getX();
      int y = isHorizontal ? startCoord.getY() : startCoord.getY() + i;

      cells.add(new Cell(new Coord(x, y)));
    }

    return cells;
  }
}
