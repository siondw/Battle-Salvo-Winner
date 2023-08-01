package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * To represent a message being sent a server game in json format
 */
public record MessageJson(
    @JsonProperty("method-name") String messageName,
    @JsonProperty("arguments")JsonNode arguments) {}
