class Solution {
    public int minDays(int[] bloomDay, int m, int k) {

        // We need to make exactly m bouquets.
        // Each bouquet needs k ADJACENT flowers.
        // Flower i blooms on bloomDay[i].
        //
        // Goal:
        //     Find the MINIMUM day such that we can make m bouquets.

        // We are NOT simulating each day one by one.
        // We are searching the ANSWER: minimum possible day.

        // Binary search range:

        // Minimum possible day = 1
        // Reason:
        // Days start from 1
        int i = 1;

        // Maximum possible day = maximum bloom day in the array
        // Reason:
        // Before this day, some flowers are not even bloomed
        int j = 1;
        for (int n : bloomDay) {
            j = Math.max(j, n);
        }

        // This will store the smallest feasible day
        int ans = -1;

        // Binary search on the ANSWER (day)
        while (i <= j) {

            // Try a candidate day
            int mid = (i + j) / 2;

            // Check if we can make m bouquets by day = mid
            if (isPoss(bloomDay, m, k, mid)) {

                // If possible, this day is FEASIBLE
                ans = mid;

                // Try to find an earlier possible day
                // Reason:
                // We want the minimum day
                j = mid - 1;
            }

            // If not possible, this day is TOO EARLY
            // We must wait for more days
            else {
                i = mid + 1;
            }
        }

        // ans is the minimum day on which we can form all bouquets
        return ans;
    }

    private boolean isPoss(int[] bloomDay, int m, int k, int day) {

        // This function checks:
        // Can we form m bouquets by 'day',
        // each bouquet consisting of k ADJACENT bloomed flowers?

        int adjCount = 0;   // count of consecutive bloomed flowers

        for (int i = 0; i < bloomDay.length; i++) {

            // If this flower has bloomed by 'day'
            if (bloomDay[i] <= day) {

                // Extend current adjacent sequence
                adjCount++;
            }

            // If this flower has NOT bloomed yet,
            // adjacency breaks
            else {
                adjCount = 0;
            }

            // If we have collected k adjacent bloomed flowers,
            // we can form ONE bouquet
            if (adjCount == k) {

                // One bouquet is formed
                m--;

                // Reset adjacency count for next bouquet
                // Reason:
                // A flower can be used in only one bouquet
                adjCount = 0;
            }

            // If we already formed all required bouquets,
            // we can stop early
            if (m == 0) break;
        }

        // Return whether all m bouquets were successfully formed
        return m == 0;
    }
}
