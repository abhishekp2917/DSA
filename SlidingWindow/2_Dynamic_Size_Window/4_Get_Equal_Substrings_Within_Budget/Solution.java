class Solution {
    public int equalSubstring(String s, String t, int maxCost) {

        // We want the longest substring such that
        // total cost of converting s -> t is <= maxCost.
        //
        // Cost at index i = |s[i] - t[i]|
        //
        // Since substring must be contiguous
        // and we maintain a running cost constraint,
        // Sliding Window is ideal.

        int n = s.length();

        int maxLen = 0;

        int cost = 0;        // total cost of current window
        int leftIdx = 0;     // left pointer
        int rightIdx = 0;    // right pointer

        while (rightIdx < n) {

            // Add cost of including current index
            cost += Math.abs(s.charAt(rightIdx) - t.charAt(rightIdx));

            // If cost exceeds maxCost,
            // shrink window from the left
            //
            // Reason:
            // We must maintain total cost <= maxCost
            while (leftIdx <= rightIdx && cost > maxCost) {

                // Remove cost contribution of left character
                cost -= Math.abs(s.charAt(leftIdx) - t.charAt(leftIdx));

                leftIdx++;
            }

            // Now window [leftIdx ... rightIdx] is valid
            // Update maximum length
            maxLen = Math.max(maxLen, (rightIdx - leftIdx + 1));

            // Expand window
            rightIdx++;
        }

        return maxLen;
    }
}
