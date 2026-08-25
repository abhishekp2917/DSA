import java.util.HashSet;
import java.util.Set;

class Solution {

    // Pattern: Bit Manipulation → Greedy Bit Masking + Prefix Matching
    //
    // Goal:
    // Find the maximum XOR of any two numbers in the array.
    //
    // Strategy:
    // Build the result greedily from the most significant bit
    // to the least significant bit.

    public int findMaximumXOR(int[] nums) {

        int maxXor = 0;   // Stores the maximum XOR found so far
        int bitMask = 0;  // Used to keep prefixes up to current bit

        // Iterate from the most significant bit to the least
        for (int i = 31; i >= 0; i--) {

            // Extend the mask to include the current bit
            bitMask |= (1 << i);

            // Store prefixes of all numbers masked till current bit
            Set<Integer> masks = new HashSet<>();
            for (int num : nums) {
                masks.add(num & bitMask);
            }

            // Try to set the current bit in the result
            int tempXor = maxXor | (1 << i);

            // Check if there exist two prefixes
            // whose XOR equals tempXor
            for (int mask : masks) {
                if (masks.contains(mask ^ tempXor)) {
                    maxXor = tempXor; // Current bit can be set
                    break;
                }
            }
        }

        return maxXor;
    }
}
