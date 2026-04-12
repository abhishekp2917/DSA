import java.util.*;

class Solution {

    public int maximalRectangle(char[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        int largestRec = 0;

        // --------------------------------------------------
        // IDEA:
        // Treat each row as base of a histogram
        // --------------------------------------------------
        //
        // histogram[col] = height of consecutive '1's
        // ending at current row
        int[] histogram = new int[m];

        for (int row = 0; row < n; row++) {

            // --------------------------------------------------
            // STEP 1: Build histogram for current row
            // --------------------------------------------------
            for (int col = 0; col < m; col++) {

                // If '1', extend height
                if (matrix[row][col] == '1')
                    histogram[col]++;

                // If '0', reset height
                else
                    histogram[col] = 0;
            }

            // --------------------------------------------------
            // STEP 2: Solve largest rectangle in histogram
            // --------------------------------------------------
            //
            // Convert 2D → 1D problem
            largestRec = Math.max(
                largestRec,
                largestRectangleArea(histogram)
            );
        }

        return largestRec;
    }


    public int largestRectangleArea(int[] histogram) {

        int n = histogram.length;

        int largestRec = 0;

        // --------------------------------------------------
        // STEP 1: Find nearest smaller to RIGHT
        // --------------------------------------------------
        int[] nearestSmallerRight = new int[n];

        Stack<Integer> minStack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {

            // Maintain increasing stack
            while (!minStack.isEmpty() &&
                   histogram[minStack.peek()] >= histogram[i]) {

                minStack.pop();
            }

            // If no smaller → boundary = n
            nearestSmallerRight[i] =
                (!minStack.isEmpty()) ? minStack.peek() : n;

            minStack.push(i);
        }

        // Clear stack for reuse
        minStack.clear();


        // --------------------------------------------------
        // STEP 2: Find nearest smaller to LEFT + compute area
        // --------------------------------------------------
        for (int i = 0; i < n; i++) {

            int height = histogram[i];

            while (!minStack.isEmpty() &&
                   histogram[minStack.peek()] >= height) {

                minStack.pop();
            }

            int nearestSmallerLeft =
                (!minStack.isEmpty()) ? minStack.peek() : -1;

            // Width determined by NSE
            int width =
                nearestSmallerRight[i] - nearestSmallerLeft - 1;

            largestRec = Math.max(largestRec, height * width);

            minStack.push(i);
        }

        return largestRec;
    }
}