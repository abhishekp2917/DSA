class Solution {

    public int findMaximumXOR(int[] nums) {
        int maxXor = 0;
        Trie trie = new Trie();

        // Process each number one by one.
        // For the current number, the Trie contains all previously processed numbers,
        // so we can find the number that produces the maximum XOR with it.
        for(int num : nums) {
            Trie root = trie;
            Trie check = trie;
            int currXor = 0;

            // Process from the most significant bit to the least significant bit.
            // A higher bit contributes more to the XOR value, so we greedily
            // try to maximize the XOR starting from the highest bit.
            for(int i=31; i>=0; i--) {
                int bit = (num>>i)&1;
                int oppBit = bit^1;

                // Insert the current number into the Trie bit by bit.
                // This makes it available for future numbers.
                if(root.map[bit]==null) root.map[bit] = new Trie();
                root = root.map[bit];

                // To make the current XOR bit equal to 1, we want the
                // opposite bit from the current number.
                // Prefer it whenever such a previously inserted number exists.
                if(check.map[oppBit]!=null) {
                    check = check.map[oppBit];

                    // If opposite bits are selected, this XOR bit becomes 1.
                    currXor |= (1<<i);
                }
                else {
                    // The opposite bit is unavailable, so we must use
                    // the same bit and this XOR bit becomes 0.
                    check = check.map[bit];
                }
            }

            // Keep the best XOR obtained using the current number.
            maxXor = Math.max(maxXor, currXor);
        }

        return maxXor;
    }
}


class Trie {

    // map[0] represents the child for bit 0,
    // map[1] represents the child for bit 1.
    Trie[] map;

    Trie() {
        map = new Trie[2];
    }
}