import java.util.HashMap;
import java.util.Map;

class Solution {

    public int similarPairs(String[] words) {
        int n = words.length;
        int count = 0;

        // Map each character-set bitmask to the number of words
        // encountered so far having exactly the same set of characters.
        Map<Integer, Integer> bitmaskFreq = new HashMap<>();

        for (int i=0; i<n; i++) {
            String word = words[i];
            int mask = 0;

            // Represent the set of distinct characters in the word using
            // a 26-bit integer.
            //
            // Bit 0 -> 'a'
            // Bit 1 -> 'b'
            // ...
            // Bit 25 -> 'z'
            //
            // Since similar words only care about which characters exist,
            // repeated occurrences of the same character do not matter.
            for (char ch : word.toCharArray()) {
                int charBitPos = ch-'a';

                // Set the bit corresponding to this character.
                // OR keeps the bit set even if the character appears again.
                mask = mask | (1 << charBitPos);
            }

            // Every previously seen word with the same mask is similar
            // to the current word, so all of them form a new valid pair
            // with the current word.
            count += bitmaskFreq.getOrDefault(mask, 0);

            // Record this word so that future words with the same
            // character set can form pairs with it.
            bitmaskFreq.put(
                mask,
                bitmaskFreq.getOrDefault(mask, 0) + 1
            );
        }

        return count;
    }
}