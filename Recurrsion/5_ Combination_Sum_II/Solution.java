import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> comb = new ArrayList<>();
        Arrays.sort(candidates);
        subsequence(candidates, target, 0, res, comb);
        return res;
    }

    private void subsequence(int[] candidates, int target, int currPtr, List<List<Integer>> res, List<Integer> comb) {
        if(target==0) {
            res.add(new ArrayList<>(comb));
            return;
        }
        if(currPtr==candidates.length || target<candidates[currPtr]) return;
        for(int i=currPtr; i<candidates.length; i++) {
            if(i>currPtr && candidates[i]==candidates[i-1]) continue;
            comb.add(candidates[i]);
            subsequence(candidates, target-candidates[i], i+1, res, comb);
            comb.remove(comb.size()-1);
        }
    }
}

