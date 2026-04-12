import java.util.*;

class Solution {

    public String decodeString(String s) {

        // --------------------------------------------------
        // stack1 → stores partial strings (StringBuilder)
        // stack2 → stores repeat counts
        // --------------------------------------------------
        Stack<StringBuilder> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        // Base string (outermost level)
        stack1.push(new StringBuilder());

        int idx = 0;

        // --------------------------------------------------
        // STEP 1: Traverse string
        // --------------------------------------------------
        while (idx < s.length()) {

            char ch = s.charAt(idx);

            // --------------------------------------------------
            // CASE 1: Digit → build full number
            // --------------------------------------------------
            if (Character.isDigit(ch)) {

                int num = 0;

                // Handle multi-digit numbers (e.g., "12[a]")
                while (Character.isDigit(s.charAt(idx))) {
                    num = num * 10 + (s.charAt(idx) - '0');
                    idx++;
                }

                stack2.push(num);
            }

            // --------------------------------------------------
            // CASE 2: Opening bracket '['
            // --------------------------------------------------
            else if (ch == '[') {

                // Start a new substring level
                stack1.push(new StringBuilder());
                idx++;
            }

            // --------------------------------------------------
            // CASE 3: Closing bracket ']'
            // --------------------------------------------------
            else if (ch == ']') {

                // Get repetition count
                int count = stack2.pop();

                // Get current substring
                String str = stack1.pop().toString();

                // Append repeated string to previous level
                stack1.peek().append(str.repeat(count));

                idx++;
            }

            // --------------------------------------------------
            // CASE 4: Normal character
            // --------------------------------------------------
            else {

                // Append to current active string
                stack1.peek().append(ch);
                idx++;
            }
        }

        // Final decoded string
        return stack1.pop().toString();
    }
}