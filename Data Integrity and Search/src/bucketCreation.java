import java.util.ArrayList;
import java.util.List;

public class bucketCreation {
    /**
     * @param input
     * @return
     */
    public static List<String> matrixTranslation(String input) {
        // Split the input text into individual words
        String[] words = input.split(" ");
        List<String> ml = new ArrayList<>();
        // Iterate over each word in the input
        for (String word : words) {
            // Iterate over each substring of the word
            for (int i = 2; i <= word.length(); i++) {
                String subword = word.substring(0, i);
                ml.add(subword);
            }
        }
        // Remove stopwords from the matrix
        List<String> stopwords = getStopwords();
        ml.removeAll(stopwords);
        return ml;

    }
    // Helper function to get a list of stopwords
    private static List<String> getStopwords() {
        List<String> stopwords = new ArrayList<>();
        // Add stopwords to the list
        stopwords.add("a");
        stopwords.add("an");
        stopwords.add("the");
        // Add more stopwords as needed
        return stopwords;
    }
}
