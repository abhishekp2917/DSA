import java.util.Arrays;

class Solution {
    public int maximumCandies(int[] candies, long k) {

        // We want to distribute candies to k children such that:
        // - Each child gets the SAME number of candies
        // - That number is MAXIMIZED

        // We are NOT choosing exact distribution.
        // We are searching the ANSWER: maximum candies per child.

        // Binary search on the possible candies per child.

        // Minimum possible candies per child = 0
        // Reason:
        // If distribution is impossible, answer can be 0
        int i = 0;

        // Maximum possible candies per child = maximum pile size
        // Reason:
        // One child can take at most all candies from the largest pile
        int j = Arrays.stream(candies).max().getAsInt();

        // This will store the largest feasible candies per child
        int ans = 0;

        // Binary search on the ANSWER (candies per child)
        while (i <= j) {

            // Try a candidate value: candies per child
            int mid = (i + j) / 2;

            // Check if it is possible to give each of k children
            // exactly 'mid' candies
            if (isPoss(candies, k, mid)) {

                // If possible, this value is FEASIBLE
                ans = mid;

                // Try to give more candies per child
                // Reason:
                // We want the MAXIMUM possible value
                i = mid + 1;
            }

            // If not possible, this value is too large
            // We must reduce candies per child
            else {
                j = mid - 1;
            }
        }

        // ans is the maximum candies each child can get
        return ans;
    }

    public boolean isPoss(int[] candies, long k, int maxCandy) {

        // This function checks:
        // Can we give each child exactly 'maxCandy' candies
        // using the given piles?

        // Special case:
        // If maxCandy = 0, we can always distribute 0 candies to everyone
        if (maxCandy == 0) return true;

        long candyCount = 0;

        for (int candy : candies) {

            // From each pile, we can form:
            //     floor(candy / maxCandy) children portions
            //
            // Reason:
            // Each child needs exactly maxCandy candies
            candyCount += candy / maxCandy;
        }

        // Check if we can serve at least k children
        return candyCount >= k;
    }
}
