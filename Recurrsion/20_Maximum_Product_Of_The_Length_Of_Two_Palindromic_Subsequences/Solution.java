class Solution {
    public int distributeCookies(int[] cookies, int k) {
        return recurrsion(cookies, 0, new int[k], 0);
    }

    private int recurrsion(int[] cookies, int ptr, int[] distribution, int currMax) {
        if(ptr==cookies.length) {
            return currMax;
        }
        int min = Integer.MAX_VALUE;
        for(int i=0; i<distribution.length; i++) {
            distribution[i] += cookies[ptr];
            min = Math.min(min, recurrsion(cookies, ptr+1, distribution, Math.max(currMax, distribution[i])));
            distribution[i] -= cookies[ptr];
        }
        return min;
    }
}