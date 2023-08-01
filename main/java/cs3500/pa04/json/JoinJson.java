package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * To represent joining a game in json format
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType) {}
