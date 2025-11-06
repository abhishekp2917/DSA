import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // 'res' stores all unique combinations that sum to the target
        List<List<Integer>> res = new ArrayList<>();
        
        // 'comb' temporarily holds the current combination being formed
        List<Integer> comb = new ArrayList<>();
        
        // Sort the array to bring duplicates together
        // Sorting helps in:
        //   1. Skipping duplicate numbers efficiently
        //   2. Early termination if the current number exceeds remaining target
        Arrays.sort(candidates);
        
        // Begin recursive backtracking from index 0
        subsequence(candidates, target, 0, res, comb);
        
        return res; // Return the list of all unique valid combinations
    }

    private void subsequence(int[] candidates, int target, int currPtr, List<List<Integer>> res, List<Integer> comb) {
        // Base case 1: If target becomes 0 → current combination is valid
        if (target == 0) {
            // Add a deep copy of current combination to result
            res.add(new ArrayList<>(comb));
            return;
        }

        // Base case 2: If index crosses array bounds OR current number > remaining target
        // No point in continuing, because numbers are sorted (all further will be larger)
        if (currPtr == candidates.length || target < candidates[currPtr]) return;

        // Try all possible candidates starting from current index
        for (int i = currPtr; i < candidates.length; i++) {

            // Skip duplicates at the same recursion level to ensure unique combinations
            // (i > currPtr) means: if the same number appears again on this level, skip it
            if (i > currPtr && candidates[i] == candidates[i - 1]) continue;

            // Choose current number → include it in current combination
            comb.add(candidates[i]);

            // Recurse for the remaining target and move pointer to next index (i + 1)
            // Unlike Combination Sum I, here each element can be used at most once
            subsequence(candidates, target - candidates[i], i + 1, res, comb);

            // Backtrack → remove last chosen number before exploring next possibility
            comb.remove(comb.size() - 1);
        }
    }
}
