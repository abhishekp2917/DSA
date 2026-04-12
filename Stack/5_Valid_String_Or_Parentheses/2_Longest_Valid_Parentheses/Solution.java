import java.util.*;

class Solution {

    public int longestValidParentheses(String s) {

        int maxLen = 0;

        // --------------------------------------------------
        // stack → tracks parentheses structure
        // countStack → tracks valid lengths per layer
        // --------------------------------------------------
        Deque<Character> stack = new ArrayDeque<>();
        Deque<Integer> countStack = new ArrayDeque<>();

        // Base layer (like dummy frame)
        countStack.push(0);


        // --------------------------------------------------
        // STEP 1: Traverse string
        // --------------------------------------------------
        for (char ch : s.toCharArray()) {

            // --------------------------------------------------
            // CASE 1: Opening bracket
            // --------------------------------------------------
            if (ch == '(') {

                // New layer begins
                stack.push(ch);

                // Push new count layer
                countStack.push(0);
            }

            // --------------------------------------------------
            // CASE 2: Invalid closing bracket
            // --------------------------------------------------
            else if (ch == ')' &&
                     (stack.isEmpty() || stack.peek() == ')')) {

                // Reset everything
                stack.clear();
                countStack.clear();

                // Start fresh
                countStack.push(0);
            }

            // --------------------------------------------------
            // CASE 3: Valid closing bracket
            // --------------------------------------------------
            else {

                // Match with '('
                stack.pop();

                // Current valid segment
                int count = countStack.pop() + 2;

                // Merge with previous layer
                countStack.push(countStack.pop() + count);

                // Update answer
                maxLen = Math.max(maxLen, countStack.peek());
            }
        }

        return maxLen;
    }
}