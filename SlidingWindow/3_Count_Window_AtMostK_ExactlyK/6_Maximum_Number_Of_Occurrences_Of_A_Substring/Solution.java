import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {

        // We need the maximum frequency of any substring
        // such that:
        //   1) Length is between minSize and maxSize
        //   2) Number of distinct characters <= maxLetters

        // Important Observation:
        // Even though length can go up to maxSize,
        // the answer will always come from substrings of length minSize.
        //
        // Why?
        // If a longer substring appears X times,
        // then its prefix of length minSize
        // will appear at least X times as well.
        //
        // So we only check substrings of size = minSize.

        Map<String, Integer> freqMap = new HashMap<>();

        // To track distinct characters efficiently in O(1)
        int[] charCount = new int[26];

        int distinct = 0;   // number of distinct characters in current window
        int start = 0;

        int maxFreq = 0;

        // Sliding window over string
        for (int end = 0; end < s.length(); end++) {

            // Add new character to window
            int idx = s.charAt(end) - 'a';

            if (charCount[idx] == 0) {
                distinct++;     // new distinct character
            }

            charCount[idx]++;

            // Keep window size fixed to minSize
            //
            // If window becomes larger than minSize,
            // shrink from left.
            if ((end - start + 1) > minSize) {

                int startIdx = s.charAt(start) - 'a';

                charCount[startIdx]--;

                // If frequency becomes zero,
                // one distinct character is removed
                if (charCount[startIdx] == 0) {
                    distinct--;
                }

                start++;
            }

            // If window size is exactly minSize
            // and distinct characters condition is satisfied
            if ((end - start + 1) == minSize &&
                distinct <= maxLetters) {

                // Extract substring
                String substr = s.substring(start, end + 1);

                // Update its frequency
                int freq = freqMap.getOrDefault(substr, 0) + 1;

                freqMap.put(substr, freq);

                // Track maximum frequency
                maxFreq = Math.max(maxFreq, freq);
            }
        }

        return maxFreq;
    }
}
