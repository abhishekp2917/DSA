import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution1 {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n=nums.length;
        ArrayList<Integer> longestSubset=new ArrayList<>();
        int[] prevIdxMap=new int[n];
        int longestSubsetLastIdx=0;
        int longestSubsetLen=0;
        Arrays.fill(prevIdxMap,-1);
        Arrays.sort(nums);
        Integer[] memo=new Integer[n];
        for(int i=0; i<n; i++) {
            int currLen=recursion(nums, i, memo, prevIdxMap);
            if(longestSubsetLen<currLen) {
                longestSubsetLen=currLen;
                longestSubsetLastIdx=i;
            }
        }
        while(longestSubsetLastIdx!=-1) {
            longestSubset.add(nums[longestSubsetLastIdx]);
            longestSubsetLastIdx=prevIdxMap[longestSubsetLastIdx];
        }
        Collections.reverse(longestSubset);
        return longestSubset;
    }

    private int recursion(int[] nums, int i, Integer[] memo, int[] prevIdxMap) {
        if(memo[i]!=null) return memo[i];
        int longestSubsetLen=1;
        int curr=nums[i];
        for(int j=0; j<i; j++) {
            int prev=nums[j];
            if(curr%prev==0) {
                int currLen=1+recursion(nums, j, memo, prevIdxMap);
                if(longestSubsetLen<currLen) {
                    longestSubsetLen=currLen;
                    prevIdxMap[i]=j;
                }
            }
        }
        memo[i]=longestSubsetLen;
        return longestSubsetLen;
    }
}

class Solution2 {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        ArrayList<Integer> longestSubset= new ArrayList<>();
        int[] prevIdxMap = new int[n];
        int longestSubsetLastIdx = 0;
        int longestSubsetLen = 0;
        int[] dp = new int[n+1];
        Arrays.fill(prevIdxMap, -1);
        Arrays.fill(dp, 1);
        Arrays.sort(nums);
        for(int i=1; i<=n; i++) {
            int curr = nums[i-1];
            for(int j=1; j<i; j++) {
                int prev = nums[j-1];
                if(curr%prev==0 && dp[i]<(1+dp[j])) {
                    dp[i] = 1 + dp[j];
                    prevIdxMap[i-1] = j-1; 
                }
            }
            if(longestSubsetLen<dp[i]) {
                longestSubsetLen = dp[i];
                longestSubsetLastIdx = i-1;
            }
        }
        while(longestSubsetLastIdx!=-1) {
            longestSubset.add(nums[longestSubsetLastIdx]);
            longestSubsetLastIdx = prevIdxMap[longestSubsetLastIdx];
        }
        Collections.reverse(longestSubset);
        return longestSubset;
    }
}





