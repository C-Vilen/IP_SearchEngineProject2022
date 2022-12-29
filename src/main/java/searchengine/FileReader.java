// Packages
package searchengine;

// Imports
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/** 
 * FileReader class.
 * <p>
 * This class is responsible reading the datafile and creating the inverted index
 * This includes the following methods:
 * <ul>
 * <li> getFile: The method to get the datafile and load the contents into bytes.
 * <li> readFile: The method to read the datafile and convert it into an inverted index.
 * <li> mapingSearchwordsWithPages: The method to setup the searchword as key, set of pages as value and sets rankingMap for each Page.
 * <li> createRankingMap: The method to count the accurance of each word in a Page.
 * </ul>
 * 
 * @author      Christian Vilen
 * @author      Marcus Holst Garner
 * @author      Julie Abildgaard Jacobsen
 * @author      Kristoffer Lyduch
 * @since       1.0
 */
public class FileReader {
  private String datafile;
  private Map<String, Set<Page>> outputMap;

  /** Constructor for FileReader.
   * @param datafile is the location path for the datafile.
   */
  public FileReader(String datafile) {
    this.datafile = datafile;
    this.outputMap = new HashMap<String, Set<Page>>();
  }

  /** Retriving the datafile and load the contents into bytes.
   * @param datafile the file path of the data to be loaded into search engine.
   * @return <code>byte[]</code> the datafile contens converted into bytes.
   */
  public byte[] getFile(String datafile) {
    try {
      return Files.readAllBytes(Paths.get(datafile));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  /** Reads the datafile and convert it into an inverted index.
   * @throws IOException can be trown if a datafile cannot be read. 
   * @return <code>Map</code> the datafile converted into an inverted index.
   */
  public Map<String, Set<Page>> readFile() throws IOException {
    try {
      Map<String, Set<Page>> fileReaderMap = new HashMap<String, Set<Page>>();
      List<String> lines = Files.readAllLines(Paths.get(datafile));
      String urlString = "";
      int urlIndexPointer = 0;
      Page currentPage = new Page(urlString, lines.get(0));
      for (int index = 0; index < lines.size(); index++) {
        String lineIndexPointer = lines.get(index);
        if (lineIndexPointer.startsWith("*PAGE")) {
          urlIndexPointer = index;
          urlString = lineIndexPointer.substring(6);
          if (!lines.get(urlIndexPointer+1).startsWith("*PAGE") && !lines.get(urlIndexPointer+2).startsWith("*PAGE")) {
            currentPage = new Page(urlString, lines.get(index + 1));
          }
        } 
        else if (!lines.get(urlIndexPointer+1).startsWith("*PAGE") && !lines.get(urlIndexPointer+2).startsWith("*PAGE")) {
          mapingSearchwordsWithPages(fileReaderMap, lineIndexPointer, urlString, currentPage);
        }
      }
      outputMap = fileReaderMap;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return outputMap;
  }

  /** Setting up the searchword as key, set of pages as value and sets rankingMap for each Page.
   * @param fileReaderMap is the inverted index map that the readFile method creates.
   * @param lineIndexPointer is the current line on the index that is being pointed on.
   * @param urlString is the current url string that belongs to the page.
   * @param currentPage is the current page which is being created.
   * <code>void</code> adds the searchword as key, set of pages as value and sets rankingMap for each Page.
   */
  public void mapingSearchwordsWithPages(Map<String, Set<Page>> fileReaderMap, String lineIndexPointer, String urlString, Page currentPage){
    if (!fileReaderMap.containsKey(lineIndexPointer) && urlString != "") {
      Set<Page> SetOfUrl = new HashSet<Page>();
      SetOfUrl.add(currentPage);
      fileReaderMap.put(lineIndexPointer, SetOfUrl);
      createRankingMap(currentPage, lineIndexPointer);
    } 
    else {
      Set<Page> SetOfUrls = fileReaderMap.get(lineIndexPointer);
      SetOfUrls.add(currentPage);
      fileReaderMap.put(lineIndexPointer, SetOfUrls); 
      createRankingMap(currentPage, lineIndexPointer);
    }
  }         

  /** Counts the accurance of each word in a Page.
   * @param lineIndexPointer is the current line on the index that is being pointed on.
   * @param currentPage is the current page which is being created.
   * <code>void</code> adds the accurence each word occur on a Page.
   */
  public void createRankingMap(Page currentPage, String lineIndexPointer){
    Map<String, Integer> inputMap = currentPage.getRankingMap();
    if (currentPage.getRankingMap().containsKey(lineIndexPointer)) {
      int pageCounter = currentPage.getRankingMap().get(lineIndexPointer);
      inputMap.put(lineIndexPointer, pageCounter+1);
      currentPage.setRankingMap(inputMap); 
    } else {
      inputMap.put(lineIndexPointer, 1);
      currentPage.setRankingMap(inputMap); 
    }
  }
}