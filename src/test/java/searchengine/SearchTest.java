// Packages
package searchengine;

// Imports
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

// SearchTest test class
class SearchTest {

    // Instance of SearchTest class object
    private static Search search;
    
    // Creates a new SearchTest before each test.
    @BeforeAll static void setUp() {
        Page page1 = new Page("www.page1.com", "Page1");
        Page page2 = new Page("www.page2.com", "Page2");
        Page page3 = new Page("www.page3.com", "Page3");
        Set<Page> setPage1 = new HashSet<>();
        setPage1.add(page3);
        setPage1.add(page2);
        setPage1.add(page1);
        Map<String, Set<Page>> searchMap = new HashMap<>();
        searchMap.put("search", setPage1);
        search = new Search(searchMap);
    }
    @Test 
    void listHttpIO () {
        // List<String> outputLisst = outpustList

    }





    // @Test
    // void sendingSearchOutputToFrontend () {
    //     assertEquals()
    // }
}



//assertEquals
//Map<String, Set<Page>> searchMap = new HashMap<>();
// 2 set af pages og 2 strings, som derefter bliver givet til mappet