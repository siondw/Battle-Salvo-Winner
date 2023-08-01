package cs3500.pa04.model;

import java.util.List;
import java.util.Map;

/**
 * this class is the model for the Battleship Game state
 */
public class Model {
  private Player player1;
  private Player player2;
  private OceanBoard player1GameBoard;
  private TrackingBoard player1TrackingBoard;
  private OceanBoard player2GameBoard;
  private TrackingBoard player2TrackingBoard;
  private String player1Reason;
  private GameResult player1Result;


  /**
   * constructor for a type of model
   */
  public Model(Player player1, Player player2, OceanBoard player1GameBoard,
               TrackingBoard player1TrackingBoard, OceanBoard player2GameBoard,
               TrackingBoard player2TrackingBoard) {
    this.player1 = player1;
    this.player2 = player2;
    this.player1GameBoard = player1GameBoard;
    this.player1TrackingBoard = player1TrackingBoard;
    this.player2GameBoard = player2GameBoard;
    this.player2TrackingBoard = player2TrackingBoard;
  }

  /**
   * @param height board height
   * @param width board width
   * @param shipMap how many of each ship
   *                this method passes each player what it needs to create it's bard
   */
  public void setup(int height, int width, Map<ShipType, Integer> shipMap) {


    player1.setup(height, width, shipMap);
    player2.setup(height, width, shipMap);
  }


  /**
   * runs the game loop
   * controlled by gameController
   */
  public void runGameIteration() {
    // Both players take their shots
    List<Coord> shotsPlayer1 = player1.takeShots();
    List<Coord> shotsPlayer2 = player2.takeShots();

    // Each player takes in the opponents shots, updates their gameBoards accordingly,
    // and then return the shots that hit
    List<Coord> hitsPlayer1 = player1.reportDamage(shotsPlayer2);
    List<Coord> hitsPlayer2 = player2.reportDamage(shotsPlayer1);

    // Reports to this player what shots in their previous volley returned from takeShots()
    //     * successfully hit an opponent's ship.
    //     *
    //     * @param shotsThatHitOpponentShips the list of shots that successfully hit
    //              the opponent's ships
    //     */
    player1.successfulHits(hitsPlayer2);
    player2.successfulHits(hitsPlayer1);


  }


  /**
   * @return check if the game over
   */
  public boolean isGameOver() {
    if ((player1GameBoard.getShipsLeft() > 0) && (player2GameBoard.getShipsLeft() > 0)) {
      return false;
    } else if ((player1GameBoard.getShipsLeft() == 0) && (player2GameBoard.getShipsLeft() > 0)) {
      this.player1Reason = "All your ships are sunk!";
      this.player1Result = GameResult.LOSE;

    } else if ((player2GameBoard.getShipsLeft() == 0) && (player1GameBoard.getShipsLeft() > 0)) {
      this.player1Reason = "You sank all the enemy's ships!";
      this.player1Result = GameResult.WIN;

    } else { // both player1GameBoard.getShipsLeft() and player2GameBoard.getShipsLeft() are 0
      this.player1Reason = "Everybody's ships are sunk!";
      this.player1Result = GameResult.DRAW;

    }
    return true;
  }


  public String getPlayer1Reason() {
    return player1Reason;
  }

  public GameResult getPlayer1Result() {
    return player1Result;
  }
}




