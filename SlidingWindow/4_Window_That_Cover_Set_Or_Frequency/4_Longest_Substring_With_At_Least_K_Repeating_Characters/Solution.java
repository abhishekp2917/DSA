class Solution {

    public int longestSubstring(String s, int k) {

        // We need the longest substring where
        // every character appears at least k times.

        // Key idea:
        // Instead of directly solving for all substrings,
        // we fix the number of distinct characters in the window.

        // Why?
        // Because a valid substring must have:
        //   - Some number of distinct characters
        //   - Each of them appearing at least k times
        //
        // So we try all possible distinct counts (1 to 26).

        int maxLen = 0;

        for (int unqChars = 1; unqChars <= 26; unqChars++) {

            maxLen = Math.max(maxLen,
                    getSubstringLen(s, k, unqChars));
        }

        return maxLen;
    }

    private int getSubstringLen(String s, int k, int unqChars) {

        int maxLen = 0;

        int start = 0;
        int end = 0;

        int[] charCount = new int[26];

        // reqChars tracks how many distinct characters
        // still need to reach frequency k.
        int reqChars = unqChars;

        while (end < s.length()) {

            char ch = s.charAt(end);

            // If this character is newly added,
            // reduce remaining distinct slots.
            if (charCount[ch - 'a'] == 0)
                unqChars--;

            charCount[ch - 'a']++;

            // If this character just reached k,
            // it now satisfies the requirement.
            if (charCount[ch - 'a'] == k)
                reqChars--;

            // If distinct count exceeds allowed,
            // shrink window.
            while (start <= end && unqChars < 0) {

                char startCh = s.charAt(start);

                // If removing breaks k condition,
                // increase reqChars.
                if (charCount[startCh - 'a'] == k)
                    reqChars++;

                charCount[startCh - 'a']--;

                // If character completely removed,
                // increase allowed distinct count.
                if (charCount[startCh - 'a'] == 0)
                    unqChars++;

                start++;
            }

            // If:
            //   - exactly required number of distinct chars
            //   - all of them appear at least k times
            if (unqChars == 0 && reqChars == 0)
                maxLen = Math.max(maxLen,
                        end - start + 1);

            end++;
        }

        return maxLen;
    }
}
