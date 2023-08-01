package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * Test for the messageJson record
 */
public class MessageJsonTest {

  @Test
  public void testSerializationAndDeserialization() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    // Create a new instance of MessageJSON
    String messageName = "testMessageName";
    // creating a simple JsonNode
    JsonNode arguments = mapper.createObjectNode().put("key", "value");
    MessageJson original = new MessageJson(messageName, arguments);

    // Serialize to JSON
    String json = mapper.writeValueAsString(original);

    // Deserialize from JSON
    MessageJson deserialized = mapper.readValue(json, MessageJson.class);

    // Check that the deserialized object matches the original
    assertEquals(original.messageName(), deserialized.messageName());
    assertEquals(original.arguments(), deserialized.arguments());
  }
}
