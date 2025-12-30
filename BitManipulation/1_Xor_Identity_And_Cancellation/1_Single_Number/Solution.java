class Solution {

    // Pattern: Bit Manipulation → XOR Identity & Cancellation
    //
    // Key Properties of XOR used here:
    // 1. a ^ a = 0        (a number XORed with itself cancels out)
    // 2. a ^ 0 = a        (XOR with 0 keeps the number unchanged)
    // 3. XOR is commutative and associative
    //
    // Intuition:
    // Since every number appears twice except one,
    // XORing all numbers together will cancel out the duplicates,
    // leaving only the unique number.
    //
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    public int singleNumber(int[] nums) {

        int xorResult = 0; // Acts as an accumulator for XOR operation

        // XOR all elements in the array
        for (int num : nums) {
            xorResult ^= num;
        }

        // The remaining value is the single (non-duplicated) number
        return xorResult;
    }
}
