import java.util.*;

class Solution {

    public int calculate(String s) {

        int n = s.length();

        // Stack stores:
        // [ ... previousResult, signBeforeBracket, currentResult ... ]
        Stack<Integer> numStack = new Stack<>();

        // Base result (outside all brackets)
        numStack.push(0);

        int i = 0;

        // Current sign (+1 or -1)
        int sign = 1;


        // --------------------------------------------------
        // STEP 1: Traverse string
        // --------------------------------------------------
        while (i < n) {

            char ch = s.charAt(i);

            // --------------------------------------------------
            // CASE 1: Number (handle multi-digit)
            // --------------------------------------------------
            if (Character.isDigit(ch)) {

                int num = 0;

                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }

                // Apply sign immediately
                num = num * sign;

                // Add to current running result
                numStack.push(numStack.pop() + num);

                // Reset sign
                sign = 1;
            }

            // --------------------------------------------------
            // CASE 2: '-' operator
            // --------------------------------------------------
            else if (ch == '-') {

                sign = -1;
                i++;
            }

            // --------------------------------------------------
            // CASE 3: '(' → start new context
            // --------------------------------------------------
            else if (ch == '(') {

                // Save current sign for this bracket
                numStack.push(sign);

                // Start fresh result inside bracket
                numStack.push(0);

                // Reset sign
                sign = 1;

                i++;
            }

            // --------------------------------------------------
            // CASE 4: ')' → resolve bracket
            // --------------------------------------------------
            else if (ch == ')') {

                // Result inside bracket
                int num = numStack.pop();

                // Sign before bracket
                int numSign = numStack.pop();

                // Merge with previous result
                numStack.push(
                    numStack.pop() + num * numSign
                );

                i++;
            }

            // --------------------------------------------------
            // CASE 5: '+' or space
            // --------------------------------------------------
            else {
                // '+' doesn't change sign (default = +1)
                i++;
            }
        }

        return numStack.pop();
    }
}