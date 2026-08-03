import java.util.Arrays;

class Solution1 {

    public static int longestBitonicSequence(int n, int[] nums) {
        Integer[] lisMemo = new Integer[n];
        Integer[] ldsMemo = new Integer[n];
        int longestBitonicSequenceLen = 0;
        for (int i = 0; i < n; i++) {
            lis(nums, i, lisMemo);
            lds(nums, i, ldsMemo);
        }
        for (int i = 0; i < n; i++) {
            if (lisMemo[i]==1 || ldsMemo[i]==1) continue;
            longestBitonicSequenceLen = Math.max(
                longestBitonicSequenceLen,
                lisMemo[i]+ldsMemo[i]-1
            );
        }
        return longestBitonicSequenceLen;
    }

    private static int lis(int[] nums, int currIdx, Integer[] memo) {
        if (memo[currIdx]!=null) return memo[currIdx];
        int best = 1;
        for (int prevIdx = 0; prevIdx < currIdx; prevIdx++) {
            if (nums[prevIdx] < nums[currIdx]) {
                best = Math.max(
                    best,
                    1 + lis(nums, prevIdx, memo)
                );
            }
        }
        return memo[currIdx] = best;
    }

    private static int lds(int[] nums, int currIdx, Integer[] memo) {
        if (memo[currIdx]!=null) return memo[currIdx];
        int best = 1;
        for (int nextIdx = currIdx + 1; nextIdx < nums.length; nextIdx++) {
            if (nums[nextIdx] < nums[currIdx]) {
                best = Math.max(
                    best,
                    1 + lds(nums, nextIdx, memo)
                );
            }
        }
        return memo[currIdx] = best;
    }
}

class Solution2 {
    public static int longestBitonicSequence(int n, int[] nums) {
        int[] lis = new int[n];
        int[] lds = new int[n];
        Arrays.fill(lis, 1);
        Arrays.fill(lds, 1);
        int longestBitonicSequenceLen = 0;
        for(int i=0; i<n; i++) {
            for(int j=0; j<i; j++) {
                if(nums[j]<nums[i]) {
                    lis[i] = Math.max(lis[i], 1 + lis[j]);
                }
            }
        }
        for(int i=n-1; i>=0; i--) {
            for(int j=n-1; j>i; j--) {
                if(nums[j]<nums[i]) {
                    lds[i] = Math.max(lds[i], 1 + lds[j]);
                }
            }
        }
        for(int i=0; i<n; i++) {
            if(lis[i]==1 || lds[i]==1) continue;
            longestBitonicSequenceLen = Math.max(
                longestBitonicSequenceLen,
                lis[i] + lds[i] - 1
            );
        }
        return longestBitonicSequenceLen;
    }
}