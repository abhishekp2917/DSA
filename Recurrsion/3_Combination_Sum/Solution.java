import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 'res' will store all unique combinations whose sum equals 'target'
        List<List<Integer>> res = new ArrayList<>();
        
        // 'comb' will temporarily hold the current combination being formed
        List<Integer> comb = new ArrayList<>();
        
        // Start recursive backtracking from index 0
        subsequence(candidates, 0, target, res, comb);
        
        return res; // Return all valid combinations
    }

    private void subsequence(int[] candidates, int startPtr, int target, List<List<Integer>> res, List<Integer> currComb) {
        // Base case: if target becomes negative, the current combination is invalid
        if (target < 0) return;

        // Base case: if target becomes exactly 0, we found a valid combination
        if (target == 0) {
            // Create a deep copy of the current combination before adding to result
            res.add(new ArrayList<>(currComb));
            return;
        }

        // Try all possible candidates starting from 'startPtr' to avoid duplicates
        for (int i = startPtr; i < candidates.length; i++) {
            // Include current candidate in the combination
            currComb.add(candidates[i]);

            /*
             * Recursive call with:
             * - Same 'i' instead of 'i + 1' because we can reuse the same element multiple times.
             * - Reduced 'target' by the current candidate's value.
             */
            subsequence(candidates, i, target - candidates[i], res, currComb);

            // Backtrack: remove last added element to try next candidate
            currComb.remove(currComb.size() - 1);
        }
    }
}
