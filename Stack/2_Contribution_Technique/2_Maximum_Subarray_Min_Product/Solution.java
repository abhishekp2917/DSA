import java.util.*;

class Solution {

    public int maxSumMinProduct(int[] nums) {

        int n = nums.length;

        final int MOD = 1000_000_007;

        long maxSumProd = 0;

        // --------------------------------------------------
        // STEP 1: Prefix sum for fast range sum queries
        // --------------------------------------------------
        //
        // prefixSum[i] = sum of nums[0...i-1]
        //
        // Why?
        // To compute subarray sum in O(1)
        long[] prefixSum = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            prefixSum[i] =
                prefixSum[i - 1] + nums[i - 1];
        }


        // --------------------------------------------------
        // STEP 2: Find nearest smaller to RIGHT
        // --------------------------------------------------
        int[] nearestSmallerRight = new int[n];

        Stack<Integer> minStack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {

            // Maintain increasing stack
            //
            // Pop all >= current
            //
            // Why >= ?
            // Ensures correct handling of duplicates
            while (!minStack.isEmpty() &&
                   nums[minStack.peek()] >= nums[i]) {

                minStack.pop();
            }

            nearestSmallerRight[i] =
                (!minStack.isEmpty()) ? minStack.peek() : n;

            minStack.push(i);
        }


        // Clear stack
        minStack.clear();


        // --------------------------------------------------
        // STEP 3: Find nearest smaller to LEFT + compute answer
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {

            // Maintain increasing stack
            while (!minStack.isEmpty() &&
                   nums[minStack.peek()] >= nums[i]) {

                minStack.pop();
            }

            int nearestSmallerLeft =
                (!minStack.isEmpty()) ? minStack.peek() : -1;


            // --------------------------------------------------
            // STEP 4: Compute range where nums[i] is MIN
            // --------------------------------------------------
            //
            // Range:
            // (nearestSmallerLeft + 1) → (nearestSmallerRight - 1)
            int leftIdx = nearestSmallerLeft + 1;
            int rightIdx = nearestSmallerRight[i];


            // --------------------------------------------------
            // STEP 5: Compute sum of this range
            // --------------------------------------------------
            //
            // prefixSum[right] - prefixSum[left]
            long sum =
                prefixSum[rightIdx] - prefixSum[leftIdx];


            // --------------------------------------------------
            // STEP 6: Compute min-product
            // --------------------------------------------------
            //
            // nums[i] is minimum in this range
            maxSumProd = Math.max(
                maxSumProd,
                sum * nums[i]
            );

            minStack.push(i);
        }

        return (int) (maxSumProd % MOD);
    }
}