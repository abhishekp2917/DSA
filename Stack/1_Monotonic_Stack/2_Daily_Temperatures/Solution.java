import java.util.*;

class Solution {

    public int[] dailyTemperatures(int[] temperatures) {

        int n = temperatures.length;

        // Answer array:
        // noOfDays[i] = number of days until a warmer temperature
        int[] noOfDays = new int[n];

        // Monotonic stack (stores indices)
        //
        // Why indices instead of values?
        // Because we need distance (index difference)
        Deque<Integer> stack = new ArrayDeque<>();


        // --------------------------------------------------
        // STEP 1: Traverse from RIGHT → LEFT
        // --------------------------------------------------
        //
        // Why?
        // Because we need "next warmer day"
        // → future information must already be processed
        for (int i = n - 1; i >= 0; i--) {

            // --------------------------------------------------
            // STEP 2: Maintain monotonic decreasing stack
            // --------------------------------------------------
            //
            // Remove all indices whose temperature
            // is ≤ current temperature
            //
            // Why?
            // Because they can NEVER be next warmer day
            //
            // If temperatures[i] >= temperatures[stack top],
            // then current blocks them for all earlier elements
            while (!stack.isEmpty() &&
                   temperatures[i] >= temperatures[stack.peekLast()]) {

                stack.removeLast();
            }


            // --------------------------------------------------
            // STEP 3: Assign answer
            // --------------------------------------------------
            //
            // If stack not empty:
            //   top = next warmer day index
            //
            // Distance = index difference
            noOfDays[i] =
                (!stack.isEmpty())
                    ? stack.peekLast() - i
                    : 0;


            // --------------------------------------------------
            // STEP 4: Push current index
            // --------------------------------------------------
            //
            // This index can act as a candidate
            // for elements to the left
            stack.addLast(i);
        }

        return noOfDays;
    }
}