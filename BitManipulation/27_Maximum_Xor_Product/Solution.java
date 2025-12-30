class Solution {
    public int maximumXorProduct(long a, long b, int n) {
        long bitMask = (1L<<n)-1;
        long x = (~(a|b))&bitMask;
        long xor = a^b;
        for(int bit=n-1; bit>=0; bit--) {
            if(((xor>>bit)&1)==0) continue;
            long num1 = a^x;
            long num2 = b^x;
            if(num1>num2) {
                if(((a>>bit)&1)==1) x |= (1L<<bit);
            }
            else if(num2>num1) {
                if(((b>>bit)&1)==1) x |= (1L<<bit);
            }
        }
        return (int)((((a^x)%1000000007)*((b^x)%1000000007))%1000000007);
    }
}


