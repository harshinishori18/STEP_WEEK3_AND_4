import java.util.*;

class Client {
    String name;
    int riskScore;
    double accountBalance;

    Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return name + ":" + riskScore;
    }
}

public class ClientRiskRanking {

    // 🔹 Bubble Sort (Ascending by riskScore)
    public static void bubbleSort(Client[] arr) {
        int n = arr.length;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    // swap
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    swaps++;
                    swapped = true;
                }
            }

            if (!swapped) break; // optimization
        }

        System.out.println("Bubble Sort (Ascending): " + Arrays.toString(arr));
        System.out.println("Total Swaps: " + swaps);
    }

    // 🔹 Insertion Sort (Descending by riskScore + accountBalance)
    public static void insertionSort(Client[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Client key = arr[i];
            int j = i - 1;

            while (j >= 0 &&
                    (arr[j].riskScore < key.riskScore ||
                            (arr[j].riskScore == key.riskScore &&
                                    arr[j].accountBalance < key.accountBalance))) {

                arr[j + 1] = arr[j]; // shift
                j--;
            }

            arr[j + 1] = key;
        }

        System.out.println("Insertion Sort (Descending): " + Arrays.toString(arr));
    }

    // 🔹 Top N Highest Risk Clients
    public static void printTopClients(Client[] arr, int topN) {
        System.out.print("Top " + topN + " risks: ");
        for (int i = 0; i < Math.min(topN, arr.length); i++) {
            System.out.print(arr[i].name + "(" + arr[i].riskScore + ") ");
        }
        System.out.println();
    }

    // 🔹 Main Method
    public static void main(String[] args) {

        Client[] clients = {
                new Client("clientC", 80, 5000),
                new Client("clientA", 20, 10000),
                new Client("clientB", 50, 7000)
        };

        // Clone arrays
        Client[] bubbleArr = clients.clone();
        Client[] insertionArr = clients.clone();

        // Bubble Sort (ascending)
        bubbleSort(bubbleArr);

        // Insertion Sort (descending)
        insertionSort(insertionArr);

        // Top 10 (or fewer if less data)
        printTopClients(insertionArr, 10);
    }
}