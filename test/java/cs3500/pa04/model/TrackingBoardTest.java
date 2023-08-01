package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;


/**
 * test for trackingBoard claass
 */
public class TrackingBoardTest {

  @Test
  public void testBoardCreation() {
    TrackingBoard trackingBoard = new TrackingBoard(6, 6);
    assertNotNull(trackingBoard);
  }

  @Test
  public void testGetHeight() {
    TrackingBoard trackingBoard = new TrackingBoard(6, 6);
    assertEquals(6, trackingBoard.getHeight());
  }


  @Test
  public void testGetWidth() {
    TrackingBoard trackingBoard = new TrackingBoard(6, 6);
    assertEquals(6, trackingBoard.getWidth());
  }

  @Test
  public void testGetCell() {
    TrackingBoard trackingBoard = new TrackingBoard(6, 6);
    Cell cell = trackingBoard.getCell(0, 0);
    assertNotNull(cell);
    assertEquals(0, cell.getLocation().getX());
    assertEquals(0, cell.getLocation().getY());
  }

  @Test
  public void testBoardAsList() {
    TrackingBoard trackingBoard = new TrackingBoard(6, 6);
    ArrayList<Cell> cells = trackingBoard.boardAsList();
    assertEquals(36, cells.size()); // 6*6
    for (Cell cell : cells) {
      assertNotNull(cell);
    }
  }

  @Test
  public void testInvalidGetCell() {
    TrackingBoard trackingBoard = new TrackingBoard(6, 6);
    try {
      trackingBoard.getCell(10, 10); // Invalid cell
      fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
    } catch (ArrayIndexOutOfBoundsException e) {
      assertEquals("Index 10 out of bounds for length 6", e.getMessage());
    }
  }

}
