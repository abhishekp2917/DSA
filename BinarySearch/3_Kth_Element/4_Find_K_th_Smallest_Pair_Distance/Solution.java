import java.util.Arrays;

class Solution {
    public int smallestDistancePair(int[] nums, int k) {

        // We need to find the k-th SMALLEST absolute difference
        // among all possible pairs (i < j).

        // Brute force generating all pairs is O(n^2) → too slow.

        // We binary search on the ANSWER (distance).

        // Sort the array so that:
        // - Pair differences become monotonic
        // - Two-pointer counting becomes possible
        Arrays.sort(nums);

        // Minimum possible distance = 0
        // Reason:
        // Two equal numbers give difference 0
        int minDist = 0;

        // Maximum possible distance = max(nums) - min(nums)
        // Here upper bound chosen large enough (by constraints)
        int maxDist = 1000000;

        // This will store the smallest distance
        // such that at least k pairs have distance <= it
        int kthDist = -1;

        // Binary search on distance
        while (minDist <= maxDist) {

            // Try a candidate distance
            int dist = minDist + (maxDist - minDist) / 2;

            // Check if there are AT LEAST k pairs
            // whose distance is <= dist
            if (hasAtleastKPairLessOrEqual(nums, k, dist)) {

                // If yes, this distance is FEASIBLE
                kthDist = dist;

                // Try to find an even smaller feasible distance
                // Reason: we want the smallest possible such distance
                maxDist = dist - 1;
            }

            // If not enough pairs exist,
            // this distance is too small
            // We must allow larger distance
            else {
                minDist = dist + 1;
            }
        }

        // kthDist is the k-th smallest pair distance
        return kthDist;
    }

    private boolean hasAtleastKPairLessOrEqual(int[] nums,
                                               int k,
                                               int distance) {

        // This function counts how many pairs (i < j)
        // have nums[j] - nums[i] <= distance.

        int count = 0;

        // Two pointers technique:
        // j moves forward as right pointer
        // i moves forward to maintain valid window
        int i = 0, j = 1;

        while (j < nums.length) {

            // Move left pointer i until the pair difference becomes valid
            //
            // Reason:
            // Since array is sorted,
            // increasing i decreases the difference nums[j] - nums[i]
            while (i <= j && nums[j] - nums[i] > distance) {
                i++;
            }

            // Now for this fixed j:
            // All indices from i to j-1 form valid pairs with j
            //
            // Number of valid pairs ending at j = (j - i)
            count += j - i;

            // Move right pointer forward
            j++;
        }

        // Check if we have at least k valid pairs
        return count >= k;
    }
}
