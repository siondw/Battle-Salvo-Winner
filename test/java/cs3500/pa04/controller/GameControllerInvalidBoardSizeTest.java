package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.view.ConsoleView;
import cs3500.pa04.view.ConsoleWriter;
import cs3500.pa04.view.Reader;
import cs3500.pa04.view.View;
import cs3500.pa04.view.Writer;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test for controller receiving invalid board size
 */
public class GameControllerInvalidBoardSizeTest {

  private GameController gameController;
  private StringWriter stringWriter;
  private StringReader stringReader;
  private Reader reader;
  private Writer writer;
  private View view;

  /**
   * setup method for invalid board
   */
  @BeforeEach
  public void setUpInvalidBoardSize() {
    stringWriter = new StringWriter();
    writer = new ConsoleWriter(stringWriter);
    stringReader = new StringReader(
        "3 2\n 6 6\n1 1 1 3\n"
        // Shots for Player 1
        + "0 1\n0 2\n0 3\n0 4\n0 5\n"   // Sinking battleship
        + "1 2\n1 3\n1 4\n"              // Sinking submarine
        + "2 0\n2 1\n2 2\n2 3\n2 4\n2 5\n"  // Sinking carrier
        + "3 2\n3 3\n3 4\n3 5\n"              // Sinking destroyer
        + "4 1\n4 2\n4 3\n"              // Sinking submarine
        + "5 3\n5 4\n5 5\n"
    );
    reader = new Reader(stringReader);
    view = new ConsoleView(writer, reader);
    Random random = new Random(0); // deterministic random seed
    gameController = new GameController(view, reader, writer, random);
  }

  @Test
  public void testInitializeGameInvalidBoardSize() {
    gameController.initializeGame();
    String output = stringWriter.toString();
    assertTrue(output.contains("You've entered invalid dimensions."));
  }
}