class Solution {

    // Pattern: Bit Manipulation → Bit Filling using Zero Bit Positions
    //
    // Goal:
    // Construct the minimum possible ending value after n elements,
    // starting with x, while preserving the required bit properties.

    public long minEnd(int n, int x) {

        long ans = x;

        // We already consider the first element as x
        n--;

        // Index to find the next available zero bit in ans
        int clearBitIdx = 0;

        // Fill bits of n into the zero bit positions of ans
        while (n > 0) {

            // Take the least significant bit of n
            long bit = n & 1;

            // Move to the next zero bit position in ans
            while (((ans >> clearBitIdx) & 1) == 1) {
                clearBitIdx++;
            }

            // Place the bit from n into the zero bit position of ans
            ans = ans | (bit << clearBitIdx);

            // Move to the next bit position
            clearBitIdx++;

            // Shift n to process the next bit
            n >>= 1;
        }

        return ans;
    }
}
