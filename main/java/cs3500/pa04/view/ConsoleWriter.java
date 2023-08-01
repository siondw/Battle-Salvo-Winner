package cs3500.pa04.view;

import java.io.IOException;
import java.util.Objects;

/**
 * Responsible for writing any messages to the console, udes by view
 */
public class ConsoleWriter implements Writer {
  private final Appendable appendable;

  // Constructor
  public ConsoleWriter(Appendable appendable) {
    this.appendable = Objects.requireNonNull(appendable);
  }


  /**
   * @param phrase the content to write
   */
  @Override
  public void write(String phrase) {
    try {
      appendable.append(phrase); // this may fail, hence the try-catch
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
