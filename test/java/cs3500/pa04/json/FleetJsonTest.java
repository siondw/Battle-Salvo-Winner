package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * test fleetJson record
 */
public class FleetJsonTest {

  @Test
  public void testSerializationAndDeserialization() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    // Create a new instance of FleetJSON
    Coord coord1 = new Coord(0, 0);  // Replace with any valid coordinate.
    Coord coord2 = new Coord(0, 1);  // Replace with any valid coordinate.
    ShipAdapterJson ship1 = new ShipAdapterJson(coord1, 5, "HORIZONTAL");
    ShipAdapterJson ship2 = new ShipAdapterJson(coord2, 5, "HORIZONTAL");
    FleetJson original = new FleetJson(Arrays.asList(ship1, ship2));

    // Serialize to JSON
    String json = mapper.writeValueAsString(original);

    // Deserialize from JSON
    FleetJson deserialized = mapper.readValue(json, FleetJson.class);

    // Check that the deserialized object matches the original
    assertEquals(original.fleet().size(), deserialized.fleet().size());
    for (int i = 0; i < original.fleet().size(); i++) {
      assertEquals(original.fleet().get(i).coord(), deserialized.fleet().get(i).coord());
      assertEquals(original.fleet().get(i).length(), deserialized.fleet().get(i).length());
      assertEquals(original.fleet().get(i).direction(), deserialized.fleet().get(i).direction());
    }
  }

  @Test
  public void testFleetToShipList() {
    // Create a new instance of FleetJSON
    Coord coord1 = new Coord(0, 0);  // Replace with any valid coordinate.
    Coord coord2 = new Coord(0, 1);  // Replace with any valid coordinate.
    ShipAdapterJson shipAdapter1 = new ShipAdapterJson(coord1, 5, "HORIZONTAL");
    ShipAdapterJson shipAdapter2 = new ShipAdapterJson(coord2, 5, "HORIZONTAL");
    FleetJson fleet = new FleetJson(Arrays.asList(shipAdapter1, shipAdapter2));

    // Convert to List<Ship>
    List<Ship> ships = new ArrayList<>();
    for (ShipAdapterJson shipAdapter : fleet.fleet()) {
      ships.add(shipAdapter.convertToShip());
    }

    // Check that the converted List<Ship> has the correct properties
    for (int i = 0; i < ships.size(); i++) {
      assertEquals(fleet.fleet().get(i).coord(), ships.get(i).getCells().get(0).getLocation());
      assertEquals(fleet.fleet().get(i).length(), ships.get(i).getShipType().getLength());
      assertEquals(fleet.fleet().get(i).direction().equals("HORIZONTAL"),
          ships.get(i).isHorizontal());
    }
  }
}

