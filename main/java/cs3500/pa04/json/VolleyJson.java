package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import java.util.List;


/**
 * To represent a volley in json format
 */
public record VolleyJson(@JsonProperty("coordinates") List<Coord> volley) {}
