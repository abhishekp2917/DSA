class Solution {

    public int minEatingSpeed(int[] piles, int h) {

        // We need to find the MINIMUM eating speed (bananas/hour)
        // such that Koko can finish all piles within 'h' hours.

        // We are NOT simulating every possible speed linearly.
        // We are searching the ANSWER using Binary Search on speed.

        // Minimum possible speed = 1 banana/hour
        // Reason:
        // Koko must eat at least 1 banana per hour
        int minHr = 1;

        // Maximum possible speed = maximum pile size
        // Here upper bound is taken large enough by constraints
        int maxHr = 1_000_000_000;

        // This will store the smallest feasible eating speed
        int minNoOfBananaPerHour = 0;

        // Binary search on the ANSWER (eating speed)
        while (minHr <= maxHr) {

            // Try a candidate speed
            int noOfBananaPerHour = minHr + (maxHr - minHr) / 2;

            // Calculate how many hours are REQUIRED
            // if Koko eats at this speed
            long requiredHours = calcHours(piles, noOfBananaPerHour);

            // If more hours are required than allowed,
            // then this speed is TOO SLOW
            // We must increase eating speed
            if (requiredHours > h) {

                minHr = noOfBananaPerHour + 1;
            }

            // If Koko can finish within h hours or less,
            // then this speed is FEASIBLE
            else {

                // Store as possible answer
                minNoOfBananaPerHour = noOfBananaPerHour;

                // Try to minimize the speed further
                // Reason:
                // We want the smallest possible eating speed
                maxHr = noOfBananaPerHour - 1;
            }
        }

        // minNoOfBananaPerHour is the minimum eating speed required
        return minNoOfBananaPerHour;
    }

    private long calcHours(int[] piles, int noOfBananaPerHour) {

        // This function computes:
        // How many hours are REQUIRED to finish all piles
        // if Koko eats at speed 'noOfBananaPerHour'.

        long hour = 0;

        for (int i = 0; i < piles.length; i++) {

            // For each pile:
            // Time needed = ceil(piles[i] / speed)

            // Integer division gives floor(piles[i] / speed)
            hour += piles[i] / noOfBananaPerHour;

            // If remainder exists, one extra hour is needed
            if (piles[i] % noOfBananaPerHour != 0) {
                hour++;
            }
        }

        // Return total hours required
        return hour;
    }
}
