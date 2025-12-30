class Solution {
    public int minOperations(int n) {
        int opCount = 0;
        while(n>0) {
            if((n&3)==3) {
                n++;
                opCount++;
            }
            if((n&1)==1) opCount++;
            n >>= 1;
        }
        return opCount;
    }
}


