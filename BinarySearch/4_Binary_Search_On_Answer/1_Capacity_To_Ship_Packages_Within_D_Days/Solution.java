class Solution {

    public int shipWithinDays(int[] weights, int days) {

        // We need to ship all packages within exactly 'days' days.
        // Each day, the ship has a fixed capacity.
        //
        // Goal:
        //     MINIMIZE the ship capacity such that all packages
        //     can be shipped within 'days'.

        // We are NOT choosing the schedule directly.
        // We are searching the ANSWER: minimum possible ship capacity.

        // Get:
        //   - minimum possible capacity (max single weight)
        //   - maximum possible capacity (sum of all weights)
        int[] temp = totalWeights(weights);

        int i = temp[0];   // lower bound = heaviest package
        int j = temp[1];   // upper bound = total sum of all packages

        // This will store the smallest feasible capacity found
        int estCapacity = -1;

        // Binary search on the ANSWER (ship capacity)
        while (i <= j) {

            // Try a candidate capacity
            int capacity = (i + j) / 2;

            // Calculate how many days are REQUIRED
            // if ship capacity is 'capacity'
            int estDays = calcDays(weights, capacity);

            // If more days are needed than allowed,
            // then this capacity is TOO SMALL
            // We must increase capacity
            if (estDays > days) {
                i = capacity + 1;
            }

            // If we can ship within 'days' or fewer days,
            // then this capacity is FEASIBLE
            else {

                // Store this as a possible answer
                estCapacity = capacity;

                // Try to minimize capacity further
                // Reason: we want the smallest possible valid capacity
                j = capacity - 1;
            }
        }

        // estCapacity is the minimum ship capacity required
        return estCapacity;
    }

    public int[] totalWeights(int[] weights) {

        // This function computes:
        //   - maximum single weight
        //   - total sum of all weights
        //
        // These define the binary search boundaries.

        int total = 0;
        int max = 0;

        for (int weight : weights) {

            // Lower bound:
            // Ship must at least carry the heaviest package
            max = Math.max(max, weight);

            // Upper bound:
            // Ship can carry all packages in one day
            total += weight;
        }

        return new int[]{max, total};
    }

    public int calcDays(int[] weights, int capacity) {

        // This function calculates:
        // How many days are REQUIRED to ship all packages
        // if ship capacity per day is 'capacity'.

        int days = 0;              // number of days used
        int totalWeights = 0;     // current day's loaded weight

        for (int i = 0; i < weights.length; i++) {

            // If adding this package exceeds today's capacity,
            // we must start a NEW day
            if ((totalWeights + weights[i]) > capacity) {

                // One full day completed
                days++;

                // Reset current day's load
                totalWeights = 0;
            }

            // Load current package into current day
            totalWeights += weights[i];
        }

        // Add the last day (which is not counted inside the loop)
        return ++days;
    }
}
