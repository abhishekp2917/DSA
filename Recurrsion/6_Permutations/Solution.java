import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution1 {
    public List<List<Integer>> permute(int[] nums) {
        Queue<Integer> numsQueue = new LinkedList<>();
        for(int num : nums) numsQueue.add(num);
        List<List<Integer>> permutations = new ArrayList<>();
        recurrsion(numsQueue, new ArrayList<>(), permutations);
        return permutations;
    }

    private void recurrsion(Queue<Integer> numsQueue, List<Integer> currPermutation, List<List<Integer>> permutations) {
        if(numsQueue.isEmpty()) {
            permutations.add(new ArrayList<>(currPermutation));
            return;
        }
        int queueSize = numsQueue.size();
        while(queueSize-->0) {
            Integer num = numsQueue.poll();
            currPermutation.add(num);
            recurrsion(numsQueue, currPermutation, permutations);
            currPermutation.remove(currPermutation.size()-1);
            numsQueue.add(num);
        }
    }
}

class Solution2 {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> permutations = new ArrayList<>();
        recurrsion(nums, nums.length-1, permutations);
        return permutations;
    }

    private void recurrsion(int[] nums, int endPtr, List<List<Integer>> permutations) {
        if(endPtr<0) {
            List<Integer> currPermutation = new ArrayList<>();
            for(int num : nums) currPermutation.add(num);
            permutations.add(currPermutation);
            return;
        }
        for(int i=0; i<=endPtr; i++) {
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



