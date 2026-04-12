import java.util.*;

class Solution {

    public int[] nextGreaterElements(int[] nums) {

        int n = nums.length;

        // Monotonic stack (stores values)
        //
        // Maintains decreasing order
        // (top = next greater candidate)
        Deque<Integer> stack = new ArrayDeque<>();

        int[] ans = new int[n];

        // --------------------------------------------------
        // STEP 1: Traverse array TWICE (circular handling)
        // --------------------------------------------------
        //
        // Why 2*n?
        //
        // Because array is circular:
        // after reaching end, we should check from start again
        //
        // Example:
        // nums = [1,2,1]
        // Next greater for last 1 is 2 (wrap around)
        for (int i = 2 * n - 1; i >= 0; i--) {

            int idx = i % n; // simulate circular index


            // --------------------------------------------------
            // STEP 2: Maintain monotonic decreasing stack
            // --------------------------------------------------
            //
            // Remove all elements <= current element
            //
            // Why?
            // Because they can NEVER be next greater
            //
            // If nums[idx] >= stack top:
            //   → current blocks them
            while (!stack.isEmpty() &&
                   nums[idx] >= stack.peekLast()) {

                stack.removeLast();
            }


            // --------------------------------------------------
            // STEP 3: Assign answer
            // --------------------------------------------------
            //
            // If stack not empty:
            //   top is next greater element
            //
            // Else:
            //   no greater element exists
            ans[idx] =
                (!stack.isEmpty())
                    ? stack.peekLast()
                    : -1;


            // --------------------------------------------------
            // STEP 4: Push current element
            // --------------------------------------------------
            //
            // It may serve as next greater
            // for elements to the LEFT
            stack.addLast(nums[idx]);
        }

        return ans;
    }
}