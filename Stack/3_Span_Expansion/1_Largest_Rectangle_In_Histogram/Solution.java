import java.util.Stack;

class Solution {

    public int largestRectangleArea(int[] heights) {

        int n = heights.length;

        // Stack stores indices of bars
        //
        // Maintains increasing height order
        //
        // Why increasing?
        // Because when a smaller height comes,
        // we can resolve rectangles for taller bars
        Stack<Integer> stack = new Stack<>();

        int maxArea = 0;

        // --------------------------------------------------
        // STEP 1: Traverse histogram
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {

            // --------------------------------------------------
            // Resolve rectangles when current bar is smaller
            // --------------------------------------------------
            //
            // Why?
            //
            // If current height < stack top height,
            // then stack top can no longer extend to right
            //
            // So we finalize its rectangle
            while (!stack.isEmpty() &&
                   heights[stack.peek()] >= heights[i]) {

                // Height of rectangle
                int height = heights[stack.pop()];

                // --------------------------------------------------
                // Find boundaries
                // --------------------------------------------------

                // Nearest smaller to left
                int nearestSmallerLeft =
                    (!stack.isEmpty()) ? stack.peek() : -1;

                // Nearest smaller to right = current index
                int nearestSmallerRight = i;

                // Width = distance between boundaries
                int width =
                    nearestSmallerRight - nearestSmallerLeft - 1;

                // Compute area
                maxArea = Math.max(maxArea, height * width);
            }

            // Push current index
            stack.push(i);
        }


        // --------------------------------------------------
        // STEP 2: Process remaining stack
        // --------------------------------------------------
        //
        // Remaining elements:
        // No smaller element to right
        while (!stack.isEmpty()) {

            int height = heights[stack.pop()];

            int nearestSmallerLeft =
                (!stack.isEmpty()) ? stack.peek() : -1;

            // No smaller element on right
            int nearestSmallerRight = n;

            int width =
                nearestSmallerRight - nearestSmallerLeft - 1;

            maxArea = Math.max(maxArea, height * width);
        }

        return maxArea;
    }
}