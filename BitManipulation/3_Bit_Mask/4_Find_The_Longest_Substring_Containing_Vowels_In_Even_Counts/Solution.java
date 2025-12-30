import java.util.HashMap;
import java.util.Map;

class Solution {

    // Pattern: Bit Manipulation → XOR Prefix Mask + First Occurrence Tracking
    //
    // Goal:
    // Find the longest substring where each vowel appears an even number of times.

    public int findTheLongestSubstring(String s) {

        int maxLen = 0;

        // Map stores: XOR mask -> first index where this mask appeared
        Map<Integer, Integer> xorMap = new HashMap<>();

        // Base case: XOR mask 0 before processing any character
        xorMap.put(0, -1);

        int xor = 0;

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            // Toggle the corresponding bit if the character is a vowel
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                xor ^= (1 << (ch - 'a'));
            }

            // If this XOR mask was seen before, update maximum length
            if (xorMap.containsKey(xor)) {
                maxLen = Math.max(maxLen, i - xorMap.get(xor));
            }
            // Otherwise, record the first occurrence of this mask
            else {
                xorMap.put(xor, i);
            }
        }

        return maxLen;
    }
}
