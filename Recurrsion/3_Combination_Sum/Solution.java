import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> comb = new ArrayList<>();
        subsequence(candidates, 0, target, res, comb);
        return res;
    }

    private void subsequence(int[] candidates, int startPtr, int target, List<List<Integer>> res, List<Integer> currComb) {
        if(target<0) return;
        if(target==0) {
            res.add(new ArrayList<Integer>(currComb));
            return;
        }
        for(int i=startPtr; i<candidates.length; i++) {
            currComb.add(candidates[i]);
            subsequence(candidates, i, target-candidates[i], res, currComb);
            currComb.remove(currComb.size()-1);
        }
    }
}