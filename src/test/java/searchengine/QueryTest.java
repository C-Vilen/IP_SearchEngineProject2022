// Packages
package searchengine;

// Imports
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

// QueryTest test class
@TestInstance(Lifecycle.PER_CLASS)
public class QueryTest {

    // Instance of QueryTest class object and fields for test data
    private static Query query;
    private static List<String> searchterm;
    private static Set<Page> testSet;
    private static Map<Page, Integer> searchForWordResults;
    private static Page page1;
    private static Page page2;
    private static Page page3;
    
    //setUp before all tests
    @BeforeAll 
    void setUp() {
        // Set up test data
        testSet = new HashSet<Page>();
        page1 = new Page("https://en.wikipedia.org/wiki/Japan", "Japan");
        testSet.add(page1);
        page2 = new Page("https://en.wikipedia.org/wiki/Battle_of_the_Java_Sea", "Battle of the Java Sea");
        testSet.add(page2);
        page3 = new Page("https://en.wikipedia.org/wiki/java", "Java");
        testSet.add(page3);

        //Creates a test fileReaderMap 
        Map<String, Set<Page>> fileReaderMap = new HashMap<String, Set<Page>>();
        fileReaderMap.put("searchword1", testSet);
        searchterm = new ArrayList<>();
        searchterm.add("Battle");
        searchterm.add("no");

        //Creates a test searchForWordResults
        searchForWordResults = new HashMap<>();
        searchForWordResults.put(page1, 1);
        searchForWordResults.put(page2, 1);
        searchForWordResults.put(page3, 1);

        // Creates a new QueryTest before all tests.
        query = new Query(fileReaderMap, searchterm);
    }

    @AfterEach 
    void tearDown() {
        // Set up test data
        testSet = new HashSet<Page>();
        page1 = new Page("https://en.wikipedia.org/wiki/Japan", "Japan");
        testSet.add(page1);
        page2 = new Page("https://en.wikipedia.org/wiki/Battle_of_the_Java_Sea", "Battle of the Java Sea");
        testSet.add(page2);
        page3 = new Page("https://en.wikipedia.org/wiki/java", "Java");
        testSet.add(page3);

        //Creates a test fileReaderMap 
        Map<String, Set<Page>> fileReaderMap = new HashMap<String, Set<Page>>();
        fileReaderMap.put("searchword1", testSet);
        searchterm = new ArrayList<>();
        searchterm.add("Battle");
        searchterm.add("no");

        //Creates a test searchForWordResults
        searchForWordResults = new HashMap<>();
        searchForWordResults.put(page1, 1);
        searchForWordResults.put(page2, 1);
        searchForWordResults.put(page3, 1);

        // Creates a new QueryTest before all tests.
        query = new Query(fileReaderMap, searchterm);
    }

    //Tests the ORFiltering method by checking that the method seperates the searchterm at OR
    @Test 
    void ORFiltering_seperatingSearchtermAtOR(){
        searchterm.add("OR");
        searchterm.add("sorry");
        query.ORFiltering(searchterm);
        List<String> firstSetOfSearchwords = new ArrayList<String>();
        List<String> secondSetOfSearchwords = new ArrayList<String>();
        List<List<String>> outputListOR = new ArrayList<>();
        firstSetOfSearchwords.add("Battle");
        firstSetOfSearchwords.add("no");
        secondSetOfSearchwords.add("sorry");
        outputListOR.add(firstSetOfSearchwords);
        outputListOR.add(secondSetOfSearchwords);
        assertEquals(outputListOR, query.ORFiltering(searchterm));
    }

    //Tests the findingSearchtermRankforPage method checking that the right searchtermRank is assigned
    @Test 
    void findingSearchtermRankforPage_methodTest(){
        List<String> ORFilteredTest = new ArrayList<String>();
        ORFilteredTest.add("Battle");
        ORFilteredTest.add("no");
        HashMap<String, Integer> rankingMapTest = new HashMap<String, Integer>();
        rankingMapTest.put("Battle", 10);
        page2.setRankingMap(rankingMapTest);
        query.findingSearchtermRankforPage(testSet, ORFilteredTest);
        assertEquals(10, page2.getSearchtermRank());
    }

    @Test 
    void wordFiltering_FilteringTheListOfPages(){
        // Set up test data
        List<Page> filteredResults = new ArrayList<>();
        filteredResults = query.wordFiltering(searchForWordResults, 1);

        assertEquals(3, filteredResults.size());
    }

    @Test
    void searchForWord(){
        // Set up test data
        List<String> listOfSearchterm = new ArrayList<>();
        listOfSearchterm.add("Battle");
        listOfSearchterm.add("OR");
        listOfSearchterm.add("searchword1");

        List<Page> expectedOutputList = new ArrayList<>();
        expectedOutputList.addAll(testSet);

        List<Page> outputResults = new ArrayList<>();
        outputResults = query.searchForWord(listOfSearchterm);

        assertEquals(expectedOutputList, outputResults);
    }
}