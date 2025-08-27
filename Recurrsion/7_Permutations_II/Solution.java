import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution1 {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> permutations = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        Arrays.sort(nums);
        recurrsion(nums, used, new ArrayList<>(), permutations);
        return permutations;
    }

    private void recurrsion(int[] nums, boolean[] used, List<Integer> currPermutation, List<List<Integer>> permutations) {
        if(currPermutation.size()==nums.length) {
            permutations.add(new ArrayList<>(currPermutation));
            return;
        }
        for(int i=0; i<nums.length; i++) {
            if(used[i] || (i>0 && nums[i-1]==nums[i] && !used[i-1])) continue;
            used[i] = true;
            currPermutation.add(nums[i]);
            recurrsion(nums, used, currPermutation, permutations);
            currPermutation.remove(currPermutation.size()-1);
            used[i] = false;
        }
    }
}


class Solution2 {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> permutations = new ArrayList<>();
        Arrays.sort(nums);
        recurrsion(nums, nums.length-1, permutations);
        return permutations;
    }

    private void recurrsion(int[] nums, int endPtr, List<List<Integer>> permutations) {
        if(endPtr<0) {
           List<Integer> permutation = new ArrayList<>();
           for(int num : nums) permutation.add(num);
           permutations.add(permutation);
           return; 
        }
        Set<Integer> usedNum = new HashSet<>();
        for(int i=0; i<=endPtr; i++) {
            if(usedNum.contains(nums[i])) continue;
            usedNum.add(nums[i]);
            swap(nums, i, endPtr);
            recurrsion(nums, endPtr-1, permutations);
            swap(nums, i, endPtr);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
