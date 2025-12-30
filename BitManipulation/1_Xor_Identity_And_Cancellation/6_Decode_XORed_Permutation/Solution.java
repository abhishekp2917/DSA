class Solution {

    // Pattern: Bit Manipulation → XOR Aggregation + Prefix Reconstruction
    //
    // Key Observations:
    // 1. perm is a permutation of numbers from 1 to n.
    // 2. encoded[i] = perm[i] ^ perm[i + 1]
    // 3. XORing all numbers from 1 to n gives totalXor.
    //
    // Intuition:
    // By cleverly combining XORs of encoded elements at odd indices
    // with totalXor, we can derive the first element of perm.
    //
    // Once perm[0] is known, the rest of the permutation
    // can be reconstructed sequentially.
    //
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    public int[] decode(int[] encoded) {

        int n = encoded.length + 1;

        int totalXor = 0;
        // XOR of all numbers from 1 to n
        for (int i = 1; i <= n; i++) {
            totalXor ^= i;
        }

        int oddIndexXor = 0;
        // XOR encoded values at odd indices
        for (int i = 1; i < encoded.length; i += 2) {
            oddIndexXor ^= encoded[i];
        }

        // First element of perm
        int firstElement = totalXor ^ oddIndexXor;

        int[] perm = new int[n];
        perm[0] = firstElement;

        // Reconstruct the remaining permutation
        for (int i = 0; i < encoded.length; i++) {
            perm[i + 1] = perm[i] ^ encoded[i];
        }

        return perm;
    }
}
