class Solution {

    // Pattern: Bit Manipulation → XOR Identity & Cancellation
    //
    // Goal:
    // Compute the XOR beauty value of the array.

    public int xorBeauty(int[] nums) {

        int xor = 0;

        // XOR all elements of the array
        for (int num : nums) {
            xor ^= num;
        }

        return xor;
    }
}
