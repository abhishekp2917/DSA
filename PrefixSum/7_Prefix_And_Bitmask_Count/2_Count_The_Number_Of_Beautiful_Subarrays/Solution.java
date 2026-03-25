class Solution {

    public long beautifulSubarrays(int[] nums) {

        long ans = 0;

        // xorCount[x] = number of times prefix XOR = x has appeared
        //
        // Why array instead of HashMap?
        // Because constraints allow XOR values up to 2^20,
        // so direct indexing is faster.
        int[] xorCount = new int[(1 << 20)];

        // Base case:
        // prefix XOR = 0 occurs once before starting
        //
        // Why?
        // To count subarrays starting from index 0
        xorCount[0] = 1;

        int xor = 0;

        for (int num : nums) {

            // --------------------------------------------------
            // STEP 1: Update prefix XOR
            // --------------------------------------------------
            xor ^= num;

            // --------------------------------------------------
            // STEP 2: Count valid subarrays
            // --------------------------------------------------
            //
            // If prefix XOR repeats:
            //
            // xor[j] == xor[i]
            //
            // ⇒ subarray XOR = 0
            //
            // Why?
            // xor[j] ^ xor[i] = 0
            //
            // ⇒ subarray from (i+1 → j) is valid
            ans += xorCount[xor];

            // --------------------------------------------------
            // STEP 3: Record current XOR
            // --------------------------------------------------
            xorCount[xor]++;
        }

        return ans;
    }
}