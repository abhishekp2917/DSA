class Solution {
    public int characterReplacement(String s, int k) {

        // We need the longest substring where we can replace
        // at most k characters to make all characters the same.

        // Key idea:
        // In any window, if we know the most frequent character count,
        // then:
        //
        //   replacements needed =
        //        window_size - maxFreq
        //
        // If replacements_needed <= k → window is valid.

        int n = s.length();

        int maxLen = 0;

        // maxFreq stores the frequency of the MOST frequent character
        // inside the current window.
        int maxFreq = 0;

        // Since only uppercase letters A-Z
        int[] charFreq = new int[26];

        int leftIdx = 0;
        int rightIdx = 0;

        while (rightIdx < n) {

            char ch = s.charAt(rightIdx);

            // Add current character to window
            charFreq[ch - 'A']++;

            // Update maximum frequency inside this window
            //
            // Reason:
            // We want to know which character is dominating the window
            maxFreq = Math.max(maxFreq, charFreq[ch - 'A']);

            // If replacements required exceed k,
            // shrink window from the left.
            //
            // replacements = window_size - maxFreq
            if ((rightIdx - leftIdx + 1) - maxFreq > k) {

                // Remove left character from window
                charFreq[s.charAt(leftIdx) - 'A']--;

                leftIdx++;
            }

            // Update maximum valid window length
            maxLen = Math.max(maxLen, (rightIdx - leftIdx + 1));

            // Expand window
            rightIdx++;
        }

        return maxLen;
    }
}
