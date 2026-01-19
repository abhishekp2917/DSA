class Solution {
    public long findMaximumNumber(long k, int x) {
        long ans = 0;
        long left = 0;
        long right = (long)Math.pow(2, 50)-1;
        while(left<=right) {
            long mid = (left + right)/2;
            if(getSetBitFrom1ToN(mid, x)<=k) {
                ans = mid;
                left = mid+1;
            }
            else right = mid-1;
        }
        return ans;
    }

    private long getSetBitFrom1ToN(long n, int x) {
        long count = 0;
        for(int i=x-1; i<50; i+=x) {
            long window = 1L<<(i+1);
            long setBitPerWindow = 1L<<i;
            long quotient = ((n+1)/window)*setBitPerWindow;
            long remainder = (((n+1)%window)<=setBitPerWindow)?0:((n+1)%window)-setBitPerWindow;
            count +=  quotient + remainder;
        }
        return count;
    }
}



