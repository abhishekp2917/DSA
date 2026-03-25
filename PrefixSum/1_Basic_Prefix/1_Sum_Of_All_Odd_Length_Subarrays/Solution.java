class Solution {

    public int sumOddLengthSubarrays(int[] nums) {

        int n = nums.length;

        int totalSum = 0;

        // --------------------------------------------------
        // STEP 1: Build prefix sum
        // --------------------------------------------------
        //
        // prefixSum[i] = sum of elements from 0 → i-1
        //
        // Why?
        // To compute subarray sum in O(1)
        //
        // subarray sum (start → end) =
        // prefixSum[end+1] - prefixSum[start]
        int[] prefixSum = new int[n + 1];

        for (int i = 0; i < n; i++) {

            prefixSum[i + 1] =
                prefixSum[i] + nums[i];
        }


        // --------------------------------------------------
        // STEP 2: Iterate over all odd lengths
        // --------------------------------------------------
        //
        // Only odd length subarrays are allowed:
        // len = 1, 3, 5, ...
        for (int len = 1; len <= n; len += 2) {

            // --------------------------------------------------
            // STEP 3: Try all starting points
            // --------------------------------------------------
            //
            // Ensure subarray fits within array
            for (int start = 0;
                 start + len <= n;
                 start++) {

                int end = start + len - 1;

                // Compute subarray sum using prefix sum
                totalSum +=
                    prefixSum[end + 1] - prefixSum[start];
            }
        }

        return totalSum;
    }
}