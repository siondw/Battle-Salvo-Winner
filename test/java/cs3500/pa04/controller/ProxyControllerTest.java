package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.Mocket;
import cs3500.pa04.model.Aplayer;
import cs3500.pa04.model.ComputerPlayer;
import cs3500.pa04.model.HumanPlayer;
import cs3500.pa04.view.ConsoleView;
import cs3500.pa04.view.ConsoleWriter;
import cs3500.pa04.view.Reader;
import cs3500.pa04.view.View;
import cs3500.pa04.view.Writer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController controller;

  View view;
  private StringWriter stringWriter;
  private StringReader stringReader;
  private Reader reader;
  private Writer writer;
  private Aplayer player1;
  private Aplayer player2;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());

    stringWriter = new StringWriter();
    writer = new ConsoleWriter(stringWriter);
    stringReader = new StringReader("");
    reader = new Reader(stringReader);
    view = new ConsoleView(writer, reader);
    Random random = new Random(0);
    // Set up Players
    player1 = new HumanPlayer("Human", view, random, 10, 10);
    player2 = new ComputerPlayer("Computer", view, random, 10, 10);
  }

  @Test
  public void testHandleJoin() throws IOException {
    List<String> serverMessages = Arrays.asList(
        "{\"method-name\":\"join\",\"arguments\":{}}",
        "{\"method-name\":\"setup\",\"arguments\":{\"width\":10,\"height\":10,\"fleet-spec\":{}}}"
    );
    Mocket mocket = new Mocket(testLog, serverMessages);
    ProxyController proxyController = new ProxyController(mocket, player1);

    proxyController.run();

    String logOutput = testLog.toString();
    assertTrue(logOutput.contains("\"method-name\":\"join\""));
    assertTrue(logOutput.contains("\"method-name\":\"setup\""));
  }



  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void testJoin() throws IOException {
    JoinJson joinJson = new JoinJson("siondw", "SINGLE");
    JsonNode sampleMessage = createSampleMessage("join", joinJson);

    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    try {
      this.controller = new ProxyController(socket, player1);
    } catch (AssertionFailedError | IOException e) {
      fail();
    }

    this.controller.run();
    socket.close();

    assertEquals(
        "{\"method-name\":\"join\",\"arguments\":{\"name\":\"siondw\",\"game-type\":\"SINGLE\"}}"
        +
        System.lineSeparator(), logToString());
  }


  @Test
  public void testHandleTakeShots() throws IOException {
    // Prepare the test data
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> serverMessages = Arrays.asList(
        "{\"method-name\":\"take-shots\",\"arguments\":{}}"
    );
    Mocket mocket = new Mocket(testLog, serverMessages);
    ProxyController proxyController = new ProxyController(mocket, player1);

    proxyController.run();

    String logOutput = testLog.toString();
    Assertions.assertTrue(logOutput.contains("\"method-name\":\"take-shots\""));
    Assertions.assertEquals(
        "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[]}}", logOutput.trim());
  }

  @Test
  public void testHandleSuccessfulHits() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> serverMessages = Arrays.asList(
        "{\"method-name\":\"successful-hits\",\"arguments\":{\"coordinates\": []}}"
    );
    Mocket mocket = new Mocket(testLog, serverMessages);
    ProxyController proxyController = new ProxyController(mocket, player1);

    proxyController.run();

    String logOutput = testLog.toString();
    Assertions.assertTrue(logOutput.contains("\"method-name\":\"successful-hits\""));
    Assertions.assertEquals(
        "{\"method-name\":\"successful-hits\",\"arguments\":\"void\"}", logOutput.trim());
  }

  @Test
  public void testReportDamage() throws IOException {
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> serverMessages = Arrays.asList(
        "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\": []}}"
    );
    Mocket mocket = new Mocket(testLog, serverMessages);
    ProxyController proxyController = new ProxyController(mocket, player1);

    proxyController.run();

    String logOutput = testLog.toString();
    Assertions.assertTrue(logOutput.contains("\"method-name\":\"report-damage\""));
    Assertions.assertEquals(
        "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":[]}}", logOutput.trim());
  }



  @Test
  public void testHandleEndGame() throws IOException {
    // Prepare the test data
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> serverMessages = Arrays.asList(
        "{\"method-name\":\"end-game\",\"arguments\":{\"result\": \"WIN\", \"reason\": "
            +
            "\"Congratulations! You won!\"}}"
    );
    Mocket mocket = new Mocket(testLog, serverMessages);
    ProxyController proxyController = new ProxyController(mocket, player1);

    // Run the ProxyController
    proxyController.run();

    // Verify the test results
    String logOutput = testLog.toString();
    Assertions.assertTrue(logOutput.contains("\"method-name\":\"end-game\""));
    Assertions.assertEquals("{\"method-name\":\"end-game\",\"arguments\":\"void\"}",
        logOutput.trim());
  }

  @Test
  public void testHandleInvalidMessageName() throws IOException {
    // Prepare the test data
    ByteArrayOutputStream testLog = new ByteArrayOutputStream();
    List<String> serverMessages = Arrays.asList(
        "{\"method-name\":\"invalid\",\"arguments\":{}}"
    );
    Mocket mocket = new Mocket(testLog, serverMessages);
    ProxyController proxyController = new ProxyController(mocket, player1);

    // Run the ProxyController
    Exception exception = assertThrows(IllegalStateException.class, proxyController::run);

    // Verify that the exception message is as expected
    String expectedMessage = "Invalid message name";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }


  /**
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   *
   * @param messageName name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   *     Create a MessageJson for some name and arguments.
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
        JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
