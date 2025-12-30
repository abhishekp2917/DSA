class Solution {

    // Pattern: Bit Manipulation → Per Bit Aggregation
    //
    // Idea:
    // For each bit position (0 to 30),
    // count how many numbers have that bit set.
    // If the count is >= k, set that bit in the answer.

    public int findKOr(int[] nums, int k) {

        // setBitCount[i] stores how many numbers
        // have the i-th bit set
        int[] setBitCount = new int[31];

        int ans = 0;

        // Iterate over each bit position
        for (int i = 0; i < 31; i++) {

            // Count how many numbers have the i-th bit set
            for (int num : nums) {
                setBitCount[i] += isKthBitSet(num, i);
            }

            // If at least k numbers have this bit set,
            // include it in the answer
            if (setBitCount[i] >= k) {
                ans = setKthBit(ans, i);
            }
        }

        return ans;
    }

    // Sets the k-th bit of number n
    private int setKthBit(int n, int k) {
        int mask = (1 << k);
        return n | mask;
    }

    // Returns 1 if k-th bit is set, otherwise 0
    private int isKthBitSet(int n, int k) {
        return (n >> k) & 1;
    }
}
