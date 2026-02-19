import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution1 {

    public List<List<Integer>> fourSum(int[] nums, int target) {

        int n = nums.length;
        List<List<Integer>> fourSums = new ArrayList<>();

        // Sorting is mandatory because:
        // 1) It enables two pointer search.
        // 2) It allows easy duplicate skipping.
        // 3) It guarantees monotonic movement of pointers.
        Arrays.sort(nums);

        // First element of quadruplet
        for (int i = 0; i < n - 3; i++) {

            // Skip duplicates for first index
            // Prevents duplicate quadruplets.
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // Second element of quadruplet
            for (int j = i + 1; j < n - 2; j++) {

                // Skip duplicates for second index
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                // Now problem reduces to 2Sum:
                // Find two numbers after j
                // such that total sum equals target.
                int left = j + 1;
                int right = n - 1;

                while (left < right) {

                    // Use long to prevent integer overflow.
                    long sum = (long) nums[i]
                             + (long) nums[j]
                             + (long) nums[left]
                             + (long) nums[right];

                    if (sum == target) {

                        // Found valid quadruplet
                        fourSums.add(Arrays.asList(
                                nums[i], nums[j], nums[left], nums[right]));

                        // Skip duplicates for left
                        while (left < right && nums[left] == nums[left + 1])
                            left++;

                        // Skip duplicates for right
                        while (left < right && nums[right] == nums[right - 1])
                            right--;

                        // Move inward to search for new combinations
                        left++;
                        right--;
                    }

                    // If sum is too small,
                    // we need a larger value → move left pointer.
                    else if (sum < target) {
                        left++;
                    }

                    // If sum is too large,
                    // we need a smaller value → move right pointer.
                    else {
                        right--;
                    }
                }
            }
        }

        return fourSums;
    }
}


class Solution2 {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, 0, 4, target);
    }

    private List<List<Integer>> kSum(int[] nums, int start, int k, long target) {
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        if(k==2) {
            int i=start, j=n-1;
            while(i<j) {
                long sum = nums[i] + nums[j];
                if(sum>target) j--;
                else if(sum<target) i++;
                else {
                    result.add(Arrays.asList(nums[i], nums[j]));
                    while(i<j && nums[i]==nums[i+1]) i++;
                    while(i<j && nums[j]==nums[j-1]) j--;
                    i++;
                    j--;
                }
            }
            return result;
        }
        for(int i=start; i<=n-k; i++) {
            if(i>start && nums[i]==nums[i-1]) continue;
            List<List<Integer>> subResult = kSum(nums, i+1, k-1, target-nums[i]);
            for(List<Integer> list : subResult) {
                List<Integer> current = new ArrayList<>();
                current.add(nums[i]);
                current.addAll(list);
                result.add(current);
            }
        }
        return result;
    }
}


      