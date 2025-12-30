class Solution {
    public int[] decode(int[] encoded) {
        int n = encoded.length+1;
        int[] ans = new int[n];
        int xor = 0;
        for(int num=1; num<=n; num++) xor ^= num;
        for(int i=1; i<encoded.length; i+=2) xor ^= encoded[i];
        ans[0] = xor;
        for(int i=1; i<n; i++) {
            ans[i] = ans[i-1] ^ encoded[i-1];
        }
        return ans;
    }
}
