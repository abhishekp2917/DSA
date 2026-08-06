class Solution1 {

    public int deleteAndEarn(int[] nums) {

        // points[x]:
        // total points obtained
        // by deleting value x.
        //
        // Example:
        //
        // nums = [2,2,2,3]
        //
        // points[2] = 6
        // points[3] = 3
        //
        // Since choosing one occurrence of x
        // forces us to choose all occurrences,
        // we aggregate them beforehand.
        int[] points = new int[10001];

        for(int num : nums) {
            points[num] += num;
        }

        // memo[i]:
        // maximum points obtainable
        // considering values from i onwards.
        Integer[] memo = new Integer[10001];

        return recursion(points, 0, memo);
    }

    private int recursion(int[] points, int i, Integer[] memo) {

        // Base Case:
        //
        // No values left to consider.
        if(i>=points.length) return 0;

        // Return previously computed answer.
        if(memo[i]!=null) return memo[i];

        // Option 1:
        // Take value i.
        //
        // Then value (i+1) cannot be chosen,
        // because deleting i removes all (i±1).
        int take =
            points[i] +
            recursion(points, i+2, memo);

        // Option 2:
        // Skip value i
        // and consider next value.
        int skip =
            recursion(points, i+1, memo);

        // Choose better decision.
        memo[i] = Math.max(take, skip);

        return memo[i];
    }
}

class Solution2 {
    
    public int deleteAndEarn(int[] nums) {

        // points[x]:
        // total points obtainable
        // by taking value x.
        int[] points = new int[10001];

        for(int num : nums) {
            points[num] += num;
        }

        int n = points.length;

        // dp[i]:
        // maximum points obtainable
        // considering values from i onwards.
        //
        // Extra space avoids boundary checks
        // while accessing dp[i+2].
        int[] dp = new int[10002];

        // Build answers from larger values
        // towards smaller values.
        for(int i=n-1; i>=0; i--) {

            // Option 1:
            // Take value i.
            //
            // Must skip value (i+1).
            int take =
                points[i] +
                ((i+2<n) ? dp[i+2] : 0);

            // Option 2:
            // Skip value i.
            int skip =
                dp[i+1];

            // Choose better decision.
            dp[i] = Math.max(take, skip);
        }

        // Answer considering all values.
        return dp[0];
    }
}