package cs3500.pa04;

import cs3500.pa04.controller.GameController;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.model.ComputerPlayer;
import cs3500.pa04.view.ConsoleView;
import cs3500.pa04.view.ConsoleWriter;
import cs3500.pa04.view.Reader;
import cs3500.pa04.view.View;
import cs3500.pa04.view.Writer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

/**
 * The Driver is responsible for connecting to the server
 * and then running an entire game with a player.
 */
public class Driver {
  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(Readable in, String host, int port)
      throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);


    ProxyController proxyController =
        new ProxyController(server, new ComputerPlayer(new Random(), true));
    proxyController.run();
  }

  /**
   * The main entrypoint into the code as the Client. Given a host and port as parameters, the
   * client is run. If there is an issue with the client or connecting,
   * an error message will be printed.
   *
   * @param args The expected parameters are the server's host and port
   */
  public static void main(String[] args) {
    Readable in = new InputStreamReader(System.in);

    try {
      if (args.length == 2) {
        try {
          runClient(in, args[0], Integer.parseInt(args[1]));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      } else if (args.length == 0) {
        humanGame();
      } else {
        System.out.println("Expected two arguments: `[host] [port]`.");
      }
    } catch (NumberFormatException e) {
      System.out.println("Second argument should be an integer. Format: `[host] [part]`.");
    }
  }

  private static void humanGame() {
    Reader reader = new Reader(new InputStreamReader(System.in));
    Writer consoleWriter = new ConsoleWriter(System.out);
    View consoleView = new ConsoleView(consoleWriter, reader);
    Random random = new Random();

    GameController gc = new GameController(consoleView, reader, consoleWriter, random);
    gc.initializeGame();
  }
}
