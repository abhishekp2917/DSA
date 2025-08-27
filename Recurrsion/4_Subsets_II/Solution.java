import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        Arrays.sort(nums);
        recurrsion(nums, 0, new ArrayList<>(), subsets);
        return subsets;
    }

    private void recurrsion(int[] nums, int ptr, List<Integer> subset, List<List<Integer>> subsets) {
        subsets.add(new ArrayList<>(subset));
        if(ptr==nums.length) {
            return;
        }
        for(int i=ptr; i<nums.length; i++) {
            if(i>ptr && nums[i]==nums[i-1]) continue;
            subset.add(nums[i]);
            recurrsion(nums, i+1, subset, subsets);
            subset.remove(subset.size()-1);
        }
    }
}

