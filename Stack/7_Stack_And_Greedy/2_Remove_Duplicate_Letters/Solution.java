import java.util.*;

class Solution {

    public String removeDuplicateLetters(String s) {

        // --------------------------------------------------
        // charFreq[i] → how many times character appears ahead
        // --------------------------------------------------
        //
        // WHY?
        // To know whether we can safely REMOVE a character
        // (i.e., it appears later again)
        int[] charFreq = new int[26];

        for (char ch : s.toCharArray()) {
            charFreq[ch - 'a']++;
        }


        // --------------------------------------------------
        // charSet → tracks which characters are already in stack
        // --------------------------------------------------
        //
        // WHY?
        // We want ONLY ONE occurrence of each character
        Set<Character> charSet = new HashSet<>();


        // --------------------------------------------------
        // stack → maintains result in progress
        // --------------------------------------------------
        //
        // This will behave like a MONOTONIC stack
        // to ensure lexicographically smallest result
        Deque<Character> stack = new ArrayDeque<>();


        // --------------------------------------------------
        // STEP 1: Traverse characters
        // --------------------------------------------------
        for (char ch : s.toCharArray()) {

            // --------------------------------------------------
            // CASE 1: If character NOT already in result
            // --------------------------------------------------
            if (!charSet.contains(ch)) {

                // --------------------------------------------------
                // GREEDY REMOVAL:
                // --------------------------------------------------
                //
                // Remove previous characters if:
                //
                // 1. They are lexicographically bigger
                // 2. They appear again later (safe to remove)
                //
                // WHY?
                // To make result lexicographically smallest
                //
                // Example:
                // stack = "bc", current = 'a'
                // → remove 'c', remove 'b', then add 'a'
                while (!stack.isEmpty() &&
                       stack.peekLast() >= ch &&
                       charFreq[stack.peekLast() - 'a'] > 0) {

                    // Remove from stack
                    char removed = stack.removeLast();

                    // Also remove from set
                    charSet.remove(removed);
                }

                // Add current character
                stack.addLast(ch);
                charSet.add(ch);
            }

            // --------------------------------------------------
            // IMPORTANT:
            // Reduce frequency AFTER processing
            // --------------------------------------------------
            charFreq[ch - 'a']--;
        }


        // --------------------------------------------------
        // STEP 2: Build final answer
        // --------------------------------------------------
        StringBuilder answer = new StringBuilder();

        for (char ch : stack) {
            answer.append(ch);
        }

        return answer.toString();
    }
}