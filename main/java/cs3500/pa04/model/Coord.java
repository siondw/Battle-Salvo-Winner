package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * responsible for storing x and y value as one object
 * used to locate a cell
 */
public class Coord {

  private final int coordx;
  private final int coordy;

  @JsonCreator
  public Coord(@JsonProperty("x") int x, @JsonProperty("y") int y) {
    this.coordx = x;
    this.coordy = y;
  }

  /**
   * @return gets x value
   */
  public int getX() {
    return coordx;
  }

  /**
   * @return gets y value
   */
  public int getY() {
    return coordy;
  }

  /**
   * @param obj an object to compare too
   * @return if that object is equal to this
   *          needed to ovveride equals
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Coord coord = (Coord) obj;
    return coordx == coord.coordx && coordy == coord.coordy;
  }

  /**
   * @return hashcode
   *          ovverrode equals so had to override this
   */
  @Override
  public int hashCode() {
    return Objects.hash(coordx, coordy);
  }

  public String toString() {
    return "Coord = " + getX() + ", " + getY();
  }

}


