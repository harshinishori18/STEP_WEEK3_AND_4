import java.util.*;

class TokenBucket {

    int maxTokens;
    int tokens;
    long lastRefillTime;
    int refillRate; // tokens per hour

    public TokenBucket(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.tokens = maxTokens;
        this.refillRate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    // refill tokens every hour
    public void refill() {

        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTime;

        if (elapsed >= 3600000) { // 1 hour = 3600000 ms
            tokens = maxTokens;
            lastRefillTime = now;
        }
    }

    public synchronized boolean allowRequest() {

        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }
}

public class RateLimiter {

    // clientId -> token bucket
    HashMap<String, TokenBucket> clients = new HashMap<>();

    int LIMIT = 1000;

    public boolean checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(LIMIT, LIMIT));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {

            System.out.println("Allowed (" + bucket.tokens + " requests remaining)");
            return true;

        } else {

            System.out.println("Denied (0 requests remaining, retry after 1 hour)");
            return false;
        }
    }

    public void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        if (bucket == null) {
            System.out.println("No requests yet.");
            return;
        }

        int used = LIMIT - bucket.tokens;

        System.out.println("{used: " + used +
                ", limit: " + LIMIT +
                ", remaining: " + bucket.tokens + "}");
    }

    public static void main(String[] args) {

        RateLimiter limiter = new RateLimiter();

        String client = "abc123";

        for (int i = 0; i < 5; i++) {
            limiter.checkRateLimit(client);
        }

        limiter.getRateLimitStatus(client);
    }
}