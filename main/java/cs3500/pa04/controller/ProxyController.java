package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.ShipAdapterJson;
import cs3500.pa04.json.VolleyJson;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * this class is rsponsible for communicating with server by communicating with the model
 */
public class ProxyController {


  private final Socket server;
  private final Player player;

  private final PrintStream out;
  private final InputStream in;
  private final ObjectMapper mapper = new ObjectMapper();
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");


  /**
   * Constructor for proxy controller
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() throws  IOException {
    JsonParser parser = this.mapper.getFactory().createParser(this.in);

    try {
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
        // End
    }
  }

  /**
   * @param message the Server's message
   *                delegates the message to the appropriate method based on the name
   */
  private void delegateMessage(MessageJson message)  {
    // todo remove exception
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    switch (name) {
      case "join" -> handleJoin(arguments);
      case "setup" -> handleSetup(arguments);
      case "take-shots" -> handleTakeShots(arguments);
      case "report-damage" -> handleReportDamage(arguments);
      case "successful-hits" -> handleSuccessfulHits(arguments);
      case "end-game" -> handleEndGame(arguments);
      default -> throw new IllegalStateException("Invalid message name");
    }
  }


  /**
   * @param arguments the servers message args
   *                  handles the Server's join request properly
   */
  private void handleJoin(JsonNode arguments) {
    // empty in this case
    JoinJson joinArgs = this.mapper.convertValue(arguments, JoinJson.class);


    String name = "siondw";
    String gameType = "SINGLE";

    JoinJson response = new JoinJson(name, gameType);

    JsonNode responseAsNode = JsonUtils.serializeRecord(response);

    MessageJson messageJson = new MessageJson("join", responseAsNode);

    JsonNode clientResponse = JsonUtils.serializeRecord(messageJson);

    this.out.println(clientResponse);
  }

  /**
   * @param arguments the servers message args handles the Server's setup request properly
   */
  private void handleSetup(JsonNode arguments) {
    // get the arguments from the server
    SetupJson setupArgs = this.mapper.convertValue(arguments, SetupJson.class);

    // get the width argument provided by server
    int height = setupArgs.width();

    // get the height argument provided by server
    int width = setupArgs.height();

    System.out.println("Height: " + height + " Width: " + width);

    // get the ship-spec argument provided by server
    Map<ShipType, Integer> shipSpec = setupArgs.fleet();

    // delegate to player
    List<Ship> ships = player.setup(height, width, shipSpec);
    List<ShipAdapterJson> shipsJsons = new ArrayList<>();

    for (Ship s : ships) {
      ShipAdapterJson ship = ShipAdapterJson.shipToShipJson(s);
      shipsJsons.add(ship);
    }

    // create new FleetJSON object out of newly created ship array
    FleetJson fleetJson = new FleetJson(shipsJsons);

    // create JsonNode from fleetJSON
    JsonNode fleetAsNode = JsonUtils.serializeRecord(fleetJson);

    // create MessageJSON
    MessageJson responseToServer = new MessageJson("setup", fleetAsNode);

    // serialize the entire message to be passed back to the server
    JsonNode clientResponse = JsonUtils.serializeRecord(responseToServer);

    this.out.println(clientResponse);
  }

  /**
   * @param arguments the servers message args handles the Server's takeShots request properly
   */
  private void handleTakeShots(JsonNode arguments) {

    // remove exception thrower
    // don't need to parse anything from server so just delegate to player immediately
    List<Coord> shots = player.takeShots();


    // create Volley from List<Shot>
    VolleyJson volleyJson = new VolleyJson(shots);

    // create JSON node from Volley
    JsonNode volleyAsNode = JsonUtils.serializeRecord(volleyJson);

    // create MessageJSON
    MessageJson responseToServer = new MessageJson("take-shots", volleyAsNode);

    // serialize the entire message to be passed back to the server
    JsonNode clientResponse = JsonUtils.serializeRecord(responseToServer);


    this.out.println(clientResponse);
  }

  private void handleReportDamage(JsonNode arguments) {
    // this is the argument for the method provided by the server
    VolleyJson setupArgs = this.mapper.convertValue(arguments, VolleyJson.class);

    // these are the list of shots that were shot at the player
    List<Coord> shotsOnPlayer = setupArgs.volley();

    // delegate to player
    List<Coord> shotsThatHit = player.reportDamage(shotsOnPlayer);

    // create volley from List<Coords>
    VolleyJson volleyJson = new VolleyJson(shotsThatHit);

    // create JSON node from Volley
    JsonNode volleyAsNode = JsonUtils.serializeRecord(volleyJson);

    // create MessageJSON
    MessageJson responseToServer = new MessageJson("report-damage", volleyAsNode);

    // serialize the entire message to be passed back to the server
    JsonNode clientResponse = JsonUtils.serializeRecord(responseToServer);

    this.out.println(clientResponse);
  }

  /**
   * @param arguments takes in arguments given by Server message "successfull-hits"
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    // this is the argument for the method provided by the server
    VolleyJson setupArgs = this.mapper.convertValue(arguments, VolleyJson.class);

    // these are the list of shots that were shot at the player
    List<Coord> shotsThatPlayerHit = setupArgs.volley();

    // delegate to player
    player.successfulHits(shotsThatPlayerHit);

    // create MessageJSON
    MessageJson responseToServer = new MessageJson("successful-hits", VOID_RESPONSE);

    // serialize the entire message to be passed back to the server
    JsonNode clientResponse = JsonUtils.serializeRecord(responseToServer);

    this.out.println(clientResponse);
  }

  private void handleEndGame(JsonNode arguments) {
    // this is the argument for the method provided by the server
    EndGameJson setupArgs = this.mapper.convertValue(arguments, EndGameJson.class);

    // grab each argument from the server
    GameResult gr = setupArgs.gameResult();
    String reason = setupArgs.reason();

    // delegate to Player
    player.endGame(gr, reason);

    // create MessageJSON
    MessageJson responseToServer = new MessageJson("end-game", VOID_RESPONSE);

    // serialize the entire message to be passed back to the server
    JsonNode clientResponse = JsonUtils.serializeRecord(responseToServer);

    this.out.println(clientResponse);
  }
}
