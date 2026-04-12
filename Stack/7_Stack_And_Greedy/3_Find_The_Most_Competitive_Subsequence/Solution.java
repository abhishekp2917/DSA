import java.util.*;

class Solution {

    public int[] mostCompetitive(int[] nums, int k) {

        int n = nums.length;

        // --------------------------------------------------
        // toRemove = how many elements we are allowed to drop
        // --------------------------------------------------
        //
        // Since we need k elements out of n,
        // we can remove (n - k) elements
        int toRemove = n - k;


        // --------------------------------------------------
        // stack → will store the best subsequence
        // --------------------------------------------------
        //
        // Maintains MONOTONIC INCREASING order
        Deque<Integer> stack = new ArrayDeque<>();


        // --------------------------------------------------
        // STEP 1: Traverse array
        // --------------------------------------------------
        for (int num : nums) {

            // --------------------------------------------------
            // GREEDY REMOVAL
            // --------------------------------------------------
            //
            // Remove previous elements if:
            // 1. They are greater than current
            // 2. We still have removals left
            //
            // WHY?
            // Smaller element earlier → lexicographically smaller subsequence
            while (!stack.isEmpty() &&
                   stack.peekLast() > num &&
                   toRemove > 0) {

                stack.removeLast();
                toRemove--;
            }

            // Add current element
            stack.addLast(num);
        }


        // --------------------------------------------------
        // STEP 2: If removals still left
        // --------------------------------------------------
        //
        // Remove from END
        //
        // WHY?
        // Stack is already increasing → tail has largest elements
        while (toRemove > 0) {
            stack.removeLast();
            toRemove--;
        }


        // --------------------------------------------------
        // STEP 3: Build result of size k
        // --------------------------------------------------
        int[] competitiveArr = new int[k];

        for (int i = 0; i < k; i++) {
            competitiveArr[i] = stack.removeFirst();
        }

        return competitiveArr;
    }
}