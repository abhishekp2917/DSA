import java.util.ArrayList;
import java.util.List;

class Solution1 {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        Integer[][] memo = new Integer[n][n];
        return recursion(nums, 0, -1, memo);
    }

    private int recursion(int[] nums, int currIdx, int prevIdx, Integer[][] memo) {
        if(currIdx==nums.length) return 0;
        if(prevIdx!=-1 && memo[currIdx][prevIdx]!=null) return memo[currIdx][prevIdx];
        int curr = nums[currIdx];
        int prev = (prevIdx>=0)? nums[prevIdx] : Integer.MIN_VALUE;
        int maxLen = recursion(nums, currIdx+1, prevIdx, memo);
        if(prev<curr) {
            maxLen = Math.max(
                maxLen,
                1 + recursion(nums, currIdx+1, currIdx, memo)
            );
        }
        if(prevIdx!=-1) memo[currIdx][prevIdx] = maxLen;
        return maxLen;
    }    
}

class Solution2 {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n+1][n+1];
        for(int currIdx=n-1; currIdx>=0; currIdx--) {
            for(int prevIdx=currIdx-1; prevIdx>=-1; prevIdx--) {
                dp[currIdx][getNegOneBased(prevIdx)] = dp[currIdx+1][getNegOneBased(prevIdx)];
                if(prevIdx==-1 || nums[prevIdx]<nums[currIdx]) {
                    dp[currIdx][getNegOneBased(prevIdx)] = Math.max(
                        dp[currIdx][getNegOneBased(prevIdx)],
                        1 + dp[currIdx+1][getNegOneBased(currIdx)]
                    );
                }
            }
        }
        return dp[0][getNegOneBased(-1)];
    }

    private int getNegOneBased(int n) {
        return n+1;
    }
}

class Solution3 {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n+1];
        int lis = 0;
        for(int i=1; i<=n; i++) dp[i] = 1;
        for(int i=1; i<=n; i++) {
            int curr = nums[i-1];
            for(int j=1; j<i; j++) {
                int prev = nums[j-1];
                if(prev<curr) {
                    dp[i] = Math.max(
                        dp[i], 
                        1 + dp[j]
                    );
                }
            }
            lis = Math.max(lis, dp[i]);
        }
        return lis;
    }
}

class Solution4 {
    public int lengthOfLIS(int[] nums) {
        List<Integer> lis = new ArrayList<>();
        for(int num : nums) {
            int idx = getLowerBoundIndex(lis, num);
            if(lis.size()<idx+1) lis.add(num);
            else lis.set(idx, num);
        }
        return lis.size();
    }

    private int getLowerBoundIndex(List<Integer> lis, int num) {
        int left = 0, right = lis.size()-1;
        int idx = -1;
        while(left<=right) {
            int mid = (left + right)/2;
            if(lis.get(mid)<num) {
                idx = mid;
                left = mid+1;
            }
            else right = mid-1;
        }
        return idx+1;
    }
}

