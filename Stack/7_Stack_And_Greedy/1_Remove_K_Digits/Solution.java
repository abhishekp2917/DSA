import java.util.*;

class Solution {

    public String removeKdigits(String num, int k) {

        // --------------------------------------------------
        // Stack → will maintain increasing digits
        // (monotonic increasing)
        // --------------------------------------------------
        Deque<Character> stack = new ArrayDeque<>();


        // --------------------------------------------------
        // STEP 1: Traverse digits
        // --------------------------------------------------
        for (char ch : num.toCharArray()) {

            // --------------------------------------------------
            // Remove bigger previous digits
            // --------------------------------------------------
            //
            // WHY?
            // Smaller digit on left → smaller number overall
            while (!stack.isEmpty() &&
                   stack.peekLast() > ch &&
                   k > 0) {

                stack.removeLast();
                k--;
            }

            // --------------------------------------------------
            // Avoid leading zeros
            // --------------------------------------------------
            if (stack.isEmpty() && ch == '0')
                continue;

            stack.addLast(ch);
        }


        // --------------------------------------------------
        // STEP 2: If still k left
        // remove from END
        // --------------------------------------------------
        //
        // WHY?
        // Number is already increasing
        // → remove largest tail digits
        while (k > 0 && !stack.isEmpty()) {
            stack.removeLast();
            k--;
        }


        // --------------------------------------------------
        // STEP 3: Build result
        // --------------------------------------------------
        StringBuilder smallestNum = new StringBuilder();

        for (char ch : stack) {
            smallestNum.append(ch);
        }


        // --------------------------------------------------
        // STEP 4: Edge case → empty
        // --------------------------------------------------
        return (smallestNum.length() != 0)
               ? smallestNum.toString()
               : "0";
    }
}