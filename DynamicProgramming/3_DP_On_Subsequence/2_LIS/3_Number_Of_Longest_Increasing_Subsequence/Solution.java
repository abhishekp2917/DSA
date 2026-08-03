class Solution1 {

    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        Integer[] lenMemo = new Integer[n];
        Integer[] countMemo = new Integer[n];
        int lisLen = 0;
        int countOfLis = 0;
        for (int i = 0; i < n; i++) {
            recursion(nums, i, lenMemo, countMemo);
            int currLisLen = lenMemo[i];
            int currLisCount = countMemo[i];
            if (currLisLen > lisLen) {
                lisLen = currLisLen;
                countOfLis = currLisCount;
            } else if (currLisLen == lisLen) {
                countOfLis += currLisCount;
            }
        }
        return countOfLis;
    }

    private void recursion(int[] nums, int currIdx, Integer[] lenMemo, Integer[] countMemo) {
        if (lenMemo[currIdx]!=null) return;
        int bestLen = 1;
        int bestCount = 1;
        for (int prevIdx = 0; prevIdx < currIdx; prevIdx++) {
            if (nums[prevIdx] < nums[currIdx]) {
                recursion(nums, prevIdx, lenMemo, countMemo);
                int prevLen = lenMemo[prevIdx];
                int prevCount = countMemo[prevIdx];
                if (1 + prevLen > bestLen) {
                    bestLen = 1 + prevLen;
                    bestCount = prevCount;
                } else if (1 + prevLen == bestLen) {
                    bestCount += prevCount;
                }
            }
        }
        lenMemo[currIdx] = bestLen;
        countMemo[currIdx] = bestCount;
    }
}

class Solution2 {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int countOfLis = 0;
        int lisLen = 0;
        int[][] dp = new int[n][2];
        for(int i=0; i<n; i++) {
            dp[i][0] = 1;
            dp[i][1] = 1;
        }
        for(int i=0; i<n; i++) {
            for(int j=0; j<i; j++) {
                if(nums[j]<nums[i]) {
                    int prevLisLen = dp[j][0];
                    int prevLisCount = dp[j][1];
                    if((1 + prevLisLen)>dp[i][0]) {
                        dp[i][0] = 1 + prevLisLen;
                        dp[i][1] = prevLisCount;
                    }
                    else if(dp[i][0]==(1 + prevLisLen)) dp[i][1] += prevLisCount;
                }
            }
        }
        for(int i=0; i<n; i++) {
            int currLisLen = dp[i][0];
            int currLisCount = dp[i][1];
            if(currLisLen>lisLen) {
                lisLen = currLisLen;
                countOfLis = currLisCount;
            }
            else if(currLisLen==lisLen) {
                countOfLis += currLisCount;
            }
        }
        return countOfLis;
    }
}