package cs3500.pa04.controller;

import cs3500.pa04.model.Aplayer;
import cs3500.pa04.model.ComputerPlayer;
import cs3500.pa04.model.HumanPlayer;
import cs3500.pa04.model.Model;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.view.Reader;
import cs3500.pa04.view.View;
import cs3500.pa04.view.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * to represent a gameController
 */
public class GameController {
  private Model battleshipGame;
  private final View view;
  private final Reader reader;
  private final Writer writer;

  private Aplayer player1;


  private final Random random;

  /**
   * Constructor for game controller
   */
  public GameController(View view, Reader reader, Writer writer, Random random) {
    this.view = view;
    this.reader = reader;
    this.writer = writer;
    this.random = random;
  }

  /**
   * makes calls to the view for prompts and reads input
   * creates players, boards, and fleets so the game can begin
   */
  public void initializeGame() {
    view.displayWelcomeMessage();
    view.promptForBoardSize();

    int[] boardParameters = getValidBoardSize(reader, writer);
    int height = boardParameters[0];
    int width = boardParameters[1];

    //initializeBoards(height, width);
    initializePlayers(height, width);


    int maxFleetSize = Math.min(height, width);

    view.promptForFleetConfig(maxFleetSize);
    List<Integer> fleet = null;

    while (fleet == null) {
      fleet = reader.readFleet();
    }

    Map<ShipType, Integer> shipMap = validateAndConvertFleet(fleet, height, width);

    battleshipGame.setup(height, width, shipMap);
    view.displayTrackingBoard(player1.getTrackingBoard());
    view.displayMessage("------------------------------- Initial Setup \n");
    view.displayOceanBoard(player1.getGameBoard());

    gameLoop();

  }


  /**
   * controls when the Model's game loop runs
   * this way, we can display the boards between rounds
   * this is in Line ith MVC because the conrolle controls the view and model
   */
  private void gameLoop() {
    while (!battleshipGame.isGameOver()) {
      battleshipGame.runGameIteration();
      view.displayTrackingBoard(player1.getTrackingBoard());
      view.displayMessage("-------------------------------------------- \n");
      view.displayOceanBoard(player1.getGameBoard());

    }
    view.displayMessage(battleshipGame.getPlayer1Result().name()
        + ": " + battleshipGame.getPlayer1Reason());
  }



  /**
   * passes the players their boards
   */
  private void initializePlayers(int height, int width) {
    // Create the players
    this.player1 = new HumanPlayer("Player 1", view, random, height, width);
    Aplayer player2 = new ComputerPlayer("CPU", view, random, height, width);


    this.battleshipGame =
        new Model(player1, player2, player1.getGameBoard(), player1.getTrackingBoard(),
            player2.getGameBoard(), player2.getTrackingBoard());
  }

  /**
   * @param reader ability to read user input
   * @param writer ability to write message back
   * @return a valid set of board height and width
   */
  private int[] getValidBoardSize(Reader reader, Writer writer) {
    int[] boardParameters = reader.readBoardSize();
    int height = boardParameters[0];
    int width = boardParameters[1];

    // Validate the board size
    while (height < 6 || height > 15 || width < 6 || width > 15) {
      writer.write("""
          Uh Oh! You've entered invalid dimensions. Please remember that the height and width
          of the game must be in the range (6, 15), inclusive. Try again!
          """);
      boardParameters = reader.readBoardSize();
      height = boardParameters[0];
      width = boardParameters[1];
    }

    return boardParameters;
  }

  /**
   * @param fleetConfig the list of how many of each ship
   * @param height      of the board
   * @param width       of the board
   * @return sets the values of each ship to the correct key on the map
   *     this also validates all the arguments
   */
  private Map<ShipType, Integer> validateAndConvertFleet(
      List<Integer> fleetConfig, int height, int width) {
    Map<ShipType, Integer> shipCount = new HashMap<>();
    try {
      if (fleetConfig.size() != 4) {
        throw new IllegalArgumentException("Fleet configuration must contain exactly 4 values.");
      }

      shipCount.put(ShipType.CARRIER, fleetConfig.get(0));
      shipCount.put(ShipType.BATTLESHIP, fleetConfig.get(1));
      shipCount.put(ShipType.DESTROYER, fleetConfig.get(2));
      shipCount.put(ShipType.SUBMARINE, fleetConfig.get(3));

      for (Integer value : shipCount.values()) {
        if (value == 0) {
          throw new IllegalArgumentException("One or more values in Fleet is equal to 0.");
        }
      }

      int totalFleetSize = shipCount.values().stream().mapToInt(Integer::intValue).sum();
      int minBoardDimension = Math.min(height, width);

      if (totalFleetSize > minBoardDimension) {
        throw new IllegalArgumentException(
            "Total fleet size cannot be greater than the minimum dimension of the board");
      }
    } catch (IllegalArgumentException e) {
      writer.write(e.getMessage() + " Please try again.\n");
      return validateAndConvertFleet(reader.readFleet(), height, width);
    }

    return shipCount;
  }
}


