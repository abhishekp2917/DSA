import java.util.HashMap;
import java.util.Map;

class Solution {

    // Pattern: Bit Manipulation → Subsequence Enumeration + Bitmask Disjointness
    //
    // Goal:
    // Find two non-overlapping palindromic subsequences
    // such that the product of their lengths is maximized.

    public int maxProduct(String s) {

        int maxProd = 0;

        // Map stores: bitmask -> length of palindrome subsequence
        Map<Integer, Integer> palindromeMap = new HashMap<>();

        // Build all palindromic subsequences using recursion
        buildPalindrome(s, 0, 0, new StringBuilder(), palindromeMap);

        // Compare every pair of palindromic subsequences
        for (Integer mask1 : palindromeMap.keySet()) {
            for (Integer mask2 : palindromeMap.keySet()) {

                // If subsequences do not overlap
                if ((mask1 & mask2) == 0) {

                    // Update maximum product
                    maxProd = Math.max(
                        maxProd,
                        palindromeMap.get(mask1) * palindromeMap.get(mask2)
                    );
                }
            }
        }

        return maxProd;
    }

    // Recursively builds all subsequences and tracks palindromic ones
    private void buildPalindrome(
        String s,
        int ptr,
        int bitmask,
        StringBuilder str,
        Map<Integer, Integer> palindromeMap
    ) {

        // If current subsequence is a palindrome,
        // store its length against the bitmask
        if (str.length() > 0 && isPalindrome(str)) {
            palindromeMap.put(bitmask, str.length());
        }

        // Base case: end of string
        if (ptr == s.length()) return;

        // Include current character
        str.append(s.charAt(ptr));
        buildPalindrome(s, ptr + 1, bitmask | (1 << ptr), str, palindromeMap);

        // Backtrack
        str.deleteCharAt(str.length() - 1);

        // Exclude current character
        buildPalindrome(s, ptr + 1, bitmask, str, palindromeMap);
    }

    // Checks if a string is a palindrome
    private boolean isPalindrome(StringBuilder s) {
        for (int i = 0; i <= s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
