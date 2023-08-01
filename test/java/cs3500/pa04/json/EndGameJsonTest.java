package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.model.GameResult;
import org.junit.jupiter.api.Test;

/**
 * tests for endGame Json record
 */
public class EndGameJsonTest {

  @Test
  public void testSerializationAndDeserialization() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    // Create a new instance of EndGameJSON
    GameResult gameResult = GameResult.WIN;
    EndGameJson original = new EndGameJson(gameResult, "Test reason");

    // Serialize to JSON
    String json = mapper.writeValueAsString(original);

    // Deserialize from JSON
    EndGameJson deserialized = mapper.readValue(json, EndGameJson.class);

    // Check that the deserialized object matches the original
    assertEquals(original.gameResult(), deserialized.gameResult());
    assertEquals(original.reason(), deserialized.reason());
  }
}
