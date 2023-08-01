package cs3500.pa04.model;

import cs3500.pa04.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * this class represents the Computer Player
 */
public class ComputerPlayer extends Aplayer {

  public ComputerPlayer(String name, View view, Random rand, int height, int width) {
    super(name, view, rand, height, width);
  }

  public ComputerPlayer(Random random, boolean server) {
    super(random, server);
  }

  /**
   * @return list of Coords with the highest probability of containing a ship
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> shots = new ArrayList<>();
    int numOfShots = gameBoard.getShipsLeft();

    List<Cell> cellsAvailable = trackingBoard.boardAsList().stream()
        .filter(cell -> cell.getCellState() == CellState.EMPTY)
        .collect(Collectors.toCollection(ArrayList::new));

    // Sort cells by shot probability in descending order
    cellsAvailable.sort((cell1, cell2) -> Double.compare(
        cell2.getProbability(), cell1.getProbability()));

    if (cellsAvailable.size() < numOfShots) {
      numOfShots = cellsAvailable.size();
    }
    // pick number of shots from list
    cellsAvailable = cellsAvailable.subList(0, numOfShots);

    // set each shot to a miss, we will update the ones that hit later
    for (Cell c : cellsAvailable) {
      c.updateCellState(CellState.MISS);

      List<Cell> surroundingCells = trackingBoard.getSurroundingCells(c.getLocation());

      for (Cell cell : surroundingCells) {
        cell.setProbability(c.getProbability() - 25);
      }
    }

    // convert each cell in the list to a coordinate
    for (Cell c : cellsAvailable) {
      Coord shot = c.getLocation();
      shots.add(shot);
    }

    return shots;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord coord : shotsThatHitOpponentShips) {
      Cell hitCell = trackingBoard.getCell(coord.getX(), coord.getY());
      hitCell.updateCellState(CellState.HIT);

      List<Cell> surroundingCells = trackingBoard.getSurroundingCells(coord);

      // Count the number of hit cells in both vertical and horizontal directions
      long hitCellsHorizontal = surroundingCells.stream()
          .filter(c -> c.getLocation().getX() == hitCell.getLocation().getX()
              && c.getCellState() == CellState.HIT)
          .count();

      long hitCellsVertical = surroundingCells.stream()
          .filter(c -> c.getLocation().getY() == hitCell.getLocation().getY()
              && c.getCellState() == CellState.HIT)
          .count();

      // If there are more hits in one direction, assume that's the ship's orientation
      if (hitCellsHorizontal > hitCellsVertical) {
        hitCell.setDirection(Direction.HORIZONTAL);
      } else if (hitCellsHorizontal < hitCellsVertical) {
        hitCell.setDirection(Direction.VERTICAL);
      }

      for (Cell cell : surroundingCells) {
        if (hitCell.getDirection() == Direction.UNKNOWN
            ||
            hitCell.getDirection() == cell.getDirection()) {
          cell.setProbability(300);
        }
      }
    }
  }


  /**
   * * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

    System.out.println(result.toString());
    System.out.println(" " + reason);
  }


}
