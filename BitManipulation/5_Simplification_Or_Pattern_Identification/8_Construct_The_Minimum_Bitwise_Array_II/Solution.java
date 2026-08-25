import java.util.List;

class Solution {

    // Pattern: Bit Manipulation → Lowest Clear Bit Manipulation
    //
    // Goal:
    // For each number, construct the minimum possible value that satisfies
    // the problem's bitwise constraints.

    public int[] minBitwiseArray(List<Integer> nums) {

        int[] ans = new int[nums.size()];

        // Process each number independently
        for (int i = 0; i < nums.size(); i++) {

            int num = nums.get(i);

            // If the number is even, it is impossible to construct
            // a valid value under the given constraints
            if ((num & 1) == 0) {
                ans[i] = -1;
            }
            else {

                // Find the lowest set bit in (num + 1),
                // which corresponds to the lowest clear bit in num
                int leastClearBit = (num + 1) & (-(num + 1));

                // Flip the bit just below the lowest clear bit
                ans[i] = num ^ (leastClearBit >> 1);
            }
        }

        return ans;
    }
}
