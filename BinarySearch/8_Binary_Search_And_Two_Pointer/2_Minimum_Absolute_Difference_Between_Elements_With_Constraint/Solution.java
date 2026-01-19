import java.util.List;
import java.util.TreeSet;

class Solution {
    public int minAbsoluteDifference(List<Integer> nums, int x) {

        // We need to find:
        //   min |nums[i] - nums[j]| such that |i - j| >= x
        //
        // So when we are at index j, only indices i <= j - x are allowed.
        // We maintain a set of valid previous elements to compare against.

        // TreeSet keeps elements in sorted order
        // and allows us to find nearest smaller and larger values efficiently
        TreeSet<Integer> set = new TreeSet<>();

        // i → left pointer (elements entering the valid window)
        // j → right pointer (current index)
        int i = 0, j = 0;

        // Store the minimum absolute difference found so far
        int ans = Integer.MAX_VALUE;

        while (j < nums.size()) {

            // We only start comparing when the index gap condition is satisfied
            // i.e., j - i >= x ensures |i - j| >= x
            if (j - i >= x) {

                // Add nums[i] into the TreeSet because it now becomes a valid candidate
                // for comparison with current nums[j]
                set.add(nums.get(i));

                // Find the closest smaller or equal value to nums[j]
                Integer floor = set.floor(nums.get(j));

                // Find the closest greater or equal value to nums[j]
                Integer ceil = set.ceiling(nums.get(j));

                // If no floor exists, treat it as very large difference
                if (floor == null) floor = Integer.MAX_VALUE;

                // If no ceil exists, treat it as very large difference
                if (ceil == null) ceil = Integer.MAX_VALUE;

                // Update answer with the minimum possible difference
                // from nearest left or nearest right neighbor
                ans = Math.min(ans,
                               Math.min(Math.abs(nums.get(j) - floor),
                                        Math.abs(ceil - nums.get(j))));

                // Move left pointer forward to maintain the sliding window
                i++;
            }

            // Always move right pointer forward
            j++;
        }

        // ans stores the minimum absolute difference satisfying the index constraint
        return ans;
    }
}
