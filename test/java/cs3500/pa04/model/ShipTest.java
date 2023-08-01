package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * tests for the ship class
 */
public class ShipTest {

  private Ship ship;
  private Random random;
  private ArrayList<Cell> cells;
  private ShipType shipType;

  @BeforeEach
  void setUp() {
    long seed = 12345;
    random = new Random(seed);
    cells = new ArrayList<>();
    shipType = ShipType.DESTROYER;
    ship = new Ship(shipType, new OceanBoard(10, 10), random);
  }

  @Test
  public void testGetCells() {
    ship.setCells(cells);
    assertEquals(cells, ship.getCells());
  }

  @Test
  public void testGetShipType() {
    assertEquals(shipType, ship.getShipType());
  }

  @Test
  public void testAddHit() {
    Cell cell = new Cell(new Coord(0, 0));
    ship.addHit(cell);
    assertFalse(ship.checkSunk());
  }

  @Test
  public void testIsHorizontal() {
    boolean expectedOrientation = random.nextBoolean();
    assertFalse(ship.isHorizontal());
  }


  @Test
  public void testCheckSunk() {
    assertFalse(ship.checkSunk());
    for (int i = 0; i < shipType.getLength(); i++) {
      ship.addHit(new Cell(new Coord(0, i)));
    }
    assertTrue(ship.checkSunk());
  }

  @Test
  public void testRotateShip() {
    boolean wasHorizontal = ship.isHorizontal();
    ship.rotateShip();
    assertNotEquals(wasHorizontal, ship.isHorizontal());
  }
}
