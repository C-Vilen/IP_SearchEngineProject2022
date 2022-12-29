// Packages
package searchengine;

// Imports
import static org.junit.jupiter.api.Assertions.assertEquals;
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

// AlgorithmsTest test class
@TestInstance(Lifecycle.PER_CLASS)
public class AlgorithmsTest {
    private static Algorithms populatedAlgorithms;
    private static Algorithms emptyAlgorithms;

    @BeforeAll
    public static void setUpTestData() {
        // Set up test data
        Set<Page> outputSet = new HashSet<>();
        Page page1 = new Page("www.page1.com", "Page1");
        Map<String, Integer> rankingMap1 = new HashMap<>();
        rankingMap1.put("word1", 1);
        rankingMap1.put("word2", 2);
        rankingMap1.put("word3", 3);
        page1.setRankingMap(rankingMap1);
        Page page2 = new Page("www.page2.com", "Page2");
        Map<String, Integer> rankingMap2 = new HashMap<>();
        rankingMap2.put("word1", 1);
        rankingMap2.put("word2", 2);
        rankingMap2.put("word3", 3);
        page2.setRankingMap(rankingMap2);
        Page page3 = new Page("www.page3.com", "Page3");
        Map<String, Integer> rankingMap3 = new HashMap<>();
        rankingMap3.put("word1", 1);
        rankingMap3.put("word2", 2);
        rankingMap3.put("word3", 3);
        page3.setRankingMap(rankingMap3);
        outputSet.add(page1);
        outputSet.add(page2);
        outputSet.add(page3);
        int sizeOfPages = 6;
                
        // Setting setSearchtermRank for each page.
        int num = 1;
        for (Page page : outputSet) {
            page.setSearchtermRank(num);
            num++;
        }

        // Create a populated instance of Algorithms
        populatedAlgorithms = new Algorithms(outputSet, sizeOfPages);


        // Set up EMPTY test data
        Set<Page> emptyOutputSet = new HashSet<>();
        Page emptyPage1 = new Page("", "");
        Map<String, Integer> EmptyankingMap = new HashMap<>();
        emptyPage1.setRankingMap(EmptyankingMap);
        emptyOutputSet.add(emptyPage1);
        int EmptySizeOfPages = 0;
                
        // Setting setSearchtermRank for the EMPTY page.
        emptyPage1.setSearchtermRank(0);

        // Create an EMPTY instance of Algorithms
        emptyAlgorithms = new Algorithms(emptyOutputSet, EmptySizeOfPages);
    }

    @AfterEach
    void tearDown() {
       // Set up test data
       Set<Page> outputSet = new HashSet<>();
       Page page1 = new Page("www.page1.com", "Page1");
       Map<String, Integer> rankingMap1 = new HashMap<>();
       rankingMap1.put("word1", 1);
       rankingMap1.put("word2", 2);
       rankingMap1.put("word3", 3);
       page1.setRankingMap(rankingMap1);
       Page page2 = new Page("www.page2.com", "Page2");
       Map<String, Integer> rankingMap2 = new HashMap<>();
       rankingMap2.put("word1", 1);
       rankingMap2.put("word2", 2);
       rankingMap2.put("word3", 3);
       page2.setRankingMap(rankingMap2);
       Page page3 = new Page("www.page3.com", "Page3");
       Map<String, Integer> rankingMap3 = new HashMap<>();
       rankingMap3.put("word1", 1);
       rankingMap3.put("word2", 2);
       rankingMap3.put("word3", 3);
       page3.setRankingMap(rankingMap3);
       outputSet.add(page1);
       outputSet.add(page2);
       outputSet.add(page3);
       int sizeOfPages = 6;
               
       // Setting setSearchtermRank for each page.
       int num = 1;
       for (Page page : outputSet) {
           page.setSearchtermRank(num);
           num++;
       }

       // Create a populated instance of Algorithms
       populatedAlgorithms = new Algorithms(outputSet, sizeOfPages);


       // Set up EMPTY test data
       Set<Page> emptyOutputSet = new HashSet<>();
       Page emptyPage1 = new Page("", "");
       Map<String, Integer> EmptyankingMap = new HashMap<>();
       emptyPage1.setRankingMap(EmptyankingMap);
       emptyOutputSet.add(emptyPage1);
       int EmptySizeOfPages = 0;
               
       // Setting setSearchtermRank for the EMPTY page.
       emptyPage1.setSearchtermRank(0);

       // Create an EMPTY instance of Algorithms
       emptyAlgorithms = new Algorithms(emptyOutputSet, EmptySizeOfPages);
    }

    @Test 
    void sortPages_outputList(){
        // Sort the pages using the sortPages method
        List<Page> sortedPages = populatedAlgorithms.sortPages();
    
        // Verify that the pages are correctly sorted in descending order of searchtermRank
        assertEquals(3, sortedPages.get(0).getSearchtermRank());
        assertEquals(2, sortedPages.get(1).getSearchtermRank());
        assertEquals(1, sortedPages.get(2).getSearchtermRank());
    }

    @Test 
    void emptyPages_outputList(){
        // Sort the EMPTY page using the sortPages method
        List<Page> emptyPages = emptyAlgorithms.sortPages();
    
        // Verify that the EMPTY page is correctly sorted in descending order of searchtermRank
        assertEquals(0, emptyPages.get(0).getSearchtermRank());
    }
    

	@Test
	void setTermFrequencyScoreRank_withTermFrequencySort() {    
        // Calculate the term frequency scores using the termFrequencyScore method
        List<Page> populatedTermFrequencyScoredPages = populatedAlgorithms.termFrequencyScore();
    
        // Verify that the term frequency scores are correctly calculated and the pages are sorted in descending order of termFrequencyScoreRank
        assertEquals(0.5, populatedTermFrequencyScoredPages.get(0).getTermFrequencyScoreRank(), 0.001);
        assertEquals(0.3333333333333333, populatedTermFrequencyScoredPages.get(1).getTermFrequencyScoreRank(), 0.001);
        assertEquals(0.16666666666666666, populatedTermFrequencyScoredPages.get(2).getTermFrequencyScoreRank(), 0.001);
	}

    @Test
	void emptySetTermFrequencyScoreRank_withTermFrequencySort() {    
        // Calculate the term frequency score on the EMPTY page using the termFrequencyScore method
        List<Page> emptyTermFrequencyScoredPages = emptyAlgorithms.termFrequencyScore();
        double NAN = 0.0/0.0;
        // Verify that the term frequency score is Not A Number on the EMPTY page
        assertEquals(NAN, emptyTermFrequencyScoredPages.get(0).getTermFrequencyScoreRank());
	}

	@Test
	void populatedGetSizeOfPages() {
		// Calculate the size of the pages using the getSizeOfPages method
        int size = populatedAlgorithms.getSizeOfPages();

        // Verify that the size is correctly calculated
        assertEquals(6, size);
	}

    @Test
	void emptyGetSizeOfPages() {
		// Calculate the size of the EMPTY page using the getSizeOfPages method
        int size = emptyAlgorithms.getSizeOfPages();

        // Verify that the size is correctly calculated to ZERO
        assertEquals(0, size);
	}

	@Test
	void populatedSumOfPagesWithMatchingWords() {
        // Calculate the sum of pages with matching words using the sumOfPagesWithMatchingWords method
        int sum = populatedAlgorithms.sumOfPagesWithMatchingWords();

        // Verify that the sum is correctly calculated
        assertEquals(3, sum);
	}

    @Test
	void emptySumOfPagesWithMatchingWords() {
        // Calculate the sum of the EMPTY page with matching words using the sumOfPagesWithMatchingWords method
        int sum = emptyAlgorithms.sumOfPagesWithMatchingWords();

        // Verify that the sum is correctly calculated to zero
        assertEquals(0, sum);
	}


    @Test
	void populatedInverseDocumentFrequencyScore() {
		// Calculate the inverse document frequency scores using the InverseDocumentFrequencyScore method
        populatedAlgorithms.InverseDocumentFrequencyScore();

        // Verify that the inverse document frequency scores are correctly calculated
        assertEquals(0.6931471805599453, populatedAlgorithms.getPages(0).getInverseDocumentScoreRank(), 0.001);
        assertEquals(0.6931471805599453, populatedAlgorithms.getPages(1).getInverseDocumentScoreRank(), 0.001);
        assertEquals(0.6931471805599453, populatedAlgorithms.getPages(2).getInverseDocumentScoreRank(), 0.001);
	}

    @Test
	void emptyInverseDocumentFrequencyScore() {
		// Calculate the inverse document frequency scores using the InverseDocumentFrequencyScore method of the EMPTY page
        emptyAlgorithms.InverseDocumentFrequencyScore();
        double NAN = 0.0/0.0;

        // Verify that the inverse document frequency score is correctly calculated to Not A Number.
        assertEquals(NAN, emptyAlgorithms.getPages(0).getInverseDocumentScoreRank());
	}

	@Test
	void populatedTermFrequencyInverseDocumentFrequencyScore() {
		// Calculate the term frequency inverse document frequency scores using the TermFrequencyInverseDocumentFrequencyScore method
        populatedAlgorithms.termFrequencyInverseDocumentFrequencyScore();
    
        // Verify that the term frequency inverse document frequency scores are correctly calculated
        assertEquals(0.34657359027997264, populatedAlgorithms.getPages(0).getTermFrequencyInverseDocumentScoreRank(), 0.001);
        assertEquals(0.23104906018664842, populatedAlgorithms.getPages(1).getTermFrequencyInverseDocumentScoreRank(), 0.001);
        assertEquals(0.11552453009332421, populatedAlgorithms.getPages(2).getTermFrequencyInverseDocumentScoreRank(), 0.001);
	}

    @Test
	void empttTermFrequencyInverseDocumentFrequencyScore() {
		// Calculate the term frequency inverse document frequency scores using the TermFrequencyInverseDocumentFrequencyScore method of the EMPTY page
        emptyAlgorithms.termFrequencyInverseDocumentFrequencyScore();
        double NAN = 0.0/0.0;

        // Verify that the term frequency inverse document frequency score is correctly calculated to Not A Number.
        assertEquals(NAN, emptyAlgorithms.getPages(0).getTermFrequencyInverseDocumentScoreRank());
	}

	// @Test
	// void populatedTermFrequencyInverseDocumentFrequencySort() {
	// 	// Calculate and sort the term frequency inverse document frequency scores using the TermFrequencyInverseDocumentFrequencySort method
    //     List<Page> populatedPages = populatedAlgorithms.TermFrequencyInverseDocumentFrequencyScore();

    //     // Verify that the pages are correctly sorted by term frequency inverse document frequency scores 
    //     assertEquals("www.page1.com", populatedPages.get(0).getURL());
    //     assertEquals("www.page3.com", populatedPages.get(1).getURL());
    //     assertEquals("www.page2.com", populatedPages.get(2).getURL());
	// }

    @Test
	void emptyTermFrequencyInverseDocumentFrequencySort() {
		// Calculate and sort the term frequency inverse document frequency scores using the TermFrequencyInverseDocumentFrequencySort method of the EMPTY page
        List<Page> emptyPages = emptyAlgorithms.termFrequencyInverseDocumentFrequencyScore();

        // Verify that the EMPTY page is correctly sorted by term frequency inverse document frequency scores
        assertEquals("", emptyPages.get(0).getUrl());
	}
}
