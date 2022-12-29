// Packages
package searchengine;

// Imports
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/** 
 * Algorithms class.
 * <p>
 * This class is responsible for sorting our various views in the frontend.
 * This includes the following methods:
 * <ul>
 * <li> sortPages: The normal sorting method on the highest amount of matches.
 * <li> termFrequencyScoreRank: The method to rank each page according to their individual term frequency.
 * <li> termFrequencySort: The method to sort the pages according to their individual term frequency score.
 * <li> getSizeOfPages: The method to return the sum of pages.
 * <li> sumOfPagesWithMatchingWords: The method to score the amount of matching words for each pages.
 * <li> InverseDocumentFrequencyScore: The method to rank each page according to their individual inverse document frequency.
 * <li> termFrequencyInverseDocumentFrequencyScore: The method to rank each page according to their individual term frequency inverse document frequency.
 * <li> termFrequencyInverseDocumentFrequencySort: The method to sort the pages according to their individual term frequency inverse document frequency score.
 * <li> getPages: The method to return the specific page from the list of pages.
 * </ul>
 * 
 * @author      Christian Vilen
 * @author      Marcus Holst Garner
 * @author      Julie Abildgaard Jacobsen
 * @author      Kristoffer Lyduch
 * @since       1.0
 */
public class Algorithms {
    private List<Page> outputList;
    private int sizeOfPages;

    /** Constructor for Algorithms.
     * @param outputSet is the set of pages to be used.
     * @param sizeOfPages is the sum of current pages.
     */
    public Algorithms(Set<Page> outputSet, int sizeOfPages){
        outputList = new ArrayList<>();
        outputList.addAll(outputSet);
        this.sizeOfPages = sizeOfPages;
    }

    /** Normal sort of list of pages on searchtermRank.
     * @return <code>List</code> of pages that is sorted on their searchtermRank
     */
    public List<Page> sortPages(){
        outputList.sort((p1, p2) -> 
            p1.getSearchtermRank()-p2.getSearchtermRank());;
        Collections.reverse(outputList);
        return outputList;
    }

    /** Setting the termFrequencyScoreRank for each page and sorts the view on their ranking.
     * @return <code>List</code> of pages that is sorted on their term frequency score. The sorting is done by the termFrequencySort method.
     */
    public List<Page> termFrequencyScore(){
        for (Page page : outputList) {
            double termFrequencyCalculation = ((double) (page.getSearchtermRank())/ (double) (page.sumOfRankingMap()));
            page.setTermFrequencyScoreRank(termFrequencyCalculation);
        }
        return termFrequencySort();
    }

    /** Term Frequency sort of pages on TermFrequencyScoreRank.
     * @return <code>List</code> of pages that is sorted on their term frequency score.
     */
    public List<Page> termFrequencySort(){
        outputList.sort((p1, p2) -> 
            Double.compare(p1.getTermFrequencyScoreRank(), p2.getTermFrequencyScoreRank()));
        Collections.reverse(outputList);
        return outputList;
    }

    /** Creates the sum of page.
     * @return <code>int</code> that is the sum of pages.
     */
    public int getSizeOfPages(){
        int sumOfPagesRankingMaps = sizeOfPages;
        return sumOfPagesRankingMaps;
    }

    /** Creates the sum of pages with matching words.
     * @return <code>int</code> that is the sum of pages with matching words.
     */
    public int sumOfPagesWithMatchingWords(){
        int sumOfPagesWithMatchingWords = 0;
        for (Page page : outputList) {
            if (page.getSearchtermRank() > 0) {
                sumOfPagesWithMatchingWords = sumOfPagesWithMatchingWords + 1;
            }
        }
        return sumOfPagesWithMatchingWords;
    }

    /** Setting the inverseDocumentScoreRank for each pages.
     */
    public void InverseDocumentFrequencyScore(){
        double inverseDocumentScoreRank = 0.0;
        for (Page page : outputList) {
            inverseDocumentScoreRank = Math.log(((double) getSizeOfPages())/ (double) (sumOfPagesWithMatchingWords()));
            page.setInverseDocumentScoreRank(inverseDocumentScoreRank);
        }
    }

    /** Setting the termFrequencyInverseDocumentScoreRank for each page and sorts the view.
     * This method also calls and sets the following:
     * <ul>
     * <li> termFrequencyScoreRank: The method to rank each page according to their individual term frequency.
     * <li> InverseDocumentFrequencyScore: The method to rank each page according to their individual inverse document frequency.
     * </ul>
     * @return <code>List</code> of pages that is sorted on their term frequency inverse document score. The sorting is done by the termFrequencySort method.
     */
    public List<Page> termFrequencyInverseDocumentFrequencyScore(){
        // Setting the termFrequencyScore for each page.
        termFrequencyScore();
        // Setting the InverseDocumentFrequencyScore for each page.
        InverseDocumentFrequencyScore();
        double termFrequencyInverseDocumentScoreRank = 0.0;
        for (Page page : outputList) {
            termFrequencyInverseDocumentScoreRank = ((double) page.getTermFrequencyScoreRank()) * (double) (page.getInverseDocumentScoreRank());
            page.setTermFrequencyInverseDocumentScoreRank(termFrequencyInverseDocumentScoreRank);
        }
        return termFrequencyInverseDocumentFrequencySort();
    }

    /** Term Frequency sort of pages on TermFrequencyScoreRank.
     * @return <code>List</code> of page that is sorted on their termFrequencyInverseDocumentScoreRank.
     */
    public List<Page> termFrequencyInverseDocumentFrequencySort(){
        outputList.sort((p1, p2) -> 
            Double.compare(p1.getTermFrequencyInverseDocumentScoreRank(), p2.getTermFrequencyInverseDocumentScoreRank()));
        Collections.reverse(outputList);
        return outputList;
    }

    /** Returns the specific page from a list of pages
     * @param index for the index on the list
     * @return <code>Page</code> of page that is sorted on their termFrequencyInverseDocumentScoreRank.
     */
    public Page getPages(int index) {
        List<Page> getPages = new ArrayList<>();
        for (Page page : outputList) {
            getPages.add(page);
        }
        return getPages.get(index);
    }
}