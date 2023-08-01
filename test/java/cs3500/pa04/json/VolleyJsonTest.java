package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.model.Coord;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * test for the JsonVolley record
 */
public class VolleyJsonTest {

  @Test
  public void testSerializationAndDeserialization() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    // Create a new instance of VolleyJSON
    List<Coord> volley = new ArrayList<>();
    volley.add(new Coord(1, 1));
    volley.add(new Coord(2, 2));
    volley.add(new Coord(3, 3));
    VolleyJson original = new VolleyJson(volley);

    // Serialize to JSON
    String json = mapper.writeValueAsString(original);

    // Deserialize from JSON
    VolleyJson deserialized = mapper.readValue(json, VolleyJson.class);

    // Check that the deserialized object matches the original
    assertEquals(original.volley(), deserialized.volley());
  }
}
