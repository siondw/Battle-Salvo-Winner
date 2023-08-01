package cs3500.pa04.model;

import cs3500.pa04.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * To represent a player type
 */
public abstract class Aplayer implements Player {

  protected String playerName;
  protected OceanBoard gameBoard;
  protected TrackingBoard trackingBoard;
  protected View view;
  protected Random random;
  protected boolean server;

  /**
   * Constructor for A player
   */
  public Aplayer(String name, View view, Random random, int height, int width) {
    this.playerName = name;
    this.view = view;
    this.random = random;

    this.gameBoard = new OceanBoard(height, width);
    this.trackingBoard = new TrackingBoard(height, width);

  }

  /**
   * Testing contractor for a player
   */
  public Aplayer(Random random, boolean server) {
    this.random = random;
    this.server = server;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.playerName;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    if (this.gameBoard == null && this.trackingBoard == null) {
      this.gameBoard = new OceanBoard(height, width);
      this.trackingBoard = new TrackingBoard(height, width);

    }

    // Sort the entries by the length of the ships in descending order
    List<Map.Entry<ShipType, Integer>> sortedEntries = new ArrayList<>(specifications.entrySet());
    sortedEntries.sort((entry1, entry2) ->
        entry2.getKey().getLength() - entry1.getKey().getLength());

    // Iterate over each entry in the sorted list
    List<Ship> ships = new ArrayList<>();
    for (Map.Entry<ShipType, Integer> entry : sortedEntries) {
      ShipType type = entry.getKey();
      int count = entry.getValue();

      // Create the specified number of ships of each type
      for (int i = 0; i < count; i++) {
        Ship ship = new Ship(type, gameBoard, random);
        ships.add(ship);
        gameBoard.setShipCells(ship);
        gameBoard.addShip(ship);
      }
    }
    return ships;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public abstract void endGame(GameResult result, String reason);


  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    // Implementation goes here
    List<Coord> shotsThatHit = new ArrayList<>();

    for (Coord coord : opponentShotsOnBoard) {
      int x = coord.getX();
      int y = coord.getY();
      Cell cell = gameBoard.getCell(x, y);

      if (!(cell.getShip() == null)) {
        Ship shipOnCell = cell.getShip();
        cell.updateCellState(CellState.HIT);
        shipOnCell.addHit(cell);
        shotsThatHit.add(coord);
      }
    }
    return shotsThatHit;

  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public abstract void successfulHits(List<Coord> shotsThatHitOpponentShips);

  public OceanBoard getGameBoard() {
    return gameBoard;
  }

  public TrackingBoard getTrackingBoard() {
    return trackingBoard;
  }


}
