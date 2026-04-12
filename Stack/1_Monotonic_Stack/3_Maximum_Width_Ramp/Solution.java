import java.util.*;

class Solution {

    public int maxWidthRamp(int[] nums) {

        int n = nums.length;

        int maxWidth = 0;

        // --------------------------------------------------
        // STEP 1: Build a decreasing stack of indices
        // --------------------------------------------------
        //
        // Stack will store indices such that:
        //
        // nums[stack[0]] > nums[stack[1]] > nums[stack[2]] ...
        //
        // Why decreasing?
        //
        // Because we want BEST candidates for left side (i)
        // → smaller values are more useful
        //
        // Smaller value → easier to satisfy:
        // nums[i] <= nums[j]
        Deque<Integer> stack = new ArrayDeque<>();

        // First index is always a candidate
        stack.addLast(0);

        for (int i = 1; i < n; i++) {

            // --------------------------------------------------
            // Only push if STRICTLY smaller
            // --------------------------------------------------
            //
            // Why?
            //
            // If nums[i] >= previous,
            // it's WORSE as a left candidate
            //
            // Because:
            // - larger value → harder to satisfy nums[i] <= nums[j]
            // - also later index → smaller width
            //
            // So we ONLY keep "best" candidates
            if (nums[stack.peekLast()] > nums[i]) {

                stack.addLast(i);
            }
        }


        // --------------------------------------------------
        // STEP 2: Traverse from RIGHT → LEFT
        // --------------------------------------------------
        //
        // Why?
        //
        // We want maximum width (j - i)
        // → try largest j first
        for (int i = n - 1; i >= 0; i--) {

            // --------------------------------------------------
            // Try to match with left candidates
            // --------------------------------------------------
            //
            // While:
            // nums[left] <= nums[i]
            //
            // → valid ramp
            while (!stack.isEmpty() &&
                   nums[stack.peekLast()] <= nums[i]) {

                int leftIdx = stack.removeLast();

                maxWidth = Math.max(maxWidth, i - leftIdx);
            }
        }

        return maxWidth;
    }
}