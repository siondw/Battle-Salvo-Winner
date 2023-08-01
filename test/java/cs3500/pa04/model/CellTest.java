package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * tests for cell class
 */
public class CellTest {

  private Cell cell;
  private Coord coord;
  private Ship ship;
  private ShipType shipType;
  private OceanBoard board;
  private Random random;

  /**
   * setup method
   */
  @BeforeEach
  public void setUp() {
    coord = new Coord(2, 3);
    cell = new Cell(coord);
    shipType = ShipType.BATTLESHIP;
    board = new OceanBoard(5, 5);
    random = new Random();
    ship = new Ship(shipType, board, random);
  }


  @Test
  public void testInitialCellState() {
    assertEquals(CellState.EMPTY, cell.getCellState(), "Initial cell state should be EMPTY");
    assertNull(cell.getShip(), "Initial cell should have no ship");
    assertEquals(coord, cell.getLocation(),
        "Cell location should be equal to the coordinate provided in constructor");
  }

  @Test
  public void testSetShip() {
    cell.setShip(ship);
    assertEquals(ship, cell.getShip(), "The ship set to the cell should be the one retrieved");
  }

  @Test
  public void testUpdateCellState() {
    CellState newState = CellState.OCCUPIED;
    cell.updateCellState(newState);
    assertEquals(newState, cell.getCellState(), "Cell state should be updated to the new state");
  }

  @Test
  public void testGetLocation() {
    assertEquals(coord, cell.getLocation(),
        "Cell location should be equal to the coordinate provided in constructor");
  }

  @Test public void testGetProbability() {
    cell.setProbability(10);
    assertEquals(10, cell.getProbability());
  }
}

