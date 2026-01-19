import java.util.Arrays;
import java.util.HashSet;

class Solution {
    public int latestTimeCatchTheBus(int[] buses, int[] passengers, int capacity) {

        // We want to find the LATEST possible time such that:
        // - We arrive at that time
        // - That time is NOT already taken by any passenger
        // - We can still board some bus with available capacity

        // Sort buses and passengers to simulate boarding in chronological order
        Arrays.sort(buses);
        Arrays.sort(passengers);

        // Store all passenger arrival times in a set
        // So we can quickly check if a time is already occupied
        HashSet<Integer> set = new HashSet<>();
        for (int p : passengers) set.add(p);

        // Binary search on the ANSWER (arrival time)
        // Minimum possible arrival time = 1
        // Maximum possible arrival time = last bus time
        int i = 1, j = buses[buses.length - 1];

        // This will store the best (latest) valid arrival time
        int ans = 0;

        while (i <= j) {

            int mid = (i + j) / 2;

            // We want the latest time <= mid that is NOT taken by any passenger
            // Because we cannot arrive at a time already occupied
            int validTime = getEarliestValidTime(i, mid, set);

            // If no valid free time exists in [i, mid],
            // then we must search in the right half
            if (validTime < i) {
                i = mid + 1;
            }

            // If this validTime allows us to catch some bus,
            // then it is a feasible answer
            else if (isPoss(buses, passengers, capacity, validTime)) {

                // Store it as a possible answer
                ans = validTime;

                // Try to find an even later valid time
                i = validTime + 1;
            }

            // If we cannot catch any bus with this time,
            // then this time is too late → move left
            else {
                j = mid - 1;
            }
        }

        // ans holds the maximum valid arrival time
        return ans;
    }

    // This function finds the latest time <= currTime that is NOT in passenger set
    // Because we are not allowed to arrive at a time already taken by a passenger
    private int getEarliestValidTime(int start, int currTime, HashSet<Integer> set) {

        // Move backwards until we find a free time
        while (currTime >= start && set.contains(currTime)) {
            currTime--;
        }

        // Returns the nearest free arrival time <= original currTime
        return currTime;
    }

    // This function checks:
    // If we arrive at 'arrivalTime', can we still catch some bus?
    private boolean isPoss(int[] buses, int[] passengers, int capacity, int arrivalTime) {

        // i → bus pointer
        // j → passenger pointer
        int i = 0, j = 0;

        // Simulate boarding bus by bus
        while (i < buses.length) {

            int passengerCount = 0;

            // Board passengers on this bus until:
            // - capacity is full OR
            // - passengers arrive after this bus departs
            while (j < passengers.length &&
                   passengerCount < capacity &&
                   passengers[j] <= buses[i]) {

                // If our arrival time is strictly before this passenger,
                // it means we can insert ourselves here and board successfully
                if (arrivalTime < passengers[j]) {
                    return true;
                }

                passengerCount++;
                j++;
            }

            // After filling current bus:
            // If there is still capacity AND we arrive before bus departs,
            // we can directly board this bus
            if (passengerCount < capacity && arrivalTime <= buses[i]) {
                return true;
            }

            // Move to next bus
            i++;
        }

        // If no bus can take us, arrivalTime is not feasible
        return false;
    }
}
