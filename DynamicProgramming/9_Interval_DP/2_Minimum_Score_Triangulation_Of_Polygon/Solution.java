class Solution1 {

    public int minScoreTriangulation(int[] values) {

        int n = values.length;

        // memo[start][end]:
        // stores the minimum triangulation score
        // for the polygon formed
        // by vertices from start to end.
        Integer[][] memo = new Integer[n][n];

        return recursion(values, 0, n-1, memo);
    }

    private int recursion(int[] values, int start, int end, Integer[][] memo) {

        // Fewer than 3 vertices
        // cannot form any triangle.
        //
        // Therefore no score is added.
        if(end-start==1) return 0;

        // Return previously computed interval.
        if(memo[start][end]!=null) return memo[start][end];

        int minScore = Integer.MAX_VALUE;

        // Choose every possible middle vertex.
        //
        // Together with
        // start and end,
        // it forms one triangle.
        for(int i=start+1; i<end; i++) {

            int v1 = values[start];
            int v2 = values[i];
            int v3 = values[end];

            // Score contributed
            // by the current triangle.
            int triangleScore = v1*v2*v3;

            // Forming this triangle
            // automatically splits
            // the remaining polygon
            // into two smaller independent polygons.
            int leftPolygonScore =
                recursion(values, start, i, memo);

            int rightPolygonScore =
                recursion(values, i, end, memo);

            // Total score
            // equals the score
            // of the current triangle
            // plus the optimal triangulation
            // of both remaining polygons.
            minScore = Math.min(
                minScore,
                triangleScore +
                leftPolygonScore +
                rightPolygonScore
            );
        }

        memo[start][end] = minScore;

        return minScore;
    }
}

class Solution2 {

    public int minScoreTriangulation(int[] values) {

        int n = values.length;

        // dp[start][end]:
        // stores the minimum triangulation score
        // for the polygon formed
        // by vertices from start to end.
        int[][] dp = new int[n+1][n+1];

        // Build answers
        // from smaller polygons
        // towards larger polygons.
        //
        // Every transition depends only
        // on smaller intervals.
        for(int start=n-1; start>=0; start--) {

            // A valid polygon
            // requires at least 3 vertices.
            for(int end=start+2; end<=n-1; end++) {

                int minScore = Integer.MAX_VALUE;

                // Try every possible vertex
                // as the third vertex
                // of the first triangle.
                for(int i=start+1; i<end; i++) {

                    int v1 = values[start];
                    int v2 = values[i];
                    int v3 = values[end];

                    // Score of the triangle
                    // formed by
                    // (start, i, end).
                    int triangleScore = v1*v2*v3;

                    // Remaining polygon
                    // gets divided
                    // into two independent sub-polygons.
                    int leftPolygonScore = dp[start][i];

                    int rightPolygonScore = dp[i][end];

                    // Choose the partition
                    // producing the minimum
                    // overall triangulation score.
                    minScore = Math.min(
                        minScore,
                        triangleScore +
                        leftPolygonScore +
                        rightPolygonScore
                    );
                }

                dp[start][end] = minScore;
            }
        }

        return dp[0][n-1];
    }
}