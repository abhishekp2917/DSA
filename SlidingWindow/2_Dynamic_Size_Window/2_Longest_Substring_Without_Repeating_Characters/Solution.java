import java.util.HashSet;
import java.util.Set;

class Solution {
    public int lengthOfLongestSubstring(String s) {

        // We need the length of the longest substring
        // that contains NO repeating characters.

        // Since substring must be contiguous
        // and we dynamically expand/shrink based on duplicates,
        // we use Sliding Window + HashSet.

        int maxLen = 0;

        // This set stores characters currently inside the window
        Set<Character> chars = new HashSet<>();

        int i = 0;  // left pointer
        int j = 0;  // right pointer

        while (j < s.length()) {

            char ch = s.charAt(j);

            // If character already exists in window,
            // we must shrink window from the left
            //
            // Reason:
            // A valid substring cannot contain duplicate characters.
            while (chars.contains(ch)) {

                // Remove the leftmost character
                // until duplicate is removed
                chars.remove(s.charAt(i));
                i++;
            }

            // Now 'ch' is guaranteed to be unique inside window
            chars.add(ch);

            // Update maximum length
            //
            // chars.size() == (j - i + 1)
            // because window contains only unique characters
            maxLen = Math.max(maxLen, chars.size());

            // Expand window
            j++;
        }

        return maxLen;
    }
}
