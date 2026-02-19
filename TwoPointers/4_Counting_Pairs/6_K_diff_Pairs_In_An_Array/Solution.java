import java.util.Arrays;

class Solution {
    public int findPairs(int[] nums, int k) {    

        int n = nums.length;    

        int left = 0, right = 0;

        int count = 0;

        // Sorting is required because:
        // 1) It allows two pointer traversal.
        // 2) It helps detect duplicates easily.
        Arrays.sort(nums);

        while(left<n && right<n) {

            // Case 1:
            // If both pointers are same,
            // or difference is smaller than k,
            // we need to increase difference → move right.
            if(left==right || nums[right]-nums[left]<k) {
                right++;
            } 

            // Case 2:
            // If difference is larger than k,
            // we need to reduce difference → move left.
            else if(nums[right]-nums[left]>k) {
                left++;
            } 

            // Case 3:
            // Found valid pair where:
            // nums[right] - nums[left] == k
            else {

                count++;

                // Move left to look for next unique pair.
                left++;

                // Skip duplicates to avoid counting
                // same pair multiple times.
                while(left<n && nums[left]==nums[left-1]) {
                    left++;
                }
            }
        }

        return count;
    }
}
