package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OceanBoardTest {
  private OceanBoard board;

  @BeforeEach
  void setUp() {
    board = new OceanBoard(10, 10);
  }

  @Test
  void testAddShip() {
    Ship ship = new Ship(ShipType.CARRIER, board, new Random(1));
    board.addShip(ship);
    assertEquals(1, board.getShipsLeft());
  }

  @Test
  void testGetShipsLeft() {
    Ship ship1 = new Ship(ShipType.CARRIER, board, new Random(1));
    Ship ship2 = new Ship(ShipType.BATTLESHIP, board, new Random(1));
    board.addShip(ship1);
    board.addShip(ship2);
    assertEquals(2, board.getShipsLeft());
  }

  @Test
  void testBoardSize() {
    assertEquals(10, board.getHeight());
    assertEquals(10, board.getWidth());
  }
}

