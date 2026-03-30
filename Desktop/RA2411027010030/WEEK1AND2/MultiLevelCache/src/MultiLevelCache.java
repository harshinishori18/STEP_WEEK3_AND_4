import java.util.*;

class VideoData {
    String videoId;
    String content;

    VideoData(String videoId, String content) {
        this.videoId = videoId;
        this.content = content;
    }
}

public class MultiLevelCache {

    // L1 cache (10k) simplified to 3 for demo
    LinkedHashMap<String, VideoData> L1 = new LinkedHashMap<>(16, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
            return size() > 3;
        }
    };

    // L2 cache (100k) simplified to 5 for demo
    LinkedHashMap<String, VideoData> L2 = new LinkedHashMap<>(16, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
            return size() > 5;
        }
    };

    // Simulated database
    HashMap<String, VideoData> database = new HashMap<>();

    int L1Hits = 0;
    int L2Hits = 0;
    int L3Hits = 0;

    public MultiLevelCache() {

        // preload database
        database.put("video_123", new VideoData("video_123", "Movie A"));
        database.put("video_456", new VideoData("video_456", "Movie B"));
        database.put("video_999", new VideoData("video_999", "Movie C"));
    }

    public VideoData getVideo(String id) {

        // L1 check
        if (L1.containsKey(id)) {
            L1Hits++;
            System.out.println("L1 Cache HIT (0.5ms)");
            return L1.get(id);
        }

        System.out.println("L1 Cache MISS");

        // L2 check
        if (L2.containsKey(id)) {
            L2Hits++;
            System.out.println("L2 Cache HIT (5ms)");

            VideoData v = L2.get(id);

            // promote to L1
            L1.put(id, v);

            return v;
        }

        System.out.println("L2 Cache MISS");

        // L3 database
        VideoData v = database.get(id);

        if (v != null) {
            L3Hits++;
            System.out.println("L3 Database HIT (150ms)");

            // add to L2
            L2.put(id, v);
        }

        return v;
    }

    public void getStatistics() {

        int total = L1Hits + L2Hits + L3Hits;

        System.out.println("\nCache Statistics:");

        if (total == 0) {
            System.out.println("No requests yet.");
            return;
        }

        System.out.println("L1 Hit Rate: " + (L1Hits * 100.0 / total) + "%");
        System.out.println("L2 Hit Rate: " + (L2Hits * 100.0 / total) + "%");
        System.out.println("L3 Hit Rate: " + (L3Hits * 100.0 / total) + "%");
    }

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        cache.getVideo("video_123"); // L3
        cache.getVideo("video_123"); // L1
        cache.getVideo("video_999"); // L3
        cache.getVideo("video_999"); // L1
        cache.getVideo("video_456"); // L3

        cache.getStatistics();
    }
}
