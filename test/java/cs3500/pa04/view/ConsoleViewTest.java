package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




/**
 * tests for consoleView class
 */
public class ConsoleViewTest {
  private ConsoleView view;
  private ByteArrayOutputStream outputStream;
  private Writer writer;
  private Reader reader;
  private Random random;

  /**
   * setup method
   */
  @BeforeEach
  public void setup() {
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    writer = new ConsoleWriter(printStream);
    reader = new Reader(new StringReader(""));
    view = new ConsoleView(writer, reader);
    random = new Random(1);
  }

  @Test
  public void testDisplayWelcomeMessage() {
    view.displayWelcomeMessage();
    assertEquals("Hello! Welcome to the OOD BattleSalvo Game! \n", outputStream.toString());
  }

  @Test
  public void testDisplayMessage() {
    String message = "Test message.";
    view.displayMessage(message);
    assertEquals(message, outputStream.toString());
  }


}
