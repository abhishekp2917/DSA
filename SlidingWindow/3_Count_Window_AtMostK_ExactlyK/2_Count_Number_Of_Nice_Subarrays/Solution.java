class Solution {

    public int numberOfSubarrays(int[] nums, int k) {

        // We need to count subarrays with EXACTLY k odd numbers.
        //
        // Instead of directly counting "exactly k",
        // we use the standard trick:
        //
        //     exactly(k) = atMost(k) - atMost(k-1)
        //
        // Reason:
        //   All subarrays with <= k odds
        //   minus
        //   all subarrays with <= (k-1) odds
        //   leaves only subarrays with exactly k odds.

        return countAtmostK(nums, k) - countAtmostK(nums, k - 1);
    }

    public int countAtmostK(int[] nums, int k) {

        // This function counts subarrays
        // having AT MOST k odd numbers.

        int oddNumCount = 0;  // number of odd numbers in current window
        int count = 0;       // total valid subarrays
        int start = 0;       // left pointer

        for (int end = 0; end < nums.length; end++) {

            // Expand window by including nums[end]
            if (nums[end] % 2 != 0) {
                oddNumCount++;
            }

            // If number of odd numbers exceeds k,
            // shrink window from the left
            //
            // Reason:
            // Window must maintain condition:
            //     oddNumCount <= k
            while (start <= end && oddNumCount > k) {

                if (nums[start] % 2 != 0) {
                    oddNumCount--;
                }

                start++;
            }

            // Now window [start ... end] is valid
            // (contains at most k odd numbers)
            //
            // All subarrays ending at 'end'
            // and starting from any index between start and end
            // are valid.
            //
            // Number of such subarrays = (end - start + 1)
            count += (end - start + 1);
        }

        return count;
    }
}
