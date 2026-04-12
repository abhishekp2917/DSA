import java.util.*;

class Solution {

    public boolean isValid(String s) {

        // --------------------------------------------------
        // Map opening → closing brackets
        // --------------------------------------------------
        Map<Character, Character> brackets = new HashMap<>();
        brackets.put('(', ')');
        brackets.put('{', '}');
        brackets.put('[', ']');

        // Stack to track opening brackets
        Stack<Character> stack = new Stack<>();


        // --------------------------------------------------
        // STEP 1: Traverse string
        // --------------------------------------------------
        for (char ch : s.toCharArray()) {

            // --------------------------------------------------
            // CASE 1: Opening bracket
            // --------------------------------------------------
            if (brackets.containsKey(ch)) {

                // Push opening bracket
                stack.push(ch);
            }

            // --------------------------------------------------
            // CASE 2: Closing bracket
            // --------------------------------------------------
            else {

                // Check:
                // 1. Stack should not be empty
                // 2. Top should match current closing bracket
                if (!stack.isEmpty() &&
                    brackets.getOrDefault(stack.peek(), '-') == ch) {

                    // Valid pair → remove it
                    stack.pop();
                }
                else {
                    // Invalid structure
                    return false;
                }
            }
        }

        // --------------------------------------------------
        // STEP 2: Final check
        // --------------------------------------------------
        //
        // Stack must be empty
        // (all brackets matched)
        return stack.isEmpty();
    }
}