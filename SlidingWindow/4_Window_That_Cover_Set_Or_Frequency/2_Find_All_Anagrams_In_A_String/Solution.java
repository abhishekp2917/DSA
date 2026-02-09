import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findAnagrams(String s, String p) {

        // We need to find all starting indices of substrings in s
        // that are anagrams of p.

        // An anagram means:
        // Same character frequencies as p.

        List<Integer> ans = new ArrayList<>();

        // If s is smaller than p,
        // no anagram possible.
        if (s.length() < p.length()) return ans;

        int n = s.length();

        // Frequency arrays
        int[] sFreqMap = new int[26];
        int[] pFreqMap = new int[26];

        // Count distinct characters in s-window and p
        int sDistinctCharCount = 0;
        int pDistinctCharCount = 0;

        // Build frequency map for p
        for (char ch : p.toCharArray()) {

            if (pFreqMap[ch - 'a'] == 0)
                pDistinctCharCount++;

            pFreqMap[ch - 'a']++;
        }

        int start = 0, end = 0;

        while (end < n) {

            char ch = s.charAt(end);

            // If character not present in p,
            // we cannot form an anagram including this character.
            //
            // So reset window.
            if (pFreqMap[ch - 'a'] == 0) {

                // Clear s frequency map
                for (int i = 0; i < 26; i++)
                    sFreqMap[i] = 0;

                start = end + 1;
                sDistinctCharCount = 0;
                end++;
                continue;
            }

            // Include current character in window
            sFreqMap[ch - 'a']++;

            // If frequency matches exactly with p,
            // increase matching distinct count
            if (sFreqMap[ch - 'a'] == pFreqMap[ch - 'a'])
                sDistinctCharCount++;

            // If frequency exceeds allowed count,
            // shrink from left
            while (start <= end &&
                    sFreqMap[ch - 'a'] > pFreqMap[ch - 'a']) {

                char startCh = s.charAt(start);

                // If removing this breaks a match,
                // reduce match count
                if (sFreqMap[startCh - 'a'] ==
                        pFreqMap[startCh - 'a'])
                    sDistinctCharCount--;

                sFreqMap[startCh - 'a']--;
                start++;
            }

            // If all distinct characters match,
            // we found an anagram
            if (sDistinctCharCount == pDistinctCharCount)
                ans.add(start);

            end++;
        }

        return ans;
    }
}
