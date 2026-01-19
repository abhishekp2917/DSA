class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        int i=1, j = 1;
        for(int n : bloomDay) j = Math.max(j, n);
        int ans = -1;
        while(i<=j) {
            int mid = (i+j)/2;
            if(isPoss(bloomDay, m, k, mid)) {
                ans = mid;
                j = mid-1;
            }
            else i = mid+1;
        }
        return ans;
    }

    private boolean isPoss(int[] bloomDay, int m, int k, int day) {
        int adjCount = 0;
        for(int i=0; i<bloomDay.length; i++) {
            if(bloomDay[i]<=day) adjCount++;
            else adjCount = 0;
            if(adjCount==k) {
                m--;
                adjCount = 0;
            }
            if(m==0) break;
        }
        return m==0;
    }
}