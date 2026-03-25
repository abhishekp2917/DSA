import java.util.*;

class Solution {

    public int[] answerQueries(int[] nums, int[] queries) {

        int n = nums.length;

        int[] ans = new int[queries.length];

        // --------------------------------------------------
        // STEP 1: Sort the array
        // --------------------------------------------------
        //
        // Why sort?
        //
        // We want to pick MAXIMUM number of elements
        // such that their sum ≤ query.
        //
        // Greedy idea:
        // Always pick smallest elements first.
        //
        // Because:
        // Smaller elements → more count within same sum.
        Arrays.sort(nums);


        // --------------------------------------------------
        // STEP 2: Build prefix sum
        // --------------------------------------------------
        //
        // prefixSum[i] = sum of first i elements
        //
        // Why prefix sum?
        //
        // Because:
        // Instead of recomputing sum for each query,
        // we can directly check:
        //
        // "How many smallest elements can we take?"
        //
        int[] prefixSum = new int[n + 1];

        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = nums[i] + prefixSum[i];
        }


        // --------------------------------------------------
        // STEP 3: Answer each query using binary search
        // --------------------------------------------------
        //
        // For each query:
        // Find largest index i such that:
        //
        // prefixSum[i] ≤ query
        //
        // That i = number of elements we can pick
        //
        for (int i = 0; i < queries.length; i++) {

            ans[i] = findLowerBound(prefixSum, queries[i]);
        }

        return ans;
    }


    // --------------------------------------------------
    // Binary Search: Find largest index such that
    // prefixSum[index] ≤ target
    // --------------------------------------------------
    //
    // This is NOT classical lower bound.
    //
    // This is:
    // "Rightmost value ≤ target"
    //
    public int findLowerBound(int[] prefixSum, int target) {

        int start = 0;
        int end = prefixSum.length - 1;

        int lowerBound = -1;

        while (start <= end) {

            int mid = (start + end) / 2;

            // If prefix sum exceeds target,
            // we need smaller prefix → go left
            if (prefixSum[mid] > target) {

                end = mid - 1;
            }
            else {

                // prefixSum[mid] ≤ target
                //
                // This is a valid candidate
                lowerBound = mid;

                // Try to find larger valid index
                // to maximize count
                start = mid + 1;
            }
        }

        return lowerBound;
    }
}