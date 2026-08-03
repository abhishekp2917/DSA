import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Solution {
    public ArrayList<Integer> getLIS(int nums[]) {
        int n = nums.length;
        ArrayList<Integer> lis = new ArrayList<>();
        int[] dp = new int[n+1];
        int[] prevIdxMap = new int[n];
        Arrays.fill(prevIdxMap, -1);
        int lisLastIdx = 0;
        int lisLen = 0;
        for(int i=1; i<=n; i++) dp[i] = 1;
        for(int i=1; i<=n; i++) {
            int curr = nums[i-1];
            for(int j=1; j<i; j++) {
                int prev = nums[j-1];
                if(prev<curr && dp[i]<(1+dp[j])) {
                    dp[i] = 1 + dp[j];
                    prevIdxMap[i-1] = j-1; 
                }
            }
            if(lisLen<dp[i]) {
                lisLen = dp[i];
                lisLastIdx = i-1;
            }
        }
        while(lisLastIdx>=0) {
            lis.add(nums[lisLastIdx]);
            lisLastIdx = prevIdxMap[lisLastIdx];
        }
        Collections.reverse(lis);
        return lis;
    }
}



