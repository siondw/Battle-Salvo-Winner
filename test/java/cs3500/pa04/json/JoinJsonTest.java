package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;


/**
 * tests on the join class method
 */
public class JoinJsonTest {

  @Test
  public void testSerializationAndDeserialization() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    // Create a new instance of JoinJSON
    String name = "TestName";
    String gameType = "TestGameType";
    JoinJson original = new JoinJson(name, gameType);

    // Serialize to JSON
    String json = mapper.writeValueAsString(original);

    // Deserialize from JSON
    JoinJson deserialized = mapper.readValue(json, JoinJson.class);

    // Check that the deserialized object matches the original
    assertEquals(original.name(), deserialized.name());
    assertEquals(original.gameType(), deserialized.gameType());
  }
}
