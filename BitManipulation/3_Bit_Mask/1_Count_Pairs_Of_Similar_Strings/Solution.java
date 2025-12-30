class Solution {

    // Pattern: Bit Manipulation → Bit Mask Representation + Pair Comparison
    //
    // Goal:
    // Count how many pairs of words have exactly the same set of characters.

    public int similarPairs(String[] words) {

        int count = 0;

        // bitmask[i] represents the character set of words[i]
        int[] bitmask = new int[words.length];

        // Convert each word into a bitmask
        for (int i = 0; i < words.length; i++) {

            String word = words[i];
            int mask = 0;

            // Set the bit corresponding to each character in the word
            for (char ch : word.toCharArray()) {
                mask = mask | (1 << (ch - 'a'));
            }

            bitmask[i] = mask;
        }

        // Compare all pairs of bitmasks
        for (int i = 0; i < words.length - 1; i++) {
            for (int j = i + 1; j < words.length; j++) {

                // If XOR is 0, both masks are identical
                count += ((bitmask[i] ^ bitmask[j]) == 0) ? 1 : 0;
            }
        }

        return count;
    }
}
