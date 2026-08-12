import java.util.Arrays;

class Solution {
    public int findRadius(int[] houses, int[] heaters) {

        // We need the MINIMUM radius such that every house
        // is covered by at least one heater.

        // Sorting both arrays helps us use two pointers efficiently
        // and check coverage in linear time inside feasibility check
        Arrays.sort(houses);
        Arrays.sort(heaters);

        // We are not searching in the array —
        // we are searching in the RANGE of possible radius values.

        int i = 0;

        // Maximum possible radius can be very large,
        // so we take upper bound as Integer.MAX_VALUE
        int j = Integer.MAX_VALUE;

        // This will store the minimum valid radius found so far
        int ans = 0;

        // Binary search on the answer (radius)
        while (i <= j) {

            // Try a middle radius as candidate
            int mid = (i + j) / 2;

            // Check if this radius can cover all houses
            if (isPoss(houses, heaters, mid)) {

                // If possible, this radius works
                // But we try to minimize it further
                ans = mid;
                j = mid - 1;
            }

            // If not possible, radius is too small
            // We must increase the radius
            else {
                i = mid + 1;
            }
        }

        // ans holds the minimum radius that covers all houses
        return ans;
    }

    // This function checks:
    // Can all houses be covered using heaters with given 'range' radius?
    private boolean isPoss(int[] houses, int[] heaters, int range) {

        // Pointer i → current house
        // Pointer j → current heater
        int i = 0, j = 0;

        // Try to match each house with some heater
        while (i < houses.length && j < heaters.length) {

            // Move heater pointer forward until we find a heater
            // that can cover the current house within 'range'
            //
            // Condition for NOT covered:
            //      house < heater - range   OR   house > heater + range
            //
            // Meaning: house lies completely outside this heater's coverage
            while (j < heaters.length &&
                  (houses[i] < (heaters[j] - range) ||
                   houses[i] > (heaters[j] + range))) {

                // This heater cannot cover this house → try next heater
                j++;
            }

            // If we ran out of heaters, coverage is impossible
            if (j == heaters.length) break;

            // If current heater covers this house,
            // move to the next house
            i++;
        }

        // If all houses are covered, i should reach houses.length
        return i >= houses.length;
    }
}
