package cs3500.pa04.model;

import cs3500.pa04.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * class to represent HumanPlayer
 */
public class HumanPlayer extends Aplayer {
  public HumanPlayer(String name, View view, Random rand, int height, int width) {
    super(name, view, rand, height, width);
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    int numOfShot = gameBoard.getShipsLeft();
    // see how many cells are left
    List<Cell> cellsAvailable = trackingBoard.boardAsList().stream()
        .filter(cell -> cell.getCellState() == CellState.EMPTY)
        .collect(Collectors.toCollection(ArrayList::new));

    if (cellsAvailable.size() < numOfShot) {
      numOfShot = cellsAvailable.size();
    }

    List<Coord> shots = view.readShots(numOfShot);

    while (!validateShots(shots)) {
      shots = view.readShots(numOfShot);
    }

    // set each shot as a miss intially, we will change this when we process our hits
    for (Coord coord : shots) {
      // todo make sure this is correct with the way we fire shots
      Cell cell = trackingBoard.getCell(coord.getX(), coord.getY());
      cell.updateCellState(CellState.MISS);
    }


    return shots;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord coord : shotsThatHitOpponentShips) {
      Cell cell = trackingBoard.getCell(coord.getX(), coord.getY());
      cell.updateCellState(CellState.HIT);
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }

  /**
   * @param shots takes in the users shots
   * @return if all shots are valid
   */
  private boolean validateShots(List<Coord> shots) {
    // Check that each shot is within the bounds of the board and hasn't been taken before
    for (Coord shot : shots) {
      if (shot.getX() < 0 || shot.getX() > gameBoard.getWidth() - 1 || shot.getY() < 0
          || shot.getY() > gameBoard.getHeight() - 1) {
        view.displayMessage("One or more of your shots are out of bounds."
            +
            "Try again! Please enter coordinates within the board this time. ");
        return false;
      }

      CellState state = trackingBoard.getCell(shot.getX(), shot.getY()).getCellState();
      if (state == CellState.HIT || state == CellState.MISS) {
        view.displayMessage("You've already shot at (" + shot.getX() + ", " + shot.getY() + "). "
            +
            "Are you seeing double?");
      }
    }

    return true;
  }
}
