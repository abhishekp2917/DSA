class Solution {

    public int pivotIndex(int[] nums) {

        int n = nums.length;

        // --------------------------------------------------
        // STEP 1: Build suffix sum array
        // --------------------------------------------------
        //
        // suffixSum[i] = sum of elements to the RIGHT of i
        //
        // Why i+1?
        // Because pivot excludes itself
        int[] suffixSum = new int[n];

        // Note:
        // suffixSum[n-1] = 0 by default (no elements to right)

        for (int i = n - 2; i >= 0; i--) {

            suffixSum[i] =
                suffixSum[i + 1] + nums[i + 1];
        }


        // --------------------------------------------------
        // STEP 2: Traverse and compute prefix sum on the fly
        // --------------------------------------------------
        //
        // sum = sum of elements to the LEFT of i
        int sum = 0;

        for (int i = 0; i < n; i++) {

            // --------------------------------------------------
            // Check pivot condition
            // --------------------------------------------------
            //
            // Left sum == Right sum
            if (sum == suffixSum[i])
                return i;

            // Update prefix sum AFTER checking
            //
            // Why after?
            // Because current index should not be included
            sum += nums[i];
        }

        return -1;
    }
}