import java.util.*;

class Solution {

    public boolean find132pattern(int[] nums) {

        int n = nums.length;

        // --------------------------------------------------
        // STEP 1: Precompute minimum on left
        // --------------------------------------------------
        //
        // minLeft[i] = minimum value in nums[0...i]
        //
        // Why?
        // Because for 132 pattern:
        //
        // we need:
        // nums[i] < nums[k] < nums[j]
        //
        // Here:
        // i = left minimum
        // j = current index
        // k = some element in between
        int[] minLeft = findMinLeft(nums);


        // Stack will store candidates for "nums[k]"
        //
        // These are potential middle values
        Deque<Integer> stack = new ArrayDeque<>();


        // --------------------------------------------------
        // STEP 2: Traverse from RIGHT → LEFT
        // --------------------------------------------------
        //
        // Why?
        //
        // We fix nums[j] first (middle element of pattern)
        //
        // Then try to find nums[k] from right side
        for (int i = n - 1; i >= 0; i--) {

            // --------------------------------------------------
            // Check if nums[i] can be "nums[j]"
            // --------------------------------------------------
            //
            // Condition:
            // nums[j] > nums[i] (minLeft)
            //
            // If not satisfied:
            // cannot form 132 pattern
            if (nums[i] > minLeft[i]) {

                // --------------------------------------------------
                // Remove invalid "k" candidates
                // --------------------------------------------------
                //
                // nums[k] must be > nums[i] (minLeft)
                //
                // So remove all <= minLeft[i]
                while (!stack.isEmpty() &&
                       stack.peekLast() <= minLeft[i]) {

                    stack.removeLast();
                }

                // --------------------------------------------------
                // Check if valid "k" exists
                // --------------------------------------------------
                //
                // Condition:
                // nums[k] < nums[j]
                //
                // So:
                // minLeft[i] < nums[k] < nums[i]
                if (!stack.isEmpty() &&
                    stack.peekLast() < nums[i]) {

                    return true;
                }
            }

            // --------------------------------------------------
            // Add current element as future "k" candidate
            // --------------------------------------------------
            stack.addLast(nums[i]);
        }

        return false;
    }


    public int[] findMinLeft(int[] arr) {

        int[] minLeft = new int[arr.length];

        int min = Integer.MAX_VALUE;

        for (int i = 0; i < arr.length; i++) {

            // Maintain running minimum
            min = Math.min(min, arr[i]);

            minLeft[i] = min;
        }

        return minLeft;
    }
}