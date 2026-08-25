class Solution {

    // Pattern: Bit Manipulation → Per-Bit Aggregation with Running Contribution
    //
    // Goal:
    // Compute the total Hamming distance among all pairs of numbers in the array.

    public int totalHammingDistance(int[] nums) {

        int sum = 0;

        // Iterate over each bit position from 0 to 31
        for (int i = 0; i < 32; i++) {

            int setBitCount = 0;

            // Traverse numbers from left to right
            for (int j = 0; j < nums.length; j++) {

                // If current bit is 0 in nums[j],
                // it forms a differing pair with all previous set bits
                if (((nums[j] >> i) & 1) == 0) {
                    sum += setBitCount;
                }
                // If current bit is 1 in nums[j],
                // it forms a differing pair with all previous unset bits
                else {
                    sum += (j - setBitCount);
                }

                // Update count of set bits seen so far
                setBitCount += ((nums[j] >> i) & 1);
            }
        }

        return sum;
    }
}
