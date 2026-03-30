import java.util.*;

public class PlagiarismDetector {

    // n-gram -> set of document IDs
    HashMap<String, Set<String>> index = new HashMap<>();

    // documentId -> list of n-grams
    HashMap<String, List<String>> documents = new HashMap<>();

    int N = 5; // 5-gram


    // Generate n-grams
    public List<String> generateNGrams(String text) {

        String[] words = text.toLowerCase().split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }

            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }


    // Add document to database
    public void addDocument(String docId, String text) {

        List<String> ngrams = generateNGrams(text);
        documents.put(docId, ngrams);

        for (String gram : ngrams) {

            index.putIfAbsent(gram, new HashSet<>());
            index.get(gram).add(docId);
        }

        System.out.println(docId + " indexed with " + ngrams.size() + " n-grams");
    }


    // Analyze a document for plagiarism
    public void analyzeDocument(String docId) {

        List<String> ngrams = documents.get(docId);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {

            Set<String> docs = index.getOrDefault(gram, new HashSet<>());

            for (String d : docs) {

                if (!d.equals(docId)) {
                    matchCount.put(d, matchCount.getOrDefault(d, 0) + 1);
                }
            }
        }

        System.out.println("\nAnalyzing " + docId);

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);
            double similarity = (matches * 100.0) / ngrams.size();

            System.out.println("Found " + matches +
                    " matching n-grams with " + doc);

            System.out.println("Similarity: " +
                    String.format("%.2f", similarity) + "%");

            if (similarity > 50) {
                System.out.println("PLAGIARISM DETECTED");
            }

            System.out.println();
        }
    }


    public static void main(String[] args) {

        PlagiarismDetector detector = new PlagiarismDetector();

        String essay1 = "machine learning is a field of artificial intelligence that focuses on data analysis and prediction";
        String essay2 = "machine learning is a branch of artificial intelligence that focuses on data analysis";
        String essay3 = "the history of computers began with early mechanical machines and evolved into modern systems";

        detector.addDocument("essay_089.txt", essay1);
        detector.addDocument("essay_092.txt", essay2);
        detector.addDocument("essay_123.txt", essay3);

        detector.analyzeDocument("essay_092.txt");
    }
}
