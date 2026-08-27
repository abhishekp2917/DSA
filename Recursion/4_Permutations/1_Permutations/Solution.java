import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


class Solution1 {
    public List<List<Integer>> permute(int[] nums) {
        // Convert array elements into a queue to simulate choosing elements step by step.
        Queue<Integer> numsQueue = new LinkedList<>();
        for(int num : nums) numsQueue.add(num);

        List<List<Integer>> permutations = new ArrayList<>();

        // Start recursion with an empty permutation builder
        recurrsion(numsQueue, new ArrayList<>(), permutations);

        return permutations;
    }

    private void recurrsion(Queue<Integer> numsQueue, List<Integer> currPermutation, List<List<Integer>> permutations) {

        // Base case: when queue becomes empty, we formed one complete permutation.
        if(numsQueue.isEmpty()) {
            permutations.add(new ArrayList<>(currPermutation));
            return;
        }

        // The size is stored before loop because queue size changes during iteration.
        int queueSize = numsQueue.size();

        // Iterate through current available choices
        while(queueSize-- > 0) {

            // Pick a number from queue
            Integer num = numsQueue.poll();

            // Add to current path (partial permutation)
            currPermutation.add(num);

            // Explore deeper recursive state with reduced queue
            recurrsion(numsQueue, currPermutation, permutations);

            // Backtrack: undo the addition for next choice
            currPermutation.remove(currPermutation.size()-1);

            // Add the number back to queue for next iteration path
            numsQueue.add(num);
        }
    }
}

class Solution2 {
    public List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> permutations = new ArrayList<>();

        // Start recursion from last index,
        // meaning every level decides what goes at the 'endPtr' index.
        recurrsion(nums, nums.length - 1, permutations);

        return permutations;
    }

    private void recurrsion(int[] nums, int endPtr, List<List<Integer>> permutations) {

        // Base case: when endPtr crosses array limits, we formed one permutation.
        if (endPtr < 0) {
            List<Integer> currPermutation = new ArrayList<>();
            for(int num : nums) currPermutation.add(num);
            permutations.add(currPermutation);
            return;
        }

        // Try placing each available element at position `endPtr`
        for (int i = 0; i <= endPtr; i++) {

            // Swap current element to fix it at endPtr position
            swap(nums, i, endPtr);

            // Recursively fix next position (endPtr - 1)
            recurrsion(nums, endPtr - 1, permutations);

            // Backtrack: revert swap so that future choices are unaffected
            swap(nums, i, endPtr);
        }
    }

    // Helper function to swap two positions in array
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}



