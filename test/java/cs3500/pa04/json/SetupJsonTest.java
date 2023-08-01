package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.model.ShipType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * tets for setupJson record
 */
public class SetupJsonTest {

  @Test
  public void testSerializationAndDeserialization() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    // Create a new instance of SetupJSON
    int width = 10;
    int height = 10;
    Map<ShipType, Integer> fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 2);
    SetupJson original = new SetupJson(width, height, fleet);

    // Serialize to JSON
    String json = mapper.writeValueAsString(original);

    // Deserialize from JSON
    SetupJson deserialized = mapper.readValue(json, SetupJson.class);

    // Check that the deserialized object matches the original
    assertEquals(original.width(), deserialized.width());
    assertEquals(original.height(), deserialized.height());
    assertEquals(original.fleet(), deserialized.fleet());
  }
}
