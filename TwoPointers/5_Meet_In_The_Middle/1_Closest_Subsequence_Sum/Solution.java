import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {

    public int minAbsDifference(int[] nums, int goal) {

        int n = nums.length;

        // This will store the minimum absolute difference
        // between any subset sum and the goal.
        int minAbsDiff = Integer.MAX_VALUE;

        // We split the array into two halves.
        // Reason:
        // Generating all subset sums of full array would take O(2^n),
        // which is too large when n can be up to 40.
        //
        // Instead, we use Meet-in-the-Middle:
        // Generate subset sums for two halves separately,
        // each taking O(2^(n/2)).
        List<Integer> leftSubsetSum = new ArrayList<>();
        List<Integer> rightSubsetSum = new ArrayList<>();

        // Generate subset sums for first half [0 ... n/2 - 1]
        generateSubsetSum(nums, 0, n/2-1, 0, leftSubsetSum);

        // Generate subset sums for second half [n/2 ... n-1]
        generateSubsetSum(nums, n/2, n-1, 0, rightSubsetSum);

        // Sort both lists.
        // Reason:
        // After sorting, we can apply two pointer technique
        // to efficiently find closest pair to goal.
        Collections.sort(leftSubsetSum);
        Collections.sort(rightSubsetSum);

        int leftLen = leftSubsetSum.size();
        int rightLen = rightSubsetSum.size();

        // Two pointers:
        // i → smallest sum from left
        // j → largest sum from right
        int i = 0, j = rightLen - 1;

        // We try to combine one sum from left
        // and one sum from right.
        while(i < leftLen && j >= 0) {

            int sum = leftSubsetSum.get(i) + rightSubsetSum.get(j);

            int diff = Math.abs(goal - sum);

            // Update minimum difference seen so far.
            minAbsDiff = Math.min(minAbsDiff, diff);

            // Now decide pointer movement.
            //
            // If sum > goal:
            // We need smaller total → decrease right side.
            if(sum > goal) {
                j--;
            }
            // If sum < goal:
            // We need larger total → increase left side.
            else if(sum < goal) {
                i++;
            }
            // If sum == goal:
            // Perfect match. Difference is zero.
            // Cannot do better than 0.
            else {
                break;
            }
        }

        return minAbsDiff;
    }

    private void generateSubsetSum(int[] nums,
                                   int start,
                                   int end,
                                   int sum,
                                   List<Integer> subset) {

        // Base case:
        // When start crosses end,
        // we have formed one subset sum.
        if(start > end) {
            subset.add(sum);
            return;
        }

        // Choice 1: Include nums[start]
        generateSubsetSum(nums,
                          start+1,
                          end,
                          sum + nums[start],
                          subset);

        // Choice 2: Exclude nums[start]
        generateSubsetSum(nums,
                          start+1,
                          end,
                          sum,
                          subset);
    } 
}
