package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.view.ConsoleView;
import cs3500.pa04.view.ConsoleWriter;
import cs3500.pa04.view.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests for computer player class
 */
public class ComputerPlayerTest {

  private ComputerPlayer computerPlayer;
  private OceanBoard gameBoard;
  private TrackingBoard trackingBoard;
  private StringWriter stringWriter;

  /**
   * setup method
   */
  @BeforeEach
  public void setUp() {
    StringReader reader = new StringReader("");
    Reader reader1 = new Reader(reader);
    stringWriter = new StringWriter();
    ConsoleWriter writer = new ConsoleWriter(stringWriter);
    ConsoleView view = new ConsoleView(writer, reader1);
    Random random = new Random(0);
    computerPlayer = new ComputerPlayer("Computer", view, random, 10, 10);
    gameBoard = computerPlayer.getGameBoard();
    gameBoard.setShipsLeft(2);
    trackingBoard = computerPlayer.getTrackingBoard();
  }

  @Test
  public void testTakeShots() {
    List<Coord> shots = computerPlayer.takeShots();

    // Check that correct number of shots were taken
    assertEquals(computerPlayer.getGameBoard().getShipsLeft(), shots.size());

    // Check that each shot is unique
    for (int i = 0; i < shots.size(); i++) {
      for (int j = i + 1; j < shots.size(); j++) {
        assertNotEquals(shots.get(i), shots.get(j));
      }
    }

    // Check that each shot corresponds to a MISS cell on the tracking board
    for (Coord shot : shots) {
      assertEquals(CellState.MISS, trackingBoard.getCell(shot.getX(), shot.getY()).getCellState());
    }
  }
}

