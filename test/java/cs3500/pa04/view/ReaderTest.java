package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coord;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



/**
 * tests for reader class
 */
public class ReaderTest {
  private Reader reader;

  @BeforeEach
  public void setup() {
    String input = "2 3 4 5\n10 10\n1 1\n2 2\n3 3\n4 4\n";
    reader = new Reader(new StringReader(input));
  }

  @Test
  public void testReadFleet() {
    List<Integer> fleet = reader.readFleet();
    assertEquals(4, fleet.size());
    assertEquals(2, fleet.get(0));
    assertEquals(3, fleet.get(1));
    assertEquals(4, fleet.get(2));
    assertEquals(5, fleet.get(3));
  }

  @Test
  public void testReadBoardSize() {
    reader.readFleet(); // Skip fleet input
    int[] boardSize = reader.readBoardSize();
    assertEquals(10, boardSize[0]);
    assertEquals(10, boardSize[1]);
  }

  @Test
  public void testReadShots() {
    reader.readFleet(); // Skip fleet input
    reader.readBoardSize(); // Skip board size input
    List<Coord> shots = reader.readShots(4);
    assertEquals(4, shots.size());
    assertEquals(new Coord(1, 1), shots.get(0));
    assertEquals(new Coord(2, 2), shots.get(1));
    assertEquals(new Coord(3, 3), shots.get(2));
    assertEquals(new Coord(4, 4), shots.get(3));
  }

  @Test
  public void testReadShotsNonIntegerInput() {
    reader = new Reader(new StringReader("2 3 4 5\n10 10\n1 1\n2 2\nthree 3\n4 4\n"));
    // 'three' is not an integer
    reader.readFleet(); // Skip fleet input
    reader.readBoardSize(); // Skip board size input
    List<Coord> shots = reader.readShots(4);
    assertEquals(3, shots.size());
    assertEquals(new Coord(1, 1), shots.get(0));
    assertEquals(new Coord(2, 2), shots.get(1));
    assertEquals(new Coord(4, 4), shots.get(2));
  }

  @Test
  public void testReadShotsTooManyIntegers() {
    // '2 2 2' contains three integers
    reader = new Reader(new StringReader("2 3 4 5\n10 10\n1 1\n2 2 2\n3 3\n4 4\n"));
    reader.readFleet(); // Skip fleet input
    reader.readBoardSize(); // Skip board size input
    List<Coord> shots = reader.readShots(4);
    assertEquals(3, shots.size());
    assertEquals(new Coord(1, 1), shots.get(0));
    assertEquals(new Coord(3, 3), shots.get(1));
    assertEquals(new Coord(4, 4), shots.get(2));
  }

}
