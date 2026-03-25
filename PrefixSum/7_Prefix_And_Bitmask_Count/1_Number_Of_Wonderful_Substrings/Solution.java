class Solution {

    public long wonderfulSubstrings(String word) {

        // stateFreq[mask] = number of times this parity state has appeared
        //
        // mask is a 10-bit number (for characters 'a' to 'j')
        // Each bit represents whether count of that character is:
        // 0 → even
        // 1 → odd
        int[] stateFreq = new int[1 << 10];

        // Base case:
        // Before processing any character,
        // all counts are even → mask = 0
        //
        // Why?
        // To count substrings starting from index 0
        stateFreq[0] = 1;

        long count = 0;

        // Current parity state
        int stateMask = 0;

        // Traverse string
        for (char ch : word.toCharArray()) {

            // --------------------------------------------------
            // STEP 1: Update state (toggle parity)
            // --------------------------------------------------
            //
            // Toggle the bit corresponding to current character
            //
            // Why XOR?
            // Because:
            // even → odd → even → odd ...
            stateMask ^= (1 << (ch - 'a'));


            // --------------------------------------------------
            // CASE 1: All characters have even count
            // --------------------------------------------------
            //
            // If same state appeared before,
            // substring between them has all even counts
            count += stateFreq[stateMask];


            // --------------------------------------------------
            // CASE 2: Exactly ONE character has odd count
            // --------------------------------------------------
            //
            // We flip each bit once and check if such state existed
            //
            // Why?
            // Because wonderful substring allows
            // at most one character with odd frequency
            for (int i = 0; i < 10; i++) {

                int newState = stateMask ^ (1 << i);

                count += stateFreq[newState];
            }


            // --------------------------------------------------
            // STEP 3: Record current state
            // --------------------------------------------------
            stateFreq[stateMask]++;
        }

        return count;
    }
}