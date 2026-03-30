import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    int time; // minutes for simplicity

    Transaction(int id, int amount, String merchant, int time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.time = time;
    }
}

public class TransactionAnalyzer {

    List<Transaction> transactions = new ArrayList<>();


    // Add transaction
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }


    // Classic Two Sum
    public void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction t2 = map.get(complement);

                System.out.println("TwoSum Pair → (" + t2.id + ", " + t.id + ")");
            }

            map.put(t.amount, t);
        }
    }


    // Two Sum within 1 hour (60 minutes)
    public void findTwoSumWithTime(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction t2 = map.get(complement);

                if (Math.abs(t.time - t2.time) <= 60) {
                    System.out.println("Time Window Pair → (" + t2.id + ", " + t.id + ")");
                }
            }

            map.put(t.amount, t);
        }
    }


    // Duplicate detection (same amount + merchant)
    public void detectDuplicates() {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t);
        }

        for (String key : map.keySet()) {

            List<Transaction> list = map.get(key);

            if (list.size() > 1) {

                System.out.print("Duplicate Transactions → ");
                for (Transaction t : list) {
                    System.out.print("ID:" + t.id + " ");
                }
                System.out.println();
            }
        }
    }


    // K-Sum (simple recursive)
    public void findKSum(int k, int target) {
        kSumHelper(0, k, target, new ArrayList<>());
    }

    private void kSumHelper(int start, int k, int target, List<Integer> result) {

        if (k == 0 && target == 0) {
            System.out.println("KSum Result → " + result);
            return;
        }

        if (k == 0) return;

        for (int i = start; i < transactions.size(); i++) {

            Transaction t = transactions.get(i);

            result.add(t.id);

            kSumHelper(i + 1, k - 1, target - t.amount, result);

            result.remove(result.size() - 1);
        }
    }


    public static void main(String[] args) {

        TransactionAnalyzer analyzer = new TransactionAnalyzer();

        analyzer.addTransaction(new Transaction(1, 500, "StoreA", 600));
        analyzer.addTransaction(new Transaction(2, 300, "StoreB", 615));
        analyzer.addTransaction(new Transaction(3, 200, "StoreC", 630));
        analyzer.addTransaction(new Transaction(4, 500, "StoreA", 650));

        System.out.println("Two Sum:");
        analyzer.findTwoSum(500);

        System.out.println("\nTwo Sum within 1 hour:");
        analyzer.findTwoSumWithTime(500);

        System.out.println("\nDuplicate Detection:");
        analyzer.detectDuplicates();

        System.out.println("\nK Sum (k=3 target=1000):");
        analyzer.findKSum(3, 1000);
    }
}