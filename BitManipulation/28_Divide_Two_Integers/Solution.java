class Solution {
    public int divide(int dividend, int divisor) {
        // Handle edge case
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        int ans = 0;
        boolean isNegative = (dividend < 0) ^ (divisor < 0);
        long numerator = abs(dividend);
        long denominator = abs(divisor);
        for(int bit=31; bit>=0; bit--) {
            if(numerator>=(denominator<<bit)) {
                numerator -= (denominator<<bit);
                ans += (1<<bit);
            }
        }
        return isNegative?-ans:ans;
    }

    private long abs(long n) {
        long mask = (n>>31);
        return (n^mask)-mask;
    }
}
