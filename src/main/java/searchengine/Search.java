// Packages
package searchengine;

// Imports
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 
 * Search class.
 * <p>
 * This class is responsible call the advanced Query class and returning with search results.
 * This includes the following methods:
 * <ul>
 * <li> searchOutput: The method for sending the search output to the frontend.
 * </ul>
 * 
 * @author      Christian Vilen
 * @author      Marcus Holst Garner
 * @author      Julie Abildgaard Jacobsen
 * @author      Kristoffer Lyduch
 * @since       1.0
 */
public class Search{
    static final Charset CHARSET = StandardCharsets.UTF_8;
    private Map<String, Set<Page>> pages;
    private List<String> searchTerm;

    /** Constructor for Search.
   * @param map is the query map.
   */
    public Search(Map<String, Set<Page>> map){
        this.pages = map;
        searchTerm = new ArrayList<String>();
    }

    /** Method for sending the search output to the frontend.
     * @param io takes a the input HttpExchange from the frontend.
   * @return <code>List</code> of String that has the output results.
   * @throws IOException can be trown if a datafile cannot be read. 
   */
    public List<String> searchOutput(HttpExchange io) throws IOException {
        String[] searchInput = io.getRequestURI().getRawQuery().substring(2).split("%20");
        searchTerm.removeAll(searchTerm);
        searchTerm.addAll(Arrays.asList(searchInput));
        Query searchquery = new Query(pages, searchTerm);
        List<String> response = new ArrayList<>();
        for (Page page : searchquery.searchForWord(searchTerm)) {            
            response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\",\"matches\": \"%s\"}",
            page.getUrl(), page.getTitle(), page.getSearchtermRank()));
            page.resetSearchtermRank(0);
        }
        return response;
    }
}