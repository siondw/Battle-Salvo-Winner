package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameResult;

/**
 * To represent the end of a game in json format
 */
public record EndGameJson(@JsonProperty("result") GameResult gameResult,
                          @JsonProperty("reason") String reason) {
}
