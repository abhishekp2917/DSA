import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // Result list to store all unique subsets
        List<List<Integer>> subsets = new ArrayList<>();

        // Sorting helps bring duplicate elements together
        // so we can easily skip them during recursion
        Arrays.sort(nums);

        // Start recursive exploration from index 0
        recurrsion(nums, 0, new ArrayList<>(), subsets);

        return subsets;
    }

    private void recurrsion(int[] nums, int ptr, List<Integer> subset, List<List<Integer>> subsets) {
        // Add the current subset (deep copy) to the result list
        // Every subset formed so far is valid
        subsets.add(new ArrayList<>(subset));

        // Base case: if pointer reaches end of array, stop recursion
        if (ptr == nums.length) {
            return;
        }

        // Try to include each element starting from 'ptr'
        for (int i = ptr; i < nums.length; i++) {

            // Skip duplicate elements on the same recursive level
            // 'i > ptr' ensures we only skip duplicates *within* this loop level,
            // not across different branches
            if (i > ptr && nums[i] == nums[i - 1]) continue;

            // Choose current element — include it in the subset
            subset.add(nums[i]);

            // Recurse with next index (i + 1) to explore further elements
            recurrsion(nums, i + 1, subset, subsets);

            // Backtrack: remove the last added element to explore next possibility
            subset.remove(subset.size() - 1);
        }
    }
}
