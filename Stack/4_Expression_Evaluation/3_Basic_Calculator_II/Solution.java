import java.util.*;

class Solution {

    public int calculate(String s) {

        // stack1 → stores numbers (processed values)
        // stack2 → stores operators
        Stack<Integer> stack1 = new Stack<>();
        Stack<Character> stack2 = new Stack<>();

        int i = 0;

        // --------------------------------------------------
        // STEP 1: Traverse string
        // --------------------------------------------------
        while (i < s.length()) {

            char ch = s.charAt(i);

            // --------------------------------------------------
            // CASE 1: Number (handle multi-digit)
            // --------------------------------------------------
            if (Character.isDigit(ch)) {

                int num = 0;

                while (i < s.length() &&
                       Character.isDigit(s.charAt(i))) {

                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }

                // --------------------------------------------------
                // Handle HIGH precedence (*, /)
                // immediately
                // --------------------------------------------------
                if (!stack2.isEmpty() && stack2.peek() == '*') {

                    stack1.push(stack1.pop() * num);
                    stack2.pop();
                }

                else if (!stack2.isEmpty() && stack2.peek() == '/') {

                    stack1.push(stack1.pop() / num);
                    stack2.pop();
                }

                // --------------------------------------------------
                // Convert subtraction into addition
                // --------------------------------------------------
                //
                // Instead of storing '-' separately,
                // we convert:
                // a - b  → a + (-b)
                //
                else if (!stack2.isEmpty() && stack2.peek() == '-') {

                    stack1.push(-num);
                    stack2.pop();

                    // convert '-' to '+'
                    stack2.push('+');
                }

                else {
                    stack1.push(num);
                }
            }

            // --------------------------------------------------
            // CASE 2: Space → ignore
            // --------------------------------------------------
            else if (ch == ' ') {
                i++;
                continue;
            }

            // --------------------------------------------------
            // CASE 3: Operator
            // --------------------------------------------------
            else {
                stack2.push(ch);
                i++;
            }
        }


        // --------------------------------------------------
        // STEP 2: Resolve remaining + operations
        // --------------------------------------------------
        while (!stack2.isEmpty()) {

            int num = stack1.pop();

            // Only '+' remains
            stack1.push(stack1.pop() + num);

            stack2.pop();
        }

        return stack1.pop();
    }
}