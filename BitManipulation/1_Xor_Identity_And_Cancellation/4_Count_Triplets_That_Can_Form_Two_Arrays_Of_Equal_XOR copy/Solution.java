class Solution {

    // Pattern: Bit Manipulation → XOR Prefix + Equal Prefix Pairing
    //
    // Key Insight:
    // If XOR from index i to j-1 == XOR from j to k,
    // then prefixXor[i] == prefixXor[k + 1]
    //
    // Because:
    // XOR(i, j-1) = prefixXor[j] ^ prefixXor[i]
    // XOR(j, k)   = prefixXor[k + 1] ^ prefixXor[j]
    //
    // Equating them cancels prefixXor[j], leaving:
    // prefixXor[i] == prefixXor[k + 1]
    //
    // For every such (i, k), all j where i < j ≤ k are valid.
    //
    // Time Complexity: O(n^2)
    // Space Complexity: O(n)

    public int countTriplets(int[] arr) {

        int n = arr.length;
        int[] prefixXor = new int[n + 1];

        // Build prefix XOR array
        for (int i = 0; i < n; i++) {
            prefixXor[i + 1] = prefixXor[i] ^ arr[i];
        }

        int tripletCount = 0;

        // Find all (i, k) pairs where prefixXor[i] == prefixXor[k + 1]
        for (int i = 0; i < n; i++) {
            for (int k = i + 1; k < n; k++) {
                if (prefixXor[i] == prefixXor[k + 1]) {
                    // For each valid (i, k), there are (k - i) valid j values
                    tripletCount += (k - i);
                }
            }
        }

        return tripletCount;
    }
}
