import java.util.Arrays;

class Solution {
    public int numSubseq(int[] nums, int target) {

        int n = nums.length;

        final int MOD = 1000000007;

        int count = 0;

        int left = 0, right = n-1;

        // Precompute powers of 2:
        // powerOfTwo[i] = 2^i % MOD
        //
        // This is needed because for each valid pair (left, right),
        // we can freely choose any subset of elements between them.
        int[] powerOfTwo = getPowerOfTwo(n, MOD);

        // Sorting is required so that:
        // - left pointer gives minimum element
        // - right pointer gives maximum element
        // - monotonic movement works correctly
        Arrays.sort(nums);

        while(left<=right) {

            int sum = nums[left] + nums[right];

            if(sum>target) {
                // If min + max > target,
                // we must reduce the maximum.
                right--;
            }
            else {
                // If nums[left] + nums[right] <= target,
                // then for this fixed left,
                // ALL subsequences formed by:
                //    nums[left] as minimum
                //    and any subset of elements between left and right
                // will also satisfy condition.
                //
                // Number of such subsequences:
                // 2^(right - left)
                int len = (right-left);

                int subseqStartFromLeftToRight = powerOfTwo[len];

                count = (count + subseqStartFromLeftToRight)%MOD;

                // Move left to consider next minimum.
                left++;
            }
        }

        return count;
    }

    private int[] getPowerOfTwo(int n, int MOD) {

        int[] powerOfTwo = new int[n];

        // 2^0 = 1
        powerOfTwo[0] = 1;

        for(int i=1; i<n; i++) {

            // Build powers iteratively:
            // 2^i = 2^(i-1) * 2
            powerOfTwo[i] = (powerOfTwo[i-1]*2)%MOD;
        }

        return powerOfTwo;
    }
}
