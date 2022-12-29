// Packages
package searchengine;

// Imports
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * Page class of one page instance.
 * <p>
 * This class is responsible reading the data file and creating the inverted index
 * This includes the following methods:
 * <ul>
 * <li> getInverseDocumentScoreRank: The method returns the double for inverseDocumentScoreRank.
 * <li> setInverseDocumentScoreRank: The method sets the inverseDocumentScoreRank.
 * <li> getTermFrequencyInverseDocumentScoreRank: The method returns the double for termFrequencyInverseDocumentScoreRank.
 * <li> setTermFrequencyInverseDocumentScoreRank: The method sets the termFrequencyInverseDocumentScoreRank.
 * <li> getUrl: The method returns the String for url.
 * <li> setUrl The method sets the url.
 * <li> getTitle: The method returns the String for title.
 * <li> setTitle The method sets the title.
 * <li> getRankingMap: The method returns the Map for rankingMap.
 * <li> setRankingMap: The method sets the rankingMap.
 * <li> sumOfRankingMap: The method returns the sum of words that are in the rankingMap.
 * <li> getSearchtermRank: The method returns the int for searchtermRank.
 * <li> setSearchtermRank: The method sets the searchtermRank.
 * <li> calculateSearchtermRank: The method calculates the int for the searchtermRank based on the searchTerms and page.
 * <li> resetSearchtermRank: The method resets the searchtermRank for the pages.
 * <li> getTermFrequencyScoreRank: The method returns the double for termFrequencyScoreRank.
 * <li> setTermFrequencyScoreRank: The method sets the termFrequencyScoreRank.
 * </ul>
 * 
 * @author      Christian Vilen
 * @author      Marcus Holst Garner
 * @author      Julie Abildgaard Jacobsen
 * @author      Kristoffer Lyduch
 * @since       1.0
 */
public class Page {
    private String url;
    private String title;
    private Map<String, Integer> rankingMap;
    private int searchtermRank;
    private double termFrequencyScoreRank;
    private double inverseDocumentScoreRank;
    private double termFrequencyInverseDocumentScoreRank;

    /** Constructor for Page.
   * @param url is the url string for the page.
   * @param title is the title string for the page.
   */
    public Page(String url, String title){
        this.url = url;
        this.title = title;
        this.rankingMap = new HashMap<>();
        this.searchtermRank = 0;
        this.termFrequencyScoreRank = 0.0;
        this.inverseDocumentScoreRank = 0.0;
        this.termFrequencyInverseDocumentScoreRank = 0.0;
    }

    /** The method returns the double for inverseDocumentScoreRank.
   * @return <code>double</code> The double for inverseDocumentScoreRank.
   */
    public double getInverseDocumentScoreRank() {
        return inverseDocumentScoreRank;
    }

    /** The method sets the inverseDocumentScoreRank.
     * @param inverseDocumentScoreRank for the new inverseDocumentScoreRank.
   * <code>void</code> sets the inverseDocumentScoreRank.
   */
    public void setInverseDocumentScoreRank(double inverseDocumentScoreRank) {
        this.inverseDocumentScoreRank = inverseDocumentScoreRank;
    }

    /** The method returns the double for termFrequencyInverseDocumentScoreRank.
   * @return <code>double</code> The double for termFrequencyInverseDocumentScoreRank.
   */
    public double getTermFrequencyInverseDocumentScoreRank() {
        return termFrequencyInverseDocumentScoreRank;
    }

    /** The method sets the termFrequencyInverseDocumentScoreRank.
     * @param termFrequencyInverseDocumentScoreRank for the new termFrequencyInverseDocumentScoreRank.
   * <code>void</code> sets the termFrequencyInverseDocumentScoreRank.
   */
    public void setTermFrequencyInverseDocumentScoreRank(double termFrequencyInverseDocumentScoreRank) {
        this.termFrequencyInverseDocumentScoreRank = termFrequencyInverseDocumentScoreRank;
    }

     /** The method returns the String for url.
   * @return <code>String</code> The String for url.
   */
    public String getUrl() {
        return url;
    }

    /** The method sets the url.
    * @param url for the new url.
   * <code>String</code> sets the url.
   */
    public void setUrl(String url) {
        this.url = url;
    }

    /** The method returns the String for title.
   * @return <code>String</code> The String for title.
   */
    public String getTitle() {
        return title;
    }

    /** The method sets the title.
    * @param title for the new title.
   * <code>void</code> sets the title.
   */
    public void setTitle(String title) {
        this.title = title;
    }

    /** The method returns the Map for rankingMap.
   * @return <code>Map</code> The Map for rankingMap.
   */
    public Map<String, Integer> getRankingMap() {
        return rankingMap;
    }

    /** The method sets the rankingMap.
    * @param rankingMap for the new rankingMap.
   * <code>void</code> sets the rankingMap.
   */
    public void setRankingMap(Map<String, Integer> rankingMap) {
        this.rankingMap = rankingMap;
    }

    /** The method returns the sum of words that are in the rankingMap.
   * @return <code>int</code> sets the sumOfWords.
   */
    public int sumOfRankingMap(){
        int sumOfWords = 0;
        for (Integer amountOfMatch : rankingMap.values()) {
            sumOfWords = sumOfWords + amountOfMatch;
        }
        return sumOfWords;
    }

    /** The method returns the int for searchtermRank.
   * @return <code>int</code> The Map for searchtermRank.
   */
    public int getSearchtermRank() {
        return searchtermRank;
    }

    /** The method sets the searchtermRank.
    * @param searchtermRank for the new searchtermRank.
   * <code>void</code> sets the searchtermRank.
   */
    public void setSearchtermRank(int searchtermRank) {
        this.searchtermRank = searchtermRank;
    }

    /** The method calculates the int for the searchtermRank based on the searchTerms and page.
    * @param ORFiltered for the new ORFiltered.
   * <code>void</code> calculates the searchtermRank.
   */
    public void calculateSearchtermRank(List<String> ORFiltered) {
        int temporaryScore = 0;
        for (String searchWord : ORFiltered) {
            if (rankingMap.containsKey(searchWord)) {
                temporaryScore = temporaryScore + rankingMap.get(searchWord);
            }
        }
        if (searchtermRank < temporaryScore) {
            this.searchtermRank = temporaryScore;
        }
    }

    /** The method resets the searchtermRank.
     * @param searchtermRank is the new searchtermRank.
   * <code>void</code> resets the searchtermRank.
   */
    public void resetSearchtermRank(int searchtermRank) {
        this.searchtermRank = searchtermRank;
    }

    /** The method returns the double for termFrequencyScoreRank.
   * @return <code>double</code> The double for termFrequencyScoreRank.
   */
    public double getTermFrequencyScoreRank() {
        return termFrequencyScoreRank;
    }

    /** The method sets the termFrequencyScoreRank.
     * @param termFrequencyScoreRank is the new termFrequencyScoreRank
   * <code>void</code> sets the termFrequencyScoreRank.
   */
    public void setTermFrequencyScoreRank(double termFrequencyScoreRank) {
        this.termFrequencyScoreRank = termFrequencyScoreRank;
    }
}