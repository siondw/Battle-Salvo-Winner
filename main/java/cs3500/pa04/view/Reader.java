package cs3500.pa04.view;

import cs3500.pa04.model.Coord;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * responsible for reading the console input
 */
public class Reader {

  // Fields
  private final Readable readable;
  private final Scanner scanner;


  /**
   * @param readable something that can be read
   */
  public Reader(Readable readable) {
    this.readable = Objects.requireNonNull(readable);
    this.scanner = new Scanner(this.readable);
  }

  /**
   * @return a List of all the numbers the user gives for fleet size
   */
  public List<Integer> readFleet() {
    List<Integer> fleet = new ArrayList<>();


    for (int i = 0; i < 4; i++) {
      if (scanner.hasNextInt()) {
        int num = scanner.nextInt();
        fleet.add(num);
      }
    }

    return fleet;
  }

  /**
   * To read the size of a given board
   */
  public int[] readBoardSize() {
    int[] boardParameters = new int[2];

    boardParameters[0] = scanner.nextInt();
    boardParameters[1] = scanner.nextInt();

    return boardParameters;
  }

  /**
   * @return A list of Coordinates of all the shots the user is firing
   */
  public List<Coord> readShots(int numOfShot) {
    List<Coord> shots = new ArrayList<>();

    System.out.println("Please enter " + numOfShot + " shot(s):");

    for (int i = 0; i < numOfShot; i++) {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (!line.isEmpty()) {
          String[] numbers = line.split("\\s+");
          if (numbers.length == 2) {
            try {
              int x = Integer.parseInt(numbers[0]);
              int y = Integer.parseInt(numbers[1]);
              shots.add(new Coord(x, y));
            } catch (NumberFormatException e) {
              System.out.println(
                  "Invalid input: coordinates must be integers. ");
              i--; // decrement i to repeat the iteration for invalid input
              System.out.println("Please enter" + (numOfShot - i) + " shots");
            }
          } else {
            System.out.println("Invalid input: each line must contain exactly two integers.");
            i--; // decrement i to repeat the iteration for invalid input
          }
        } else {
          i--;
        }
      }
    }

    return shots;
  }

}
