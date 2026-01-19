import java.util.Arrays;

class Solution {
    public int minimizeMax(int[] nums, int p) {

        // We need to form at least p pairs such that:
        //   the maximum difference among chosen pairs is minimized.

        // If only one element exists, no pair can be formed → answer is 0
        if (nums.length == 1) return 0;

        // Sort the array so that:
        // - Nearby numbers have smaller differences
        // - Greedy pairing becomes optimal
        // Reason:
        // Pairing closest elements minimizes local differences
        Arrays.sort(nums);

        // We binary search on the ANSWER (maximum allowed difference)

        // Minimum possible difference = 0
        int minDiff = 0;

        // Maximum possible difference = max element - min element
        // Reason: farthest possible pair in sorted array
        int maxDiff = nums[nums.length - 1] - nums[0];

        // This will store the smallest feasible maximum difference found
        int minimumMaxDiff = 0;

        // Binary search on the possible maximum difference
        while (minDiff <= maxDiff) {

            // Try a candidate maximum allowed difference
            int diff = minDiff + (maxDiff - minDiff) / 2;

            // Count how many disjoint pairs can be formed
            // such that pair difference <= diff
            if (getPairCount(nums, diff) >= p) {

                // If we can form at least p pairs with this diff,
                // then this diff is FEASIBLE
                minimumMaxDiff = diff;

                // Try to minimize it further
                // Reason: we want the smallest possible maximum difference
                maxDiff = diff - 1;
            }

            // If we cannot form p pairs,
            // then this diff is too small → we must allow larger differences
            else {
                minDiff = diff + 1;
            }
        }

        // minimumMaxDiff is the smallest diff that allows forming p pairs
        return minimumMaxDiff;
    }

    private int getPairCount(int[] nums, int maxDiff) {

        // We greedily form pairs from left to right
        // Reason:
        // Pairing closest available numbers always gives
        // the smallest possible differences and maximizes pair count

        int i = 0, j = 1;
        int count = 0;

        // Traverse the array forming non-overlapping pairs
        while (i < nums.length - 1 && j < nums.length) {

            // If current adjacent pair difference is within allowed limit
            if (nums[j] - nums[i] <= maxDiff) {

                // We form a pair using i and j
                count++;

                // Move both pointers by 2 to ensure pairs do NOT overlap
                // Reason: each element can be used in only one pair
                i += 2;
                j += 2;
            }

            // If difference is too large,
            // then i cannot pair with j → move both forward to try next neighbors
            else {
                i++;
                j++;
            }
        }

        // Return how many valid pairs we could form
        return count;
    }
}
