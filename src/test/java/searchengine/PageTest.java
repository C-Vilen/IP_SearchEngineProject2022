package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;


// Page test class
@TestInstance(Lifecycle.PER_CLASS)
class PageTest {
    //private String URL; 

    // Instance of Page class object
    private static Page page;
    
    //Creates a new Page before all tests.
    @BeforeAll static void setUp() {
        page = new Page("https://en.wikipedia.org/wiki/Japan", "Japan");
    }

    //Creates a new page before each test and creates a rankingMap
    @BeforeEach void beforeEach() {
        page = new Page("https://en.wikipedia.org/wiki/Japan", "Japan");
        HashMap<String, Integer> rankingMap = new HashMap<String, Integer>();
        rankingMap.put("japan", 3);
        rankingMap.put("more", 8);
        page.setRankingMap(rankingMap);
    }

    //Test that getTitle works, by getting the title from the field
    @Test void getTitle_fromField(){
        assertEquals("Japan", page.getTitle());
    }

    //Test that setURL works, by assigning an URL to the field
    @Test void setURL_assignsURLToField() {
        page.setUrl("https://en.wikipedia.org/wiki/United_States");
        assertEquals("https://en.wikipedia.org/wiki/United_States", page.getUrl());
    }

    //Tests that getURL works, by getting the URL from the field
    @Test void getURL_fromField(){
        assertEquals("https://en.wikipedia.org/wiki/Japan", page.getUrl());
    }

    //Tests that setTitle works by assigning a Title to the field
    @Test void setTitle_assignsTitleToField() {
        page.setTitle("United States");
        assertEquals("United States", page.getTitle());
    }

    //Tests that the set and get rankingMap works by giving the Page field a new rankingMap
    @Test void setAndGetRankingMap() {
        HashMap<String, Integer> rankingMapTest = new HashMap<String, Integer>();
        rankingMapTest.put("US", 10);
        page.setRankingMap(rankingMapTest);
        assertEquals(rankingMapTest, page.getRankingMap());
    }
    
    //Tests that the set and get TermFrequencyScoreRank works, by assigning and getting the value from the field
    @Test void setAndGetTermFrequencyScoreRank() {
        page.setTermFrequencyScoreRank(9.0);
        assertEquals(9.0, page.getTermFrequencyScoreRank());
    }

    //Tests that the set and get SearchtermRank Works when the if statements are fulfilled 
    //and the rankingMap contains a key that is in the List, and searchtermRank is smaller than temporaryScore
    @Test void setAndGetSearchtermRank_RankingMapContainsSearchword() {        
        List<String> testList = new ArrayList<String>();
        testList.add("japan");
        page.calculateSearchtermRank(testList);
        assertEquals(3, page.getSearchtermRank());
    }

    //Tests that the set and get SearchtermRank Works when the if statements are not fulfilled
    //and the RankingMap does not contain a key that is in the list, and the searchtermRank is bigger than the temporaryScore
    @Test void setAndGetSearchtermRank_RankingMapNotContainsSearchword() {        
        List<String> testList = new ArrayList<String>();
        testList.add("serious");
        page.calculateSearchtermRank(testList);
        assertEquals(0, page.getSearchtermRank());
    }

    //Tests that the SearchtermRank can be reset to 0
    @Test void resetSearchtermRank_to0() {
        page.resetSearchtermRank(0);
        assertEquals(0, page.getSearchtermRank());
    }

    //Tests that the sumOfRankingMap works
    @Test void sumOfRankingMap(){
        assertEquals(11, page.sumOfRankingMap());
    }

    //Tests the set and get method for InverseDocumentScoreRank
    @Test void getAndSetInverseDocumentScoreRank(){
        page.setInverseDocumentScoreRank(1.0);
        assertEquals(1.0, page.getInverseDocumentScoreRank());
    }

    //Tests the set and get method for the TermFrequencyInverseDocumentScroreRank method
    @Test void getAndSetTermFrequencyInverseDocumentScoreRank(){
        page.setTermFrequencyInverseDocumentScoreRank(0.9);
        assertEquals(0.9, page.getTermFrequencyInverseDocumentScoreRank());
    }
}
