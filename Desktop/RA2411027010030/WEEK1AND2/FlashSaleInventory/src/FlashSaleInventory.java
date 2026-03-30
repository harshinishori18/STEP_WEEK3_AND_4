import java.util.*;

public class FlashSaleInventory {

    // productId -> stock
    HashMap<String, Integer> stock = new HashMap<>();

    // productId -> waiting list of users
    HashMap<String, Queue<Integer>> waitingList = new HashMap<>();


    // Add product with stock
    public void addProduct(String productId, int quantity) {
        stock.put(productId, quantity);
        waitingList.put(productId, new LinkedList<>());
    }


    // Check stock availability
    public void checkStock(String productId) {

        int quantity = stock.getOrDefault(productId, 0);

        if (quantity > 0) {
            System.out.println(productId + " → " + quantity + " units available");
        } else {
            System.out.println(productId + " → Out of stock");
        }
    }


    // Purchase item (thread safe)
    public synchronized void purchaseItem(String productId, int userId) {

        int quantity = stock.getOrDefault(productId, 0);

        if (quantity > 0) {

            stock.put(productId, quantity - 1);

            System.out.println(
                    "User " + userId +
                            " → Purchase successful. Remaining stock: " +
                            (quantity - 1)
            );

        } else {

            Queue<Integer> queue = waitingList.get(productId);
            queue.add(userId);

            System.out.println(
                    "User " + userId +
                            " → Added to waiting list. Position #" +
                            queue.size()
            );
        }
    }


    // Display waiting list
    public void showWaitingList(String productId) {

        Queue<Integer> queue = waitingList.get(productId);

        System.out.println("Waiting List for " + productId + ": " + queue);
    }


    public static void main(String[] args) {

        FlashSaleInventory system = new FlashSaleInventory();

        system.addProduct("IPHONE15_256GB", 5); // small number for demo

        system.checkStock("IPHONE15_256GB");

        system.purchaseItem("IPHONE15_256GB", 101);
        system.purchaseItem("IPHONE15_256GB", 102);
        system.purchaseItem("IPHONE15_256GB", 103);
        system.purchaseItem("IPHONE15_256GB", 104);
        system.purchaseItem("IPHONE15_256GB", 105);

        // stock finished
        system.purchaseItem("IPHONE15_256GB", 106);
        system.purchaseItem("IPHONE15_256GB", 107);

        system.showWaitingList("IPHONE15_256GB");
    }
}