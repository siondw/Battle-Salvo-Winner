package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * test for shipType enum
 */
public class ShipTypeTest {
  @Test
  public void testCarrierLength() {
    assertEquals(6, ShipType.CARRIER.getLength());
  }

  @Test
  public void testBattleshipLength() {
    assertEquals(5, ShipType.BATTLESHIP.getLength());
  }

  @Test
  public void testDestroyerLength() {
    assertEquals(4, ShipType.DESTROYER.getLength());
  }

  @Test
  public void testSubmarineLength() {
    assertEquals(3, ShipType.SUBMARINE.getLength());
  }
}