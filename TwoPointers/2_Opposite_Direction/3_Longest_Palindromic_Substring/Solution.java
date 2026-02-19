class Solution {

    public String longestPalindrome(String s) {

        int n = s.length();

        // start and end will store indices
        // of the longest palindrome found so far.
        int start = 0, end = 0;

        // Every character can act as a center of palindrome.
        for (int i = 0; i < n; i++) {

            // Case 1: Odd length palindrome
            // Center is at index i
            int left = i;
            int right = i;

            // Expand outward while characters match.
            while (left >= 0 && right < n &&
                   s.charAt(left) == s.charAt(right)) {

                // If current palindrome length is larger
                // than previously recorded one, update.
                if ((end - start + 1) < (right - left + 1)) {
                    start = left;
                    end = right;
                }

                left--;
                right++;
            }

            // Case 2: Even length palindrome
            // Center lies between i and i+1
            left = i;
            right = i + 1;

            while (left >= 0 && right < n &&
                   s.charAt(left) == s.charAt(right)) {

                if ((end - start + 1) < (right - left + 1)) {
                    start = left;
                    end = right;
                }

                left--;
                right++;
            }
        }

        // Return substring from start to end (inclusive).
        return s.substring(start, end + 1);
    }
}
