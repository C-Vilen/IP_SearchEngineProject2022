// Packages
package searchengine;

// Imports
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/** 
 * WebServer class.
 * <p>
 * This class is responsible for starting the web server for the frontend.
 * This includes the following methods:
 * <ul>
 * <li> respond: The method for compiling the frontend.
 * </ul>
 * 
 * @author      Christian Vilen
 * @author      Marcus Holst Garner
 * @author      Julie Abildgaard Jacobsen
 * @author      Kristoffer Lyduch
 * @since       1.0
 */
public class WebServer {
  static final int BACKLOG = 0;
  static final Charset CHARSET = StandardCharsets.UTF_8;
  private FileReader fileReaderClass;
  private Search searchClass;
  private String filename;
  HttpServer httpServer;   

  /** Constructor for WebServer.
   * @param PORT is the port to start the webserver.
   * @param datafile is the datafile to start the webserver.
   * @throws IOException can be trown if a datafile cannot be read. 
   */
  public WebServer(int PORT, String datafile) throws IOException { 
    filename = Files.readString(Paths.get(datafile)).strip();
    fileReaderClass = new FileReader(filename); 
    searchClass = new Search(fileReaderClass.readFile());
    httpServer = HttpServer.create(new InetSocketAddress(PORT), BACKLOG);
    httpServer.createContext("/", io -> respond(io, 200, "text/html", fileReaderClass.getFile("web/index.html")));
    httpServer.createContext("/search", io -> respond(io, 200, "application/json", (searchClass.searchOutput(io).toString().getBytes(CHARSET))));
    httpServer.createContext(
        "/favicon.ico", io -> respond(io, 200, "image/x-icon", fileReaderClass.getFile("web/favicon.ico")));
    httpServer.createContext(
        "/code.js", io -> respond(io, 200, "application/javascript", fileReaderClass.getFile("web/code.js")));
    httpServer.createContext(
        "/style.css", io -> respond(io, 200, "text/css", fileReaderClass.getFile("web/style.css")));
    httpServer.start();
    String msg = " WebServer running on http://localhost:" + PORT + " ";
    System.out.println("╭"+"─".repeat(msg.length())+"╮");
    System.out.println("│"+msg+"│");
    System.out.println("╰"+"─".repeat(msg.length())+"╯");
  }

  /** The method for compiling the frontend.
     * @param io takes a the input HttpExchange for the frontend.
     * @param code takes a the int code for the frontend.
     * @param mime takes a the location path string for the frontend.
     * @param response takes a the bytes of the location path for the frontend.
   * <code>void</code> to the constructor can create the frontend.
   */
  public void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders()
          .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) {
    } finally {
      io.close();
    }
  }
}