import java.util.*;

class Solution {

    public long maximumSubarraySum(int[] nums, int k) {

        // Final answer
        long maxSum = Long.MIN_VALUE;

        // Map: value → minimum prefix sum seen BEFORE this value
        //
        // Why store minimum prefix?
        // Because:
        // subarray sum = prefix[j] - prefix[i]
        // To maximize this, we want smallest prefix[i]
        Map<Integer, Long> prefixMinSum = new HashMap<>();

        long prefixSum = 0;

        for (int num : nums) {

            // Update prefix sum
            prefixSum += num;

            // --------------------------------------------------
            // Key idea:
            //
            // We want subarray where:
            // |nums[j] - nums[i]| == k
            //
            // So possible previous values:
            // nums[i] = num - k
            // nums[i] = num + k
            // --------------------------------------------------

            int target1 = num - k;
            int target2 = num + k;

            // Get smallest prefix sum seen for those values
            long prefixSum1 =
                prefixMinSum.getOrDefault(
                    target1, Long.MAX_VALUE
                );

            long prefixSum2 =
                prefixMinSum.getOrDefault(
                    target2, Long.MAX_VALUE
                );


            // --------------------------------------------------
            // If valid previous value exists,
            // compute subarray sum
            //
            // Why +target?
            //
            // Because:
            // prefixSum stores sum INCLUDING current num
            //
            // But prefixMinSum was stored BEFORE that index,
            // so we need to adjust by adding that value back
            // --------------------------------------------------

            if (prefixSum1 != Long.MAX_VALUE) {

                maxSum = Math.max(
                    maxSum,
                    prefixSum - prefixSum1 + target1
                );
            }

            if (prefixSum2 != Long.MAX_VALUE) {

                maxSum = Math.max(
                    maxSum,
                    prefixSum - prefixSum2 + target2
                );
            }


            // --------------------------------------------------
            // Update map
            //
            // Store minimum prefix sum for this value
            //
            // Why minimum?
            // To maximize future subarray sums
            // --------------------------------------------------
            prefixMinSum.put(
                num,
                Math.min(
                    prefixSum,
                    prefixMinSum.getOrDefault(num, Long.MAX_VALUE)
                )
            );
        }

        // If no valid subarray found
        return (maxSum != Long.MIN_VALUE) ? maxSum : 0;
    }
}