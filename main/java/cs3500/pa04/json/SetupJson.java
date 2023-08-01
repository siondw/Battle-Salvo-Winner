package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.ShipType;
import java.util.Map;

/**
 * To represent set up a game board in json format
 */
public record SetupJson(@JsonProperty("width") int width,
                        @JsonProperty("height") int height,
                        @JsonProperty("fleet-spec") Map<ShipType, Integer> fleet) {}
