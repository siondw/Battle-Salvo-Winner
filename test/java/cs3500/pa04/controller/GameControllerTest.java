package cs3500.pa04.controller;

import cs3500.pa04.view.ConsoleView;
import cs3500.pa04.view.ConsoleWriter;
import cs3500.pa04.view.Reader;
import cs3500.pa04.view.View;
import cs3500.pa04.view.Writer;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;


/**
 * game controller tests
 */
public class GameControllerTest {

  private GameController gameController;
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
    stringWriter = new StringWriter();
    writer = new ConsoleWriter(stringWriter);
    stringReader = new StringReader(
        "6 6\n1 1 1 3\n"
            +
            // Turn 1
            "0 1\n" + "0 2\n" + "0 3\n" + "0 4\n" + "0 5\n" + "1 2\n"
            +
            // Turn 2
            "1 3\n" + "1 4\n" + "2 0\n" + "2 1\n" + "2 2\n" + "2 3\n"
            // Turn 3
            + "2 4\n" + "2 5\n" + "3 2\n" + "3 3\n" + "3 4\n" + "3 5\n"
            // Turn 4
            + "4 1\n" + "4 2\n" + "4 3\n" + "5 3\n" + "5 4\n" + "5 5\n"
    );
    reader = new Reader(stringReader);
    view = new ConsoleView(writer, reader);
    Random random = new Random(0);
    gameController = new GameController(view, reader, writer, random);
  }

}

