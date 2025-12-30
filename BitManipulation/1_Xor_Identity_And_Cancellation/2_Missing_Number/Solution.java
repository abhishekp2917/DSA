class Solution {

    // Pattern: Bit Manipulation → XOR Aggregation
    //
    // Key XOR Properties used:
    // 1. a ^ a = 0          (cancellation)
    // 2. a ^ 0 = a
    // 3. XOR is commutative and associative
    //
    // Intuition:
    // If we XOR all numbers from 0 to n and also XOR all elements
    // present in the array, every number that appears in both
    // places will cancel out.
    //
    // The only number left after cancellation is the missing number.
    //
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    public int missingNumber(int[] nums) {

        int n = nums.length;
        int xorResult = 0;

        // XOR all indices from 0 to n
        for (int i = 0; i <= n; i++) {
            xorResult ^= i;
        }

        // XOR all values present in the array
        for (int num : nums) {
            xorResult ^= num;
        }

        // Remaining value is the missing number
        return xorResult;
    }
}
