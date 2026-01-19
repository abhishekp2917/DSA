import java.util.Arrays;

class Solution {
    public int maxDistance(int[] position, int m) {

        // We want to place m balls in given positions such that
        // the MINIMUM distance between any two balls is MAXIMIZED.

        // Sort positions so that:
        // - Distances between adjacent placements are meaningful
        // - Greedy placement from left to right becomes valid
        // Reason:
        // Without sorting, distance comparisons are meaningless
        Arrays.sort(position);

        // We binary search on the ANSWER (minimum distance between balls)

        // Minimum possible distance = 0 (theoretically)
        int minDist = 0;

        // Maximum possible distance = farthest two positions
        // Reason:
        // This is the largest possible gap any two balls can have
        int maxDist = position[position.length - 1] - position[0];

        // This will store the largest feasible minimum distance found
        int maxForce = 0;

        // Binary search on distance
        while (minDist <= maxDist) {

            // Try a candidate minimum distance
            int force = minDist + (maxDist - minDist) / 2;

            // Check if we can place m balls such that
            // every adjacent pair is at least 'force' apart
            if (isPossibleToPlaceMBalls(position, m, force)) {

                // If possible, this force is feasible
                maxForce = force;

                // Try to increase the minimum distance further
                // Reason: we want the maximum possible minimum distance
                minDist = force + 1;
            }

            // If not possible, this force is too large
            // We must reduce the minimum distance
            else {
                maxDist = force - 1;
            }
        }

        // maxForce is the largest achievable minimum distance
        return maxForce;
    }

    private boolean isPossibleToPlaceMBalls(int[] position, int m, int force) {

        // We greedily place balls from left to right
        // Reason:
        // Placing as early as possible leaves maximum space
        // for future balls and maximizes feasibility

        // curr stores the index of the last placed ball
        int curr = 0;

        // First ball is always placed at position[0]
        // So we need to place remaining (m - 1) balls
        for (int i = 0; i < position.length; i++) {

            // If the distance from last placed ball is >= required force,
            // we can place a new ball here
            if (position[i] - position[curr] >= force) {

                // Place one ball here
                m--;

                // Update last placed position
                curr = i;
            }
        }

        // We successfully placed m balls if:
        // after placing, remaining balls needed <= 1
        //
        // Reason:
        // First ball is implicitly placed,
        // so condition m <= 1 means all required balls are placed
        return m <= 1;
    }
}
