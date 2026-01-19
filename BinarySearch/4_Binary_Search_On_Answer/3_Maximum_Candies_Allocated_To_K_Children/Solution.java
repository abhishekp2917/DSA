import java.util.Arrays;

class Solution {
    public int maximumCandies(int[] candies, long k) {
        int i=0, j=Arrays.stream(candies).max().getAsInt();
        int ans = 0;
        while(i<=j) {
            int mid = (i+j)/2;
            if(isPoss(candies, k, mid)) {
                ans = mid;
                i = mid+1;
            }
            else j = mid-1;
        }
        return ans;
    }

    public boolean isPoss(int[] candies, long k, int maxCandy) {
        if(maxCandy==0) return true;
        long candyCount = 0;
        for(int candy : candies) candyCount += candy/maxCandy;
        return candyCount>=k;
    }
}