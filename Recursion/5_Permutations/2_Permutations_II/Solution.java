import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution1 {

    // Pattern: Backtracking / DFS (Permutation with Duplicate Pruning)
    //
    // Core intuition:
    // This is a permutation problem where the input may contain duplicates.
    // We must generate all unique permutations without repetition.
    //
    // Sorting helps group duplicate numbers together so that we can
    // avoid choosing the same number multiple times at the same recursion level.

    public List<List<Integer>> permuteUnique(int[] nums) {

        List<List<Integer>> permutations = new ArrayList<>();

        // used[i] tells whether nums[i] has already been placed
        // in the current permutation path
        boolean[] used = new boolean[nums.length];

        // Sorting is critical for duplicate pruning logic
        Arrays.sort(nums);

        recurrsion(nums, used, new ArrayList<>(), permutations);

        return permutations;
    }

    private void recurrsion(
        int[] nums,
        boolean[] used,
        List<Integer> currPermutation,
        List<List<Integer>> permutations
    ) {

        // Base case:
        // When the permutation length equals array length,
        // we have formed one valid unique permutation.
        if (currPermutation.size() == nums.length) {
            permutations.add(new ArrayList<>(currPermutation));
            return;
        }

        // Try placing each element at the current position
        for (int i = 0; i < nums.length; i++) {

            // Skip if already used in current path
            //
            // Skip duplicates:
            // If nums[i] == nums[i-1] and nums[i-1] has not been used,
            // choosing nums[i] would generate a duplicate permutation.
            if (used[i] ||
                (i > 0 && nums[i - 1] == nums[i] && !used[i - 1])) {
                continue;
            }

            // Choose nums[i]
            used[i] = true;
            currPermutation.add(nums[i]);

            // Recurse to fill the next position
            recurrsion(nums, used, currPermutation, permutations);

            // Backtrack: undo the choice
            currPermutation.remove(currPermutation.size() - 1);
            used[i] = false;
        }
    }
}


class Solution2 {

    // Pattern: Backtracking / DFS (Permutation by Fixing Positions)
    //
    // Core intuition:
    // At each recursion level, we decide which value should be placed
    // at position endPtr.
    //
    // To avoid duplicate permutations, we ensure that each distinct
    // number is used only once at each position.

    public List<List<Integer>> permuteUnique(int[] nums) {

        List<List<Integer>> permutations = new ArrayList<>();

        // Sorting helps group duplicates together
        Arrays.sort(nums);

        recurrsion(nums, nums.length - 1, permutations);

        return permutations;
    }

    private void recurrsion(
        int[] nums,
        int endPtr,
        List<List<Integer>> permutations
    ) {

        // Base case:
        // All positions have been fixed
        if (endPtr < 0) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) permutation.add(num);
            permutations.add(permutation);
            return;
        }

        // usedNum ensures that the same number is not placed
        // multiple times at the same position
        Set<Integer> usedNum = new HashSet<>();

        for (int i = 0; i <= endPtr; i++) {

            // Skip duplicate values at this recursion level
            if (usedNum.contains(nums[i])) continue;

            usedNum.add(nums[i]);

            // Fix nums[i] at position endPtr
            swap(nums, i, endPtr);

            // Recurse to fix the remaining positions
            recurrsion(nums, endPtr - 1, permutations);

            // Backtrack: restore original order
            swap(nums, i, endPtr);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
