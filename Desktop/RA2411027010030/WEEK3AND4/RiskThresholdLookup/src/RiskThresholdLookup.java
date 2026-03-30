import java.util.*;

public class RiskThresholdLookup {

    // 🔹 Linear Search (unsorted)
    public static int linearSearch(int[] arr, int target) {
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("Linear: Found at index " + i +
                        " (Comparisons: " + comparisons + ")");
                return i;
            }
        }

        System.out.println("Linear: Not Found (Comparisons: " + comparisons + ")");
        return -1;
    }

    // 🔹 Binary Search (Insertion Point / Lower Bound)
    public static int insertionPoint(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Insertion Index: " + low +
                " (Comparisons: " + comparisons + ")");
        return low;
    }

    // 🔹 Floor (largest ≤ target)
    public static int floor(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int floor = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) return arr[mid];

            if (arr[mid] < target) {
                floor = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return floor;
    }

    // 🔹 Ceiling (smallest ≥ target)
    public static int ceiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int ceil = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) return arr[mid];

            if (arr[mid] > target) {
                ceil = arr[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ceil;
    }

    // 🔹 Main Method
    public static void main(String[] args) {

        int[] unsorted = {50, 10, 100, 25};
        int[] sorted = {10, 25, 50, 100};

        int target = 30;

        // Linear Search (unsorted)
        linearSearch(unsorted, target);

        // Binary Insertion Point
        insertionPoint(sorted, target);

        // Floor & Ceiling
        int floorVal = floor(sorted, target);
        int ceilVal = ceiling(sorted, target);

        System.out.println("Floor(" + target + "): " + floorVal);
        System.out.println("Ceiling(" + target + "): " + ceilVal);
    }
}