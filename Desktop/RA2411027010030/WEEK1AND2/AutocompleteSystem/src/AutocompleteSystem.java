import java.util.*;

public class AutocompleteSystem {

    // query -> frequency
    HashMap<String, Integer> queryFrequency = new HashMap<>();


    // Add or update query frequency
    public void updateFrequency(String query) {
        queryFrequency.put(query, queryFrequency.getOrDefault(query, 0) + 1);
    }


    // Search suggestions by prefix
    public List<String> search(String prefix) {

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

        for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {

            if (entry.getKey().startsWith(prefix)) {

                pq.offer(entry);

                if (pq.size() > 10) {
                    pq.poll();
                }
            }
        }

        List<String> result = new ArrayList<>();

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> entry = pq.poll();
            result.add(entry.getKey() + " (" + entry.getValue() + " searches)");
        }

        Collections.reverse(result);
        return result;
    }


    public static void main(String[] args) {

        AutocompleteSystem system = new AutocompleteSystem();

        // sample search data
        system.updateFrequency("java tutorial");
        system.updateFrequency("javascript");
        system.updateFrequency("java download");
        system.updateFrequency("java tutorial");
        system.updateFrequency("java 21 features");
        system.updateFrequency("java tutorial");

        // search suggestions
        List<String> suggestions = system.search("jav");

        System.out.println("Suggestions:");

        int rank = 1;
        for (String s : suggestions) {
            System.out.println(rank + ". " + s);
            rank++;
        }
    }
}
