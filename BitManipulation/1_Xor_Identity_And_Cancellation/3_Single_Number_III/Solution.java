class Solution {

    // Pattern: Bit Manipulation → XOR Partitioning using Lowest Set Bit
    //
    // Key Observations:
    // 1. XORing all numbers gives xorAll = a ^ b,
    //    where a and b are the two unique numbers.
    // 2. Since a != b, xorAll must have at least one set bit.
    // 3. That set bit indicates a position where a and b differ.
    //
    // Intuition:
    // Use the lowest set bit of xorAll to partition numbers into
    // two independent groups. Each group will contain exactly
    // one unique number.
    //
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    public int[] singleNumber(int[] nums) {

        int xorAll = 0;

        // Step 1: XOR all numbers → result is a ^ b
        for (int num : nums) {
            xorAll ^= num;
        }

        // Step 2: Isolate the lowest set bit (rightmost set bit)
        int rightMostSetBit = xorAll & (-xorAll);

        int firstUnique = 0;
        int secondUnique = 0;

        // Step 3: Partition numbers based on the isolated bit
        for (int num : nums) {
            if ((num & rightMostSetBit) == 0) {
                firstUnique ^= num;
            } else {
                secondUnique ^= num;
            }
        }

        return new int[]{firstUnique, secondUnique};
    }
}
