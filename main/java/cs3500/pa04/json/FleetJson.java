package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * To represent a fleet in json format
 */
public record FleetJson(@JsonProperty("fleet") List<ShipAdapterJson> fleet) {}
