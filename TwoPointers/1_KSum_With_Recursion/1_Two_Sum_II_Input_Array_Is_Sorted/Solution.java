class Solution {
    public int[] twoSum(int[] nums, int target) {
        
        // Since array is SORTED in non-decreasing order,
        // we can use the Two Pointer technique instead of HashMap.
        // This allows O(n) time and O(1) space.

        int n = nums.length;

        // Left pointer starts from smallest element
        int i = 0;

        // Right pointer starts from largest element
        int j = n - 1;

        // Result array (problem states answer is 1-indexed)
        int[] ans = new int[2];

        // Core invariant:
        // All pairs left of i and right of j are already eliminated.
        while (i < j) {

            // Current pair sum
            int sum = nums[i] + nums[j];

            // Case 1: sum is too small
            if (sum < target) {

                // Since array is sorted:
                // nums[i] is too small.
                // Moving j left would only DECREASE sum further.
                // So the only way to increase sum is to move i right.
                i++;
            }

            // Case 2: sum is too large
            else if (sum > target) {

                // nums[j] is too large.
                // Moving i right would increase sum further.
                // So the only way to decrease sum is to move j left.
                j--;
            }

            // Case 3: sum equals target
            else {

                // Problem requires 1-based indexing
                ans[0] = i + 1;
                ans[1] = j + 1;

                // We break because problem guarantees exactly one solution
                break;
            }
        }

        return ans;
    }
}
