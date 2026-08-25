class Solution {

    // Pattern: Bit Manipulation → Common Prefix Extraction
    //
    // Goal:
    // Compute the bitwise AND of all numbers in the range [left, right].

    public int rangeBitwiseAnd(int left, int right) {

        int count = 0;

        // Right shift both numbers until they become equal
        // This removes differing least significant bits
        while (left != right) {
            left >>= 1;
            right >>= 1;
            count++;
        }

        // Restore the common prefix by shifting left
        left <<= count;

        return left;
    }
}
