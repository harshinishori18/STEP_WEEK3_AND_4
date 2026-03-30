import java.util.*;

class DNSEntry {
    String domain;
    String ipAddress;
    long expiryTime;

    DNSEntry(String domain, String ipAddress, int ttl) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + (ttl * 1000);
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

public class DNSCache {

    private int capacity = 5; // max cache size

    // LRU Cache
    LinkedHashMap<String, DNSEntry> cache =
            new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                    return size() > capacity;
                }
            };

    int hits = 0;
    int misses = 0;

    // Simulated upstream DNS lookup
    private String queryUpstreamDNS(String domain) {
        return "192.168.1." + new Random().nextInt(255);
    }

    public String resolve(String domain) {

        DNSEntry entry = cache.get(domain);

        // Cache HIT
        if (entry != null && !entry.isExpired()) {
            hits++;
            System.out.println("Cache HIT → " + entry.ipAddress);
            return entry.ipAddress;
        }

        // Cache MISS
        misses++;
        String ip = queryUpstreamDNS(domain);

        DNSEntry newEntry = new DNSEntry(domain, ip, 5); // TTL = 5 seconds
        cache.put(domain, newEntry);

        System.out.println("Cache MISS → Query upstream → " + ip);

        return ip;
    }

    public void getCacheStats() {

        int total = hits + misses;
        double hitRate = (total == 0) ? 0 : (hits * 100.0 / total);

        System.out.println("Cache Hits: " + hits);
        System.out.println("Cache Misses: " + misses);
        System.out.println("Hit Rate: " + String.format("%.2f", hitRate) + "%");
    }

    public static void main(String[] args) throws InterruptedException {

        DNSCache dns = new DNSCache();

        dns.resolve("google.com");
        dns.resolve("google.com"); // should be HIT

        Thread.sleep(6000); // wait for TTL expiry

        dns.resolve("google.com"); // expired → MISS

        dns.getCacheStats();
    }
}
