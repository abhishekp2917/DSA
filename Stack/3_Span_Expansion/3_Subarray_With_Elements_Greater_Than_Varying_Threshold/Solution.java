import java.util.*;

class Solution {

    public int validSubarraySize(int[] nums, int threshold) {

        int n = nums.length;

        // Monotonic increasing stack (stores indices)
        //
        // We push -1 as a dummy index to simplify boundary handling
        // (acts as "no smaller element on left")
        Stack<Integer> minStack = new Stack<>();
        minStack.push(-1);


        // --------------------------------------------------
        // STEP 1: Traverse array
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {

            // --------------------------------------------------
            // Resolve elements when current is smaller
            // --------------------------------------------------
            //
            // If current element is smaller than stack top,
            // then stack top's "right boundary" is found
            //
            // So we finalize subarray where that element is minimum
            while (minStack.peek() != -1 &&
                   nums[minStack.peek()] >= nums[i]) {

                int idx = minStack.pop();

                int num = nums[idx]; // minimum of subarray

                // --------------------------------------------------
                // Find boundaries
                // --------------------------------------------------

                // Right boundary = i - 1
                int endIdx = i - 1;

                // Left boundary = next index after smaller element
                int startIdx = minStack.peek() + 1;

                // Length of subarray
                int k = endIdx - startIdx + 1;

                // --------------------------------------------------
                // Check condition
                // --------------------------------------------------
                //
                // We need:
                // num > threshold / k
                //
                // Instead of division (precision issues),
                // use multiplication:
                //
                // num * k > threshold
                if (num * k > threshold)
                    return k;
            }

            // Push current index
            minStack.push(i);
        }


        // --------------------------------------------------
        // STEP 2: Process remaining stack
        // --------------------------------------------------
        //
        // For remaining elements,
        // assume right boundary = n - 1
        while (minStack.peek() != -1) {

            int idx = minStack.pop();

            int num = nums[idx];

            int endIdx = n - 1;

            int startIdx = minStack.peek() + 1;

            int k = endIdx - startIdx + 1;

            if (num * k > threshold)
                return k;
        }

        return -1;
    }
}