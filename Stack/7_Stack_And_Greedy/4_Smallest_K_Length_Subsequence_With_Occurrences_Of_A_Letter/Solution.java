import java.util.*;

class Solution {

    public String smallestSubsequence(String s, int k, char letter, int repetition) {

        int n = s.length();

        // --------------------------------------------------
        // toRemove → how many characters we can discard
        // --------------------------------------------------
        int toRemove = n - k;

        // --------------------------------------------------
        // letterNeeded → how many required 'letter' still needed
        // --------------------------------------------------
        int letterNeeded = repetition;


        // --------------------------------------------------
        // Count total occurrences of 'letter'
        // --------------------------------------------------
        int letterCount = 0;
        for (char ch : s.toCharArray()) {
            if (ch == letter) letterCount++;
        }

        // --------------------------------------------------
        // letterToRemove → how many 'letter' we can safely drop
        // --------------------------------------------------
        int letterToRemove = letterCount - repetition;


        // --------------------------------------------------
        // stack → builds result (monotonic increasing)
        // --------------------------------------------------
        Deque<Character> stack = new ArrayDeque<>();


        // --------------------------------------------------
        // STEP 1: Traverse string
        // --------------------------------------------------
        for (char ch : s.toCharArray()) {

            // --------------------------------------------------
            // GREEDY REMOVAL
            // --------------------------------------------------
            //
            // Remove previous characters if:
            // 1. stack.top > current (lexicographically worse)
            // 2. we still have removals left
            // 3. removing it does NOT break letter constraint
            while (!stack.isEmpty() &&
                   stack.peekLast() > ch &&
                   toRemove > 0 &&
                   (stack.peekLast() != letter || letterToRemove > 0)) {

                char removed = stack.removeLast();

                // If we removed required letter
                if (removed == letter) {
                    letterNeeded++;   // we now need one more
                    letterToRemove--; // used one removable quota
                }

                toRemove--;
            }


            // --------------------------------------------------
            // Decide whether to include current character
            // --------------------------------------------------
            //
            // Condition:
            // 1. If it's required letter → always include
            // 2. Else:
            //    ensure enough space remains for required letters
            //
            // k - stack.size() = slots remaining
            if (ch == letter || k - stack.size() > letterNeeded) {

                stack.addLast(ch);

                if (ch == letter) {
                    letterNeeded--;
                }
            }

            else {
                // Skip this character
                toRemove--;
            }
        }


        // --------------------------------------------------
        // STEP 2: Remove extra elements if needed
        // --------------------------------------------------
        while (toRemove > 0) {
            char removed = stack.removeLast();

            if (removed == letter) {
                // Should not ideally happen due to constraints,
                // but kept safe
                letterNeeded++;
            }

            toRemove--;
        }


        // --------------------------------------------------
        // STEP 3: Build result
        // --------------------------------------------------
        StringBuilder smallestSubseq = new StringBuilder();

        for (char ch : stack) {
            smallestSubseq.append(ch);
        }

        return smallestSubseq.toString();
    }
}