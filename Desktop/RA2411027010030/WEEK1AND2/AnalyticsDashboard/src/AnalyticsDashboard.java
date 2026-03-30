import java.util.*;

class Event {
    String url;
    String userId;
    String source;

    Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}

public class AnalyticsDashboard {

    // pageUrl -> visit count
    HashMap<String, Integer> pageViews = new HashMap<>();

    // pageUrl -> set of unique users
    HashMap<String, HashSet<String>> uniqueVisitors = new HashMap<>();

    // traffic source -> count
    HashMap<String, Integer> trafficSource = new HashMap<>();


    // Process incoming events
    public void processEvent(Event e) {

        // Update page view count
        pageViews.put(e.url, pageViews.getOrDefault(e.url, 0) + 1);

        // Update unique visitors
        uniqueVisitors.putIfAbsent(e.url, new HashSet<>());
        uniqueVisitors.get(e.url).add(e.userId);

        // Update traffic source count
        trafficSource.put(e.source, trafficSource.getOrDefault(e.source, 0) + 1);
    }


    // Get Top 10 pages
    public List<String> getTopPages() {

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        List<String> result = new ArrayList<>();
        int count = 0;

        while (!pq.isEmpty() && count < 10) {

            Map.Entry<String, Integer> entry = pq.poll();
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            result.add(url + " - " + views + " views (" + unique + " unique)");
            count++;
        }

        return result;
    }


    // Display dashboard
    public void getDashboard() {

        System.out.println("Top Pages:");

        List<String> topPages = getTopPages();
        int rank = 1;

        for (String page : topPages) {
            System.out.println(rank + ". " + page);
            rank++;
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;
        for (int val : trafficSource.values()) {
            total += val;
        }

        for (String source : trafficSource.keySet()) {

            int count = trafficSource.get(source);
            double percent = (count * 100.0) / total;

            System.out.println(source + " : " + String.format("%.2f", percent) + "%");
        }
    }


    // Main method
    public static void main(String[] args) {

        AnalyticsDashboard dashboard = new AnalyticsDashboard();

        dashboard.processEvent(new Event("/article/breaking-news", "user_123", "google"));
        dashboard.processEvent(new Event("/article/breaking-news", "user_456", "facebook"));
        dashboard.processEvent(new Event("/sports/championship", "user_789", "direct"));
        dashboard.processEvent(new Event("/sports/championship", "user_101", "google"));
        dashboard.processEvent(new Event("/article/breaking-news", "user_123", "google"));

        dashboard.getDashboard();
    }
}