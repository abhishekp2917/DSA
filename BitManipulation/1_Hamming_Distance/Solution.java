class Solution {

    public int hammingDistance(int x, int y) {
        int xor = x ^ y;
        int diffCount = 0;
        while(xor!=0) {
            diffCount++;
            xor = xor & (xor-1);
        }
        return diffCount;
    }
}