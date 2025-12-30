class Solution {

    // Pattern: Bit Manipulation → XOR Prefix State + Frequency Counting
    //
    // Goal:
    // Count substrings where at most one character appears an odd number of times.
    // Only characters 'a' to 'j' are considered.

    public long wonderfulSubstrings(String word) {

        // stateFreq[mask] stores how many times this parity state has appeared
        int[] stateFreq = new int[(int) Math.pow(2, 10)];

        // Base state: all characters have even count before processing starts
        stateFreq[0] = 1;

        long count = 0;

        // stateMask represents parity (even/odd) of characters 'a' to 'j'
        int stateMask = 0;

        // Traverse characters of the string
        for (char ch : word.toCharArray()) {

            // Toggle parity of current character
            stateMask ^= (1 << (ch - 'a'));

            // Case 1: all characters have even count
            count += stateFreq[stateMask];

            // Case 2: exactly one character has odd count
            for (int i = 0; i < 10; i++) {
                int newState = stateMask ^ (1 << i);
                count += stateFreq[newState];
            }

            // Record current state
            stateFreq[stateMask]++;
        }

        return count;
    }
}
