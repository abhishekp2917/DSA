class Solution {

    public int maximumPoints(int mat[][]) {

        int n = mat.length;

        // dp[day][lastActivity]
        //
        // stores maximum points obtainable
        // till 'day' assuming the activity performed
        // on the PREVIOUS day is 'lastActivity'.
        //
        // Activity Mapping:
        // 0 -> Running
        // 1 -> Fighting
        // 2 -> Learning
        // 3 -> No restriction (used for final answer)
        int[][] dp = new int[n][4];

        // Base Case:
        // Day 0 has no previous day.
        //
        // If previous activity was Running,
        // today we can choose either Fighting or Learning.
        dp[0][0] = Math.max(mat[0][1], mat[0][2]);

        // If previous activity was Fighting,
        // today we can choose Running or Learning.
        dp[0][1] = Math.max(mat[0][0], mat[0][2]);

        // If previous activity was Learning,
        // today we can choose Running or Fighting.
        dp[0][2] = Math.max(mat[0][0], mat[0][1]);

        // No restriction on previous activity,
        // so choose the best among all three.
        dp[0][3] = Math.max(
            mat[0][0],
            Math.max(mat[0][1], mat[0][2])
        );

        // Compute answers day by day.
        for(int i=1; i<n; i++) {

            // Assume Running is chosen today.
            //
            // Yesterday's activity cannot be Running,
            // therefore previous state must be:
            // lastActivity = Running.
            int runningPoints =
                mat[i][0] + dp[i-1][0];

            // Assume Fighting is chosen today.
            int fightingPoints =
                mat[i][1] + dp[i-1][1];

            // Assume Learning is chosen today.
            int learningPoints =
                mat[i][2] + dp[i-1][2];

            // If tomorrow's previous activity is Running,
            // today's activity cannot be Running.
            dp[i][0] = Math.max(
                fightingPoints,
                learningPoints
            );

            // If tomorrow's previous activity is Fighting,
            // today's activity cannot be Fighting.
            dp[i][1] = Math.max(
                runningPoints,
                learningPoints
            );

            // If tomorrow's previous activity is Learning,
            // today's activity cannot be Learning.
            dp[i][2] = Math.max(
                runningPoints,
                fightingPoints
            );

            // No restriction on today's activity.
            //
            // Choose whichever activity
            // gives maximum total points.
            dp[i][3] = Math.max(
                runningPoints,
                Math.max(fightingPoints, learningPoints)
            );
        }

        // Final answer:
        // no restriction after last day.
        return dp[n-1][3];
    }
}