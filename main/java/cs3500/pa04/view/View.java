package cs3500.pa04.view;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * Represents a view
 */
public interface View {

  /**
   * @param oceanBoard takes in the board so it can display it
   */
  void displayOceanBoard(Board oceanBoard);

  void displayTrackingBoard(Board trackingBoard);

  void displayWelcomeMessage();

  void displayMessage(String message);

  void promptForBoardSize();

  void promptForFleetConfig(int maxFleetSize);

  List<Coord> readShots(int numOfShots);

  // other methods?
}
