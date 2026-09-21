import java.util.*;

class Solution {

    public int sumSubarrayMins(int[] nums) {

        int n = nums.length;

        final int MOD = 1000_000_007;

        long subarrSum = 0;

        // --------------------------------------------------
        // IDEA:
        // Instead of iterating all subarrays,
        // count contribution of each element as MIN
        // --------------------------------------------------

        int[] nearestSmallerRight = new int[n];

        Stack<Integer> minStack = new Stack<>();


        // --------------------------------------------------
        // STEP 1: Find nearest smaller element to RIGHT
        // --------------------------------------------------
        //
        // Why RIGHT first?
        // Because we want full range for each element
        for (int i = n - 1; i >= 0; i--) {

            // Maintain increasing stack
            //
            // Pop all elements >= current
            //
            // Why >= ?
            // To handle duplicates correctly
            while (!minStack.isEmpty() &&
                   nums[minStack.peek()] >= nums[i]) {

                minStack.pop();
            }

            nearestSmallerRight[i] =
                (!minStack.isEmpty()) ? minStack.peek() : n;

            minStack.push(i);
        }


        // Clear stack for reuse
        minStack.clear();


        // --------------------------------------------------
        // STEP 2: Traverse from LEFT → RIGHT
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {

            // Maintain increasing stack
            //
            // Pop all strictly greater elements
            //
            // Why only > ?
            // To avoid double counting duplicates
            while (!minStack.isEmpty() &&
                   nums[minStack.peek()] > nums[i]) {

                minStack.pop();
            }

            int nearestSmallerLeft =
                (!minStack.isEmpty()) ? minStack.peek() : -1;


            // --------------------------------------------------
            // STEP 3: Compute contribution
            // --------------------------------------------------

            // Number of choices on left
            int leftMinCount = i - nearestSmallerLeft;

            // Number of choices on right
            int rightMinCount = nearestSmallerRight[i] - i;

            // Total subarrays where nums[i] is MIN
            long minContribution =
                (long) leftMinCount * rightMinCount;


            // Add contribution
            subarrSum =
                (subarrSum +
                 (minContribution * nums[i]) % MOD) % MOD;


            // Push current index
            minStack.push(i);
        }

        return (int) subarrSum;
    }
}