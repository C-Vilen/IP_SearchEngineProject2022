
// Packages
package searchengine;

// Imports
import java.io.IOException;

/** 
 * Main class
 * <p>
 * This class is responsible creating an instance of the WebServer and starting the application.
 * 
 * @author      Christian Vilen
 * @author      Marcus Holst Garner
 * @author      Julie Abildgaard Jacobsen
 * @author      Kristoffer Lyduch
 * @since       1.0
 */
public class Main {
  private static final int PORT = 8080;
  private static final String datafile = "config.txt";
    
  /** Static void main that starts the frontend by calling the WebServer class.
   * @throws IOException can be trown if a datafile cannot be read. 
   * @param args takes the string arguments into account.
   */
  public static void main(String[] args) throws IOException {
    new WebServer(PORT, datafile);
  } 
}