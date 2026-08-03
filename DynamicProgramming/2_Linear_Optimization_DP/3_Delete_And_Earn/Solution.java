class Solution1 {

    public int deleteAndEarn(int[] nums) {
        int[] points = new int[10001];
        for(int num : nums) points[num] += num;
        Integer[] memo = new Integer[10001];
        return recursion(points, 0, memo);
    }

    private int recursion(int[] points, int i, Integer[] memo) {
        if(i>=points.length) return 0;
        if(memo[i]!=null) return memo[i];
        int maxPoints = Math.max(
            points[i] + recursion(points, i+2, memo),
            recursion(points, i+1, memo)
        );
        memo[i] = maxPoints;
        return maxPoints;
    }
}


class Solution2 {
    
    public int deleteAndEarn(int[] nums) {
        int[] points = new int[10001];
        for(int num : nums) points[num] += num;
        int n = points.length;
        int[] dp = new int[10002];
        for(int i=n-1; i>=0; i--) {
            int maxPoints = Math.max(
                points[i] + ((i+2<n)? dp[i+2] : 0),
                dp[i+1]
            );
            dp[i] = maxPoints;
        }
        return dp[0];
    }
}
