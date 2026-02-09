class Solution {

    public long validSubstringCount(String word1, String word2) {

        // We need substrings of word1 that contain
        // ALL characters of word2 with at least same frequency.

        // Instead of directly counting valid substrings,
        // we count INVALID ones (those that do NOT fully contain word2)
        // and subtract from total substrings.

        long n = word1.length();

        // Total substrings in word1
        long totalSubstring = n * (n + 1) / 2;

        // Substrings that DO NOT fully contain word2
        return totalSubstring - countAtmost(word1, word2);
    }

    private long countAtmost(String word1, String word2) {

        // This function counts substrings that
        // DO NOT fully satisfy the requirement
        // (i.e., missing at least one required character frequency)

        long n = word1.length();
        long count = 0;

        // Frequency arrays for quick lookup
        int[] word1FreqMap = new int[26];
        int[] word2FreqMap = new int[26];

        // Count distinct characters required from word2
        int word2UnqChars = 0;

        // Number of characters currently matching required frequency
        int sameCountChars = 0;

        // Build frequency map for word2
        for (char ch : word2.toCharArray()) {

            if (word2FreqMap[ch - 'a'] == 0) {
                word2UnqChars++;
            }

            word2FreqMap[ch - 'a']++;
        }

        int start = 0, end = 0;

        while (end < n) {

            char ch = word1.charAt(end);

            // Add current character to window
            word1FreqMap[ch - 'a']++;

            // If this character now satisfies required frequency,
            // increase match counter
            if (word1FreqMap[ch - 'a'] == word2FreqMap[ch - 'a']
                    && word2FreqMap[ch - 'a'] != 0) {

                sameCountChars++;
            }

            // If all required characters are satisfied,
            // shrink window to make it INVALID again
            //
            // Reason:
            // We want to count substrings that are NOT fully valid.
            while (start <= end && sameCountChars == word2UnqChars) {

                char leftCh = word1.charAt(start);

                // If removing this character breaks a requirement,
                // decrease match counter
                if (word1FreqMap[leftCh - 'a'] ==
                        word2FreqMap[leftCh - 'a']
                        && word2FreqMap[leftCh - 'a'] != 0) {

                    sameCountChars--;
                }

                word1FreqMap[leftCh - 'a']--;
                start++;
            }

            // Now window [start ... end] is INVALID
            // (does not fully contain word2)

            // All substrings ending at 'end'
            // and starting from start to end are invalid
            count += (end - start + 1);

            end++;
        }

        return count;
    }
}
