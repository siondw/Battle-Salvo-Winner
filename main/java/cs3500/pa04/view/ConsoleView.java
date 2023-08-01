package cs3500.pa04.view;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Cell;
import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * responsible for writing/reading the console
 */
public class ConsoleView implements View {

  Writer writer;
  Reader reader;

  public ConsoleView(Writer writer, Reader reader) {
    this.writer = writer;
    this.reader = reader;
  }

  @Override
  public void displayWelcomeMessage() {
    writer.write("Hello! Welcome to the OOD BattleSalvo Game! \n");
  }

  @Override
  public void displayMessage(String message) {
    writer.write(message);
  }


  @Override
  public void promptForBoardSize() {
    writer.write("Please enter a valid height and width below: \n");
  }


  @Override
  public void promptForFleetConfig(int maxFleetSize) {
    writer.write(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine] \n "
            +
            "Remember, your fleet may not exceed size " + maxFleetSize + ": \n");
  }


  @Override
  public List<Coord> readShots(int numOfShots) {
    return reader.readShots(numOfShots);
  }

  /**
   * @param oceanBoard takes in the board so it can display it
   */
  @Override
  public void displayOceanBoard(Board oceanBoard) {
    System.out.println("Your Board");

    for (int i = 0; i < oceanBoard.getHeight(); i++) {
      for (int j = 0; j < oceanBoard.getWidth(); j++) {
        Cell cell = oceanBoard.getCell(i, j);
        if (cell.getShip() != null) {
          // If there's a ship in the cell, print the first letter of the ship's type
          System.out.print(cell.getShip().getShipType().name().charAt(0) + " ");
        } else {
          // If the cell is empty, print 0
          System.out.print("0 ");
        }
      }
      System.out.println();
    }
  }

  /**
   * @param trackingBoard tracking board to display
   */
  @Override
  public void displayTrackingBoard(Board trackingBoard) {
    System.out.println("Tracker Board");

    for (int i = 0; i < trackingBoard.getHeight(); i++) {
      for (int j = 0; j < trackingBoard.getWidth(); j++) {
        Cell cell = trackingBoard.getCell(i, j);
        switch (cell.getCellState()) {
          case EMPTY:
            System.out.print("0 ");
            break;
          case HIT:
            System.out.print("H ");
            break;
          case MISS:
            System.out.print("M ");
            break;
          default:
            // Handle other possible cell states if necessary
            break;
        }
      }
      System.out.println();
    }
  }
}
