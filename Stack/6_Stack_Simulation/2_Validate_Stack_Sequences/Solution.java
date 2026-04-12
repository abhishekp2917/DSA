import java.util.*;

class Solution {

    public boolean validateStackSequences(int[] pushed, int[] popped) {

        int n = pushed.length;

        // Stack to simulate real stack behavior
        Deque<Integer> stack = new ArrayDeque<>();

        int i = 0; // pointer for pushed
        int j = 0; // pointer for popped


        // --------------------------------------------------
        // STEP 1: Simulate push + pop
        // --------------------------------------------------
        while (i < n || j < n) {

            // --------------------------------------------------
            // CASE 1: Can we pop?
            // --------------------------------------------------
            //
            // If stack top matches next pop element
            if (!stack.isEmpty() &&
                stack.peek() == popped[j]) {

                stack.pop();
                j++;
            }

            // --------------------------------------------------
            // CASE 2: Push more elements
            // --------------------------------------------------
            else if (i < n) {

                stack.push(pushed[i]);
                i++;
            }

            // --------------------------------------------------
            // CASE 3: Cannot push AND cannot pop
            // → invalid sequence
            // --------------------------------------------------
            else {
                return j == n;
            }
        }

        return true;
    }
}