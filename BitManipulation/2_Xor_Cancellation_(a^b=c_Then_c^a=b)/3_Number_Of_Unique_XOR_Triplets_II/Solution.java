class Solution {

    // Pattern: Bit Manipulation → XOR Aggregation over Value Space
    //
    // Core Idea:
    // - First compute all possible XOR results of pairs (a ^ b)
    // - Then check how many values can be formed as:
    //   (a ^ b) ^ c
    //
    // Important:
    // - We do NOT enumerate triplets directly
    // - We work in XOR result space (0 to 2^11 - 1)
    //
    // Time Complexity:
    // - Pair XOR generation: O(n^2)
    // - Result checking: O(2048 * n)
    //
    // Space Complexity: O(2048)

    public int uniqueXorTriplets(int[] nums) {

        int count = 0;

        // XOR result space (since nums[i] <= 2000 < 2^11)
        boolean[] pairXorExists = new boolean[1 << 11];

        // Step 1: Mark all possible XORs of pairs (a ^ b)
        for (int first : nums) {
            for (int second : nums) {
                pairXorExists[first ^ second] = true;
            }
        }

        // Step 2: For each possible XOR result,
        // check if it can be formed as (a ^ b) ^ c
        for (int xorResult = 0; xorResult < (1 << 11); xorResult++) {

            for (int num : nums) {
                if (pairXorExists[xorResult ^ num]) {
                    count++;
                    break; // Count this result only once
                }
            }
        }

        return count;
    }
}
