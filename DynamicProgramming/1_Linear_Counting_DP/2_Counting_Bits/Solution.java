class Solution {

    public int[] countBits(int n) {

        // setBitCounts[num]:
        // stores number of set bits (1s)
        // present in the binary representation of num.
        int[] setBitCounts = new int[n+1];

        // Process every number independently,
        // while reusing answers of smaller numbers.
        for(int num=1; num<=n; num++) {

            // Extract value of the rightmost set bit.
            //
            // Example:
            //
            // num = 12 (1100)
            // rightmostSetBitVal = 4 (0100)
            //
            // num = 10 (1010)
            // rightmostSetBitVal = 2 (0010)
            int rightmostSetBitVal = (num & -num);

            // Remove the rightmost set bit.
            //
            // Since rightmostSetBitVal contains exactly
            // one set bit, subtracting it clears only
            // that bit without affecting higher bits.
            //
            // Example:
            //
            // 1100 (12)
            //-0100 (4)
            //----------
            // 1000 (8)
            //
            // Example:
            //
            // 1010 (10)
            //-0010 (2)
            //----------
            // 1000 (8)
            int remainingNum = num - rightmostSetBitVal;

            // Current number has:
            //
            // 1 (removed set bit)
            // +
            // set bits remaining after removing it.
            //
            // DP Relation:
            //
            // bits(num) =
            // bits(num without rightmost set bit) + 1
            setBitCounts[num] = setBitCounts[remainingNum] + 1;
        }

        return setBitCounts;
    }
}