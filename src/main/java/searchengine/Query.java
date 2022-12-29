//Packages
package searchengine;

//Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.*;

/** 
 * Query class.
 * <p>
 * This class is responsible for dividing the searchterms into seperate lists and querying the results.
 * This includes the following methods:
 * <ul> 
 * <li> searchForWord: Searching for words by using wordFiltering and ORFiltering methods to create output List of Pages.
 * <li> wordFiltering: Finding webpages common for several search words. 
 * <li> ORFiltering: Creates new List of search words when seeing an OR string.
 * <li> findingSearchtermRankforPage: Calls the calculateSearchtermRank method for the Set of Pages and ORFiltered list of string.
 * </ul>
 * 
 * @author      Christian Vilen
 * @author      Marcus Holst Garner
 * @author      Julie Abildgaard Jacobsen
 * @author      Kristoffer Lyduch
 * @since       1.0
 */
public class Query {
    private Map<String, Set<Page>> pages;
    private List<String> searchTerm;
    
    /** Constructor for Query.
   * @param map is the query map.
   * @param searchTerm list of searcterm strings.
   */
    public Query(Map<String, Set<Page>> map, List<String> searchTerm){
        this.pages = map;
        this.searchTerm = new ArrayList<>(searchTerm);
    }

    /** The method search for words by using wordFiltering and ORFiltering methods to create output List of Pages.
     * @param searchTerm takes a list of String as input from the searchTerms
   * @return <code>List</code> of pages that has the searchTerms
   */
    public List<Page> searchForWord(List<String> searchTerm) {
        List<List<String>> ORfilteredList = ORFiltering(searchTerm);
        Set<Page> outputSet = new HashSet<Page>();
        for (List<String> ORFiltered : ORfilteredList) {
            int ORFilteredSize = ORFiltered.size();
            Map<Page, Integer> results = new HashMap<>();
            Set<Page> outputListORFiltered = new HashSet<>();
            //Creating a results map with Page as key and counting the number of time that Page is in the Map 
            //when going through the different searchwords.
            for (String searchWord : ORFiltered) {
                if (pages.containsKey(searchWord)){
                    pages.get(searchWord).forEach((Page) -> 
                        results.put(Page, 1 + results.getOrDefault(Page, 0)));
                }
            }
            // Populate Set of Pages and calls the setSearchtermRank method
            outputListORFiltered.addAll(wordFiltering(results, ORFilteredSize));
            findingSearchtermRankforPage(outputListORFiltered, ORFiltered);            
            
            // Prepares the collection of set of pages for the sorting algorithm
            outputSet.addAll(wordFiltering(results, ORFilteredSize));
        }
        Algorithms SortingAlgorithms = new Algorithms(outputSet, pages.size());
        // NORMAL SORT
        // return SortingAlgorithms.sortPages();

        // TERM FREQUENCY SORT
        // return SortingAlgorithms.termFrequencyScore();

        // TERM FREQUENCY INVERSE DOCUMENT SORT
        return SortingAlgorithms.termFrequencyInverseDocumentFrequencyScore();
    }

    /** The method findings the webpages common for several search words. 
     * @param searchForWordResults takes a map of word results.
     * @param currentListSize takes an int of the currentListSize.
   * @return <code>List</code> of pages that has the searchTerms
   */
    public List<Page> wordFiltering(Map<Page, Integer> searchForWordResults, int currentListSize) {
        List<Page> filteredResults = new ArrayList<>(); 
        Map<Page, Integer> filteredMap = searchForWordResults.entrySet()
            .stream()
            .filter(key -> key.getValue() == currentListSize)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        filteredMap.forEach((key, value) -> filteredResults.add(key));
        return filteredResults;
    }

    /** The method creates a new List of search words when seeing an OR string.
     * @param searchTerm takes a list of String as input from the searchTerms
   * @return <code>List</code> of list of pages that has the searchTerms
   */
    public List<List<String>> ORFiltering(List<String> searchTerm) {
        List<List<String>> outputListOR = new ArrayList<>();
        int indexCounter = 0;
        for (int index = 0; index < searchTerm.size(); index++) {
            if (searchTerm.get(index).equals("OR")) {
                outputListOR.add(searchTerm.subList(indexCounter, index));
                indexCounter = index + 1;
            } else if (index+1 >= searchTerm.size()) {
                outputListOR.add(searchTerm.subList(indexCounter, index+1));
            }
        }
        return outputListOR;
    }

    /** The method calls the calculateSearchtermRank method for the Set of Pages and ORFiltered list of string.
     * @param outputListORFiltered takes a set of pages that has been filtered by the OR search string.
     * @param ORFiltered takes a list of String that has been filtered by the OR search string.
   * <code>void</code> calls the calculateSearchtermRank to set the searchTermRank for each page.
   */
    public void findingSearchtermRankforPage(Set<Page> outputListORFiltered, List<String> ORFiltered) {
        for (Page page : outputListORFiltered) {
            page.calculateSearchtermRank(ORFiltered);
        }
    }
}
