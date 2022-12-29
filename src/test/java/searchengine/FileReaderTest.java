// Packages
package searchengine;

// Imports
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

// FileReader test class
@TestInstance(Lifecycle.PER_CLASS)
class FileReaderTest {

    // Instance of FileReader class object
        private static FileReader fileReader;
        private static final String datafile = "data/test-file.txt";
        private static final String wrongDataFilePath = "wrongData.txt";
        private static Map<String, Set<Page>> expectedOutputMap;

    
    // Creates a new FileReader and an expectedOutputMap with data from test-file.txt
    @BeforeAll static void setUp() throws IOException {
        // Create a FileReader object with a test file
        fileReader = new FileReader(datafile);

        // Creating Pages for testsets similar to the Pages created from test-file.txt
        Page testPage1 = new Page("http://page1.com", "title1");
        Page testPage2 = new Page("http://page2.com", "title2");

        // // Creating sets for expectedOutputMap and adding elements according to test-file.txt
        Set<Page> word1 = new HashSet<>();
        word1.add(testPage1);
        word1.add(testPage2);

        Set<Page> word2 = new HashSet<>();
        word2.add(testPage1);

        Set<Page> word3 = new HashSet<>();
        word3.add(testPage2);

        Set<Page> title1 = new HashSet<>();
        title1.add(testPage1);

        Set<Page> title2 = new HashSet<>();
        title2.add(testPage2);

        // Creating expectedOutputMap
        expectedOutputMap = new HashMap<String, Set<Page>>();
        expectedOutputMap.put("word1", word1);
        expectedOutputMap.put("word3", word3);
        expectedOutputMap.put("title1", title1);
        expectedOutputMap.put("word2", word2);
        expectedOutputMap.put("title2", title2); 
    }

    @Test
    void testAddNewPageToRankingMap() {
        // Create a test page object
        Page testPage = new Page("http://page1.com", "title1");

        // Call the createRankingMap() method on the object
        fileReader.createRankingMap(testPage, "word1");

        // Initialize the expectedRankingMap object
        Map<String, Integer> expectedRankingMap = new HashMap<>();
        expectedRankingMap.put("word1", 1);

        // Verify that the rankingMap is correct
        assertEquals(expectedRankingMap, testPage.getRankingMap());
    }

    @Test
    void createRankingMap_addingExistingKeyIncreasingValue(){
        // Instantiating and setting Map with expected output
        Map<String, Integer> expectedOutputMap = new HashMap<String, Integer>();
        expectedOutputMap.put("word1", 2);

        // Instantiate testPage1
        Page testPage1 = new Page("http://page1.com", "title1");

        // Call the method on testPage1
        fileReader.createRankingMap(testPage1, "word1");

        // Call the method on testPage1 again
        fileReader.createRankingMap(testPage1, "word1");

        // Matching expected output with test scenario
        assertTrue(expectedOutputMap.equals(testPage1.getRankingMap()));
    }

    @Test
    void GetFile_loadingTestFileIntoBytesSuccesfully() {
        // Call the getFile() method on the object
        byte[] fileContents = fileReader.getFile(datafile);

        // Initialize the expectedFileContents array
        byte[] expectedFileContents = new byte[] {
            (byte)'*', (byte)'P', (byte)'A', (byte)'G', (byte)'E', (byte)':', (byte)'h', (byte)'t', (byte)'t', (byte)'p', (byte)':', (byte)'/',
            (byte)'/', (byte)'p', (byte)'a', (byte)'g', (byte)'e', (byte)'1', (byte)'.', (byte)'c', (byte)'o', (byte)'m', (byte)'\n',
            (byte)'t', (byte)'i', (byte)'t', (byte)'l', (byte)'e', (byte)'1', (byte)'\n', (byte)'w', (byte)'o', (byte)'r', (byte)'d', (byte)'1',
            (byte)'\n', (byte)'w', (byte)'o', (byte)'r', (byte)'d', (byte)'1', (byte)'\n', (byte)'w', (byte)'o', (byte)'r', (byte)'d', (byte)'2',
            (byte)'\n', (byte)'*', (byte)'P', (byte)'A', (byte)'G', (byte)'E', (byte)':', (byte)'h', (byte)'t', (byte)'t', (byte)'p', (byte)':',
            (byte)'/', (byte)'/', (byte)'p', (byte)'a', (byte)'g', (byte)'e', (byte)'2', (byte)'.', (byte)'c', (byte)'o', (byte)'m', (byte)'\n',
            (byte)'t', (byte)'i', (byte)'t', (byte)'l', (byte)'e', (byte)'2', (byte)'\n', (byte)'w', (byte)'o', (byte)'r', (byte)'d', (byte)'1',
            (byte)'\n', (byte)'w', (byte)'o', (byte)'r', (byte)'d', (byte)'3'
          };

        // Verify that the fileContents is correct
        assertArrayEquals(expectedFileContents, (fileContents));
    }

    @Test
    void GetFile_failedLoadingTestFileIntoBytes() {
        // Call the getFile() method on the object BUT with a non-existing file path
        byte[] failedFileContents = fileReader.getFile(wrongDataFilePath);

        // Initialize the expectedFailedFileContents array
        byte[] expectedFailedFileContents = new byte[0];

        // assert Arrays.equals(expectedFailedFileContents, failedFileContents);
        assertArrayEquals(expectedFailedFileContents, failedFileContents);
    }

    @Test
    void testReadFile() {
        try {

            // Call the readFile() method on the object
            Map<String, Set<Page>> outputMap;
            outputMap = fileReader.readFile();

            // Verify that the outputMap is correct
            assertEquals(expectedOutputMap.keySet(), outputMap.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }

    // Checking for appropriate keys in readFile outputMap
    @Test
    void readFile_testOfKeysInOutputMap(){

        Map<String, Set<Page>> testOutMap = new HashMap<>();

        try {
            testOutMap = fileReader.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expectedOutputMap.keySet(), testOutMap.keySet());
    }


        // Checking for appropriate values in readFile outputMap
           
    @Test
    void readFile_testOfValuesInOutputMap(){
        Map<String, Set<Page>> testOutMap = new HashMap<>();
    
        try {
            testOutMap = fileReader.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expectedOutputMap.values().size(), testOutMap.values().size());
    }


    // Checking to see if method moves to catch block when input is faulty.
    @Test
        void readFile_testIfExceptionIsCaught(){
        fileReader = new FileReader(wrongDataFilePath);
        boolean testVar = false;

        try {
            fileReader.readFile();
        } catch (IOException e) {
            e.printStackTrace();
            testVar = true;
        }
        assertTrue(testVar);
    }    
}