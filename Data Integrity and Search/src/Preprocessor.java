import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import opennlp.tools.stemmer.PorterStemmer;

public class Preprocessor {
    // List of stopwords
    private static final List<String> STOPWORDS = new ArrayList<String>() {{
        add("a");
        add("an");
        add("and");
        add("the");
        add("are");
        add("as");
        add("be");
        add("by");
        add("for");
        add("from");
        add("has");
        add("he");
        add("in");
        add("is");
        add("it");
        add("its");
        add("of");
        add("on");
        add("that");
        add("to");
        add("was");
        add("were");
        add("will");
        add("with");
    }};

    public static void main(String[] args) throws IOException {
        // Read input file
       String inputFilePath = "Testing.txt";
       String text = readFile(inputFilePath);
        // Preprocess text
        text = removeSpecialSymbols(text);
        List<String> tokens = tokenize(text);
        tokens = removeStopWords(tokens);

         // Create matrix
    List<String> matrix = bucketCreation.matrixTranslation(String.join(" ", tokens));

    // Print matrix
    System.out.println(String.join(" ", matrix));
    }

    static String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }
        br.close();
        return sb.toString();
    }

    static String removeSpecialSymbols(String text) {
        return text.replaceAll("[^a-zA-Z0-9\\s]", "");
    }

    static List<String> tokenize(String text) {
        StringTokenizer tokenizer = new StringTokenizer(text);
        List<String> tokens = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }

    static List<String> removeStopWords(List<String> tokens) {
        tokens.removeAll(STOPWORDS);
        return tokens;
    }
        private static List<String> stem(List<String> tokens) {
            PorterStemmer stemmer = new PorterStemmer();
            List<String> stemmedTokens = new ArrayList<>();
            for (String token : tokens) {
                stemmer.setCurrent(token);
                if (stemmer.stem()) {
                    stemmedTokens.add(stemmer.getCurrent());
                } else {
                    stemmedTokens.add(token);
                }
            }
            return stemmedTokens;
    }
}
