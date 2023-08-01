package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * tests on the coord class
 */
public class CoordTest {

  @Test
  public void testGetX() {
    Coord coord = new Coord(2, 3);
    assertEquals(2, coord.getX());
  }

  @Test
  public void testGetY() {
    Coord coord = new Coord(2, 3);
    assertEquals(3, coord.getY());
  }

  @Test
  public void testEquals() {
    Coord coord1 = new Coord(2, 3);
    Coord coord2 = new Coord(2, 3);
    Coord coord3 = new Coord(3, 2);

    assertTrue(coord1.equals(coord2));
    assertFalse(coord1.equals(coord3));
  }

  @Test
  public void testHashCode() {
    Coord coord1 = new Coord(2, 3);
    Coord coord2 = new Coord(2, 3);
    Coord coord3 = new Coord(3, 2);

    assertEquals(coord1.hashCode(), coord2.hashCode());
    assertNotEquals(coord1.hashCode(), coord3.hashCode());
  }

  @Test
  public void testToString() {
    Coord coord = new Coord(2, 3);
    assertEquals("Coord = 2, 3", coord.toString());
  }

  @Test
  public void testEqualsSameObject() {
    Coord coord1 = new Coord(2, 3);

    assertTrue(coord1.equals(coord1));
  }

  @Test
  public void testEqualsNull() {
    Coord coord1 = new Coord(2, 3);

    assertFalse(coord1.equals(null));
  }

  @Test
  public void testEqualsDifferentClass() {
    Coord coord1 = new Coord(2, 3);
    String notAcoord = "Not a coordinate";

    assertFalse(coord1.equals(notAcoord));
  }

}