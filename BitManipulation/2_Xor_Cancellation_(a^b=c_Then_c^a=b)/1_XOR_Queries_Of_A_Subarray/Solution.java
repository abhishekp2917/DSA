class Solution {

    // Pattern: Bit Manipulation → XOR Prefix / XOR Accumulation
    //
    // Key XOR Properties:
    // 1. a ^ a = 0
    // 2. a ^ 0 = a
    // 3. XOR is associative and commutative
    //
    // Intuition:
    // By maintaining a prefix XOR array, we can compute
    // the XOR of any subarray [L, R] in O(1) time.
    //
    // XOR[L..R] = prefix[R] ^ prefix[L - 1]
    //
    // Time Complexity: O(n + q)
    // Space Complexity: O(n)

    public int[] xorQueries(int[] arr, int[][] queries) {

        int n = arr.length;
        int[] prefixXor = new int[n];

        // Step 1: Build prefix XOR array
        prefixXor[0] = arr[0];
        for (int i = 1; i < n; i++) {
            prefixXor[i] = prefixXor[i - 1] ^ arr[i];
        }

        int[] result = new int[queries.length];

        // Step 2: Answer each query using prefix XOR
        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];

            if (left == 0) {
                result[i] = prefixXor[right];
            } else {
                result[i] = prefixXor[right] ^ prefixXor[left - 1];
            }
        }

        return result;
    }
}
