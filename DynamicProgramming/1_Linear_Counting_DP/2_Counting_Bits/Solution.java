class Solution {
    public int[] countBits(int n) {
        int[] setBitCounts = new int[n+1];
        for(int num=1; num<=n; num++) {
            int rightmostSetBitVal = (num & - num);
            int remainingNum = num - rightmostSetBitVal;
            setBitCounts[num] = setBitCounts[remainingNum] + 1;
        }
        return setBitCounts;
    }
}
