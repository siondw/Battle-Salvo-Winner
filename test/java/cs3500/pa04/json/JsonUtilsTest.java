package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

/**
 * tests for Json utils class
 */
public class JsonUtilsTest {

  @Test
  public void testSerializeRecord() {
    // Create a new instance of JoinJSON
    String name = "TestName";
    String gameType = "TestGameType";
    JoinJson joinJson = new JoinJson(name, gameType);

    // Serialize to JsonNode
    JsonNode jsonNode = JsonUtils.serializeRecord(joinJson);

    // Check that the JsonNode contains the correct data
    assertEquals(jsonNode.get("name").asText(), joinJson.name());
    assertEquals(jsonNode.get("game-type").asText(), joinJson.gameType());
  }
}
