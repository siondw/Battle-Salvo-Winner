package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.view.ConsoleView;
import cs3500.pa04.view.ConsoleWriter;
import cs3500.pa04.view.Reader;
import cs3500.pa04.view.View;
import cs3500.pa04.view.Writer;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * tests on the model class
 */
public class ModelTest {

  private Model model;
  private Aplayer player1;
  private Aplayer player2;
  private OceanBoard player1GameBoard;
  private TrackingBoard player1TrackingBoard;
  private OceanBoard player2GameBoard;
  private TrackingBoard player2TrackingBoard;
  private StringWriter stringWriter;
  private StringReader stringReader;
  private Reader reader;
  private Writer writer;
  private View view;


  /**
   * setup method
   */
  @BeforeEach
  public void setUp() {
    // Set up Boards for each player
    player1GameBoard = new OceanBoard(10, 10);
    player1TrackingBoard = new TrackingBoard(10, 10);
    player2GameBoard = new OceanBoard(10, 10);
    player2TrackingBoard = new TrackingBoard(10, 10);
    stringWriter = new StringWriter();
    writer = new ConsoleWriter(stringWriter);
    stringReader = new StringReader(
        ""
    );
    reader = new Reader(stringReader);
    view = new ConsoleView(writer, reader);
    Random random = new Random(0);
    // Set up Players
    player1 = new HumanPlayer("Human", view, random, 10, 10);
    player2 = new ComputerPlayer("Computer", view, random, 10, 10);

    model = new Model(player1, player2, player1GameBoard, player1TrackingBoard,
        player2GameBoard, player2TrackingBoard);
  }

  @Test
  public void testIsGameOver() {
    // We can test isGameOver by setting up different game
    // states and checking if it returns the correct value.


    // Game is not over, both players have ships
    player1GameBoard.setShipsLeft(2);
    player2GameBoard.setShipsLeft(2);
    assertFalse(model.isGameOver());

    // Player1's ships are all sunk
    player1GameBoard.setShipsLeft(0);
    player2GameBoard.setShipsLeft(2);
    assertTrue(model.isGameOver());
    assertEquals("All your ships are sunk!", model.getPlayer1Reason());
    assertEquals(GameResult.LOSE, model.getPlayer1Result());

    // Player2's ships are all sunk
    player1GameBoard.setShipsLeft(2);
    player2GameBoard.setShipsLeft(0);
    assertTrue(model.isGameOver());
    assertEquals("You sank all the enemy's ships!", model.getPlayer1Reason());
    assertEquals(GameResult.WIN, model.getPlayer1Result());

    // All ships are sunk
    player1GameBoard.setShipsLeft(0);
    player2GameBoard.setShipsLeft(0);
    assertTrue(model.isGameOver());
    assertEquals("Everybody's ships are sunk!", model.getPlayer1Reason());
    assertEquals(GameResult.DRAW, model.getPlayer1Result());
  }

  @Test
  public void testSetup() {
    Map<ShipType, Integer> shipMap = new HashMap<>();
    shipMap.put(ShipType.CARRIER, 1);
    shipMap.put(ShipType.BATTLESHIP, 1);
    shipMap.put(ShipType.DESTROYER, 1);
    shipMap.put(ShipType.SUBMARINE, 1);

    model.setup(10, 10, shipMap);

    OceanBoard player1GameBoard = player1.getGameBoard();
    OceanBoard player2GameBoard = player2.getGameBoard();

    assertEquals(10, player2GameBoard.getWidth());

    // Check that player1's OceanBoard was set up correctly
    assertEquals(10, player1GameBoard.getWidth());
    assertEquals(10, player1GameBoard.getHeight());
    assertEquals(4, player1GameBoard.getShipsLeft());

    // Check that player2's OceanBoard was set up correctly

    assertEquals(10, player2GameBoard.getHeight());
    assertEquals(4, player2GameBoard.getShipsLeft());
  }


}
