import java.util.*;

class ParkingSpot {
    String licensePlate;
    long entryTime;
    boolean occupied;

    ParkingSpot() {
        occupied = false;
    }
}

public class ParkingLotSystem {

    int SIZE = 500;
    ParkingSpot[] table = new ParkingSpot[SIZE];
    int occupiedSpots = 0;
    int totalProbes = 0;
    int totalParks = 0;

    public ParkingLotSystem() {
        for (int i = 0; i < SIZE; i++) {
            table[i] = new ParkingSpot();
        }
    }

    // Hash function
    public int hash(String plate) {
        return Math.abs(plate.hashCode()) % SIZE;
    }

    // Park vehicle using linear probing
    public void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index].occupied) {
            index = (index + 1) % SIZE;
            probes++;
        }

        table[index].licensePlate = plate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].occupied = true;

        occupiedSpots++;
        totalProbes += probes;
        totalParks++;

        System.out.println("Vehicle " + plate +
                " parked at spot #" + index +
                " (" + probes + " probes)");
    }

    // Exit vehicle
    public void exitVehicle(String plate) {

        for (int i = 0; i < SIZE; i++) {

            if (table[i].occupied && table[i].licensePlate.equals(plate)) {

                long duration = System.currentTimeMillis() - table[i].entryTime;
                double hours = duration / 3600000.0;
                double fee = hours * 5; // $5 per hour

                table[i].occupied = false;
                occupiedSpots--;

                System.out.println("Vehicle " + plate +
                        " exited from spot #" + i +
                        ", Duration: " + String.format("%.2f", hours) +
                        " hours, Fee: $" + String.format("%.2f", fee));

                return;
            }
        }

        System.out.println("Vehicle not found.");
    }

    // Statistics
    public void getStatistics() {

        double occupancy = (occupiedSpots * 100.0) / SIZE;
        double avgProbes = totalParks == 0 ? 0 : (double) totalProbes / totalParks;

        System.out.println("Occupancy: " + String.format("%.2f", occupancy) + "%");
        System.out.println("Average Probes: " + String.format("%.2f", avgProbes));
    }

    public static void main(String[] args) {

        ParkingLotSystem system = new ParkingLotSystem();

        system.parkVehicle("ABC-1234");
        system.parkVehicle("ABC-1235");
        system.parkVehicle("XYZ-9999");

        system.exitVehicle("ABC-1234");

        system.getStatistics();
    }
}