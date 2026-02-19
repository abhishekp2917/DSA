import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution1 {
    public List<List<Integer>> threeSum(int[] nums) {

        // We need to find all unique triplets such that:
        // nums[i] + nums[j] + nums[k] = 0
        // Brute force would be O(n^3), which is too slow.

        List<List<Integer>> threeSums = new ArrayList<>();
        int n = nums.length;

        // Sorting is CRUCIAL here.
        // Reason:
        // 1) Allows two pointer approach.
        // 2) Makes duplicate handling easy.
        Arrays.sort(nums);

        // Fix the first element of the triplet.
        // Then solve 2-sum for remaining array.
        for(int i = 0; i < n - 2; i++) {

            // Skip duplicate values for the first element.
            // If we don't skip, we'll generate duplicate triplets.
            if(i > 0 && nums[i] == nums[i - 1]) continue;

            // Now we find two numbers after index i
            // such that nums[left] + nums[right] = -nums[i]
            int left = i + 1;
            int right = n - 1;

            while(left < right) {

                int sum = nums[i] + nums[left] + nums[right];

                // If sum is too large,
                // we need a smaller number → move right pointer left.
                if(sum > 0) {
                    right--;
                }

                // If sum is too small,
                // we need a larger number → move left pointer right.
                else if(sum < 0) {
                    left++;
                }

                else {
                    // Found a valid triplet.
                    threeSums.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Skip duplicates for left pointer
                    // so that we don't add same triplet again.
                    while(left < right && nums[left] == nums[left + 1]) left++;

                    // Skip duplicates for right pointer
                    while(left < right && nums[right] == nums[right - 1]) right--;

                    // Move both pointers inward to search new pairs.
                    left++;
                    right--;
                }
            }
        }

        return threeSums;
    }
}

class Solution2 {
    public List<List<Integer>> threeSum(int[] nums) {

        // Sorting is required because:
        // 1) It allows two-pointer optimization for k = 2 case.
        // 2) It helps in skipping duplicates easily.
        Arrays.sort(nums);

        // threeSum is just a special case of kSum where:
        // k = 3 and target = 0
        return kSum(nums, 0, 3, 0);
    }

    private List<List<Integer>> kSum(int[] nums, int start, int k, long target) {

        int n = nums.length;
        List<List<Integer>> result = new ArrayList<>();

        // Base Case:
        // When k == 2, the problem reduces to 2-sum in sorted array.
        if (k == 2) {

            int i = start;
            int j = n - 1;

            while (i < j) {

                long sum = nums[i] + nums[j];

                // If sum is too large, decrease it by moving right pointer.
                if (sum > target) {
                    j--;
                }

                // If sum is too small, increase it by moving left pointer.
                else if (sum < target) {
                    i++;
                }

                else {
                    // Found a valid pair.
                    result.add(Arrays.asList(nums[i], nums[j]));

                    // Skip duplicates on left
                    while (i < j && nums[i] == nums[i + 1]) i++;

                    // Skip duplicates on right
                    while (i < j && nums[j] == nums[j - 1]) j--;

                    // Move both pointers inward
                    i++;
                    j--;
                }
            }

            return result;
        }

        // Recursive Case:
        // Fix one element and recursively solve (k-1) sum.
        for (int i = start; i <= n - k; i++) {

            // Skip duplicates for the fixed element.
            // Prevents duplicate combinations.
            if (i > start && nums[i] == nums[i - 1]) continue;

            // Recursively find (k-1) numbers that sum to:
            // target - nums[i]
            List<List<Integer>> subResult =
                    kSum(nums, i + 1, k - 1, target - nums[i]);

            // For every combination found in subResult,
            // prepend nums[i] to build full k-sized combination.
            for (List<Integer> list : subResult) {

                List<Integer> current = new ArrayList<>();
                current.add(nums[i]);     // fixed element
                current.addAll(list);     // remaining elements

                result.add(current);
            }
        }

        return result;
    }
}
