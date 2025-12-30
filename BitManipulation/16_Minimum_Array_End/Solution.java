class Solution {
    public long minEnd(int n, int x) {
        long ans = x;
        n--;
        int clearBitIdx = 0;
        while(n>0) {
            long bit = n&1;
            while(((ans>>clearBitIdx)&1)==1) clearBitIdx++;
            ans = ans | (bit<<clearBitIdx);
            clearBitIdx++;
            n >>= 1; 
        }
        return ans;
    }
}


