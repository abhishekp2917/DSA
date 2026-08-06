class Solution1 {

    static int countWays(String s) {

        int n = s.length();

        // memo[start][end][0]:
        // Number of ways
        // to parenthesize the expression
        // from start to end
        // so that it evaluates to FALSE.
        //
        // memo[start][end][1]:
        // Number of ways
        // to parenthesize the same expression
        // so that it evaluates to TRUE.
        Integer[][][] memo = new Integer[n][n][2];

        // Return only
        // the number of TRUE evaluations.
        return recursion(s, 0, n-1, memo)[1];
    }

    private static Integer[] recursion(String s, int start, int end, Integer[][][] memo) {

        // Base case:
        // A single boolean value
        // has only one possible evaluation.
        if(start==end) {

            if(s.charAt(start)=='T') return new Integer[]{0, 1};

            else return new Integer[]{1, 0};
        }

        // If TRUE count has already been computed,
        // FALSE count is also guaranteed
        // because both are stored together.
        if(memo[start][end][1]!=null) return memo[start][end];

        int trueWays = 0;

        int falseWays = 0;

        // Every partition
        // must occur at an operator.
        //
        // Expression format:
        //
        // operand operator operand operator operand...
        //
        // Therefore operators
        // always lie at odd indices.
        for(int partIdx=start+1; partIdx<end; partIdx+=2) {

            char operand = s.charAt(partIdx);

            // Number of ways
            // left expression
            // evaluates to TRUE/FALSE.
            Integer[] leftCount = recursion(s, start, partIdx-1, memo);

            // Number of ways
            // right expression
            // evaluates to TRUE/FALSE.
            Integer[] rightCount = recursion(s, partIdx+1, end, memo);

            // Combine both sides
            // according to the operator's truth table.
            if(operand=='&') {

                // TRUE only when
                // both sides are TRUE.
                trueWays += leftCount[1]*rightCount[1];

                // Every remaining combination
                // evaluates to FALSE.
                falseWays += (
                    (leftCount[1]*rightCount[0]) +
                    (leftCount[0]*rightCount[1]) +
                    (leftCount[0]*rightCount[0])
                );
            }

            else if(operand=='|') {

                // TRUE whenever
                // at least one side is TRUE.
                trueWays += (
                    (leftCount[1]*rightCount[0]) +
                    (leftCount[0]*rightCount[1]) +
                    (leftCount[1]*rightCount[1])
                );

                // FALSE only when
                // both sides are FALSE.
                falseWays += leftCount[0]*rightCount[0];
            }

            else {

                // XOR is TRUE
                // only when exactly one side is TRUE.
                trueWays += (
                    (leftCount[1]*rightCount[0]) +
                    (leftCount[0]*rightCount[1])
                );

                // XOR is FALSE
                // when both sides are equal.
                falseWays += (
                    (leftCount[1]*rightCount[1]) +
                    (leftCount[0]*rightCount[0])
                );
            }
        }

        memo[start][end][1] = trueWays;

        memo[start][end][0] = falseWays;

        return memo[start][end];
    }
}

class Solution2 {

    static int countWays(String s) {

        int n = s.length();

        // dp[start][end][0]:
        // Number of ways
        // expression[start...end]
        // evaluates to FALSE.
        //
        // dp[start][end][1]:
        // Number of ways
        // expression[start...end]
        // evaluates to TRUE.
        int[][][] dp = new int[n][n][2];

        // Initialize every single operand.
        //
        // A single operand
        // has exactly one evaluation.
        for(int i=0; i<n; i+=2) {

            char bool = s.charAt(i);

            if(bool=='T') {

                dp[i][i][1] = 1;

                dp[i][i][0] = 0;
            }

            else {

                dp[i][i][1] = 0;

                dp[i][i][0] = 1;
            }
        }

        // Build larger expressions
        // from smaller expressions,
        // because every partition
        // depends on already solved subexpressions.
        for(int start=n-1; start>=0; start-=2) {

            for(int end=start+2; end<n; end+=2) {

                int trueWays = 0;

                int falseWays = 0;

                // Try every operator
                // as the final partition.
                for(int partIdx=start+1; partIdx<end; partIdx+=2) {

                    char operand = s.charAt(partIdx);

                    // Precomputed counts
                    // for the left expression.
                    int[] leftCount = dp[start][partIdx-1];

                    // Precomputed counts
                    // for the right expression.
                    int[] rightCount = dp[partIdx+1][end];

                    // Combine both subexpressions
                    // according to the operator.
                    if(operand=='&') {

                        trueWays += leftCount[1]*rightCount[1];

                        falseWays += (
                            (leftCount[1]*rightCount[0]) +
                            (leftCount[0]*rightCount[1]) +
                            (leftCount[0]*rightCount[0])
                        );
                    }

                    else if(operand=='|') {

                        trueWays += (
                            (leftCount[1]*rightCount[0]) +
                            (leftCount[0]*rightCount[1]) +
                            (leftCount[1]*rightCount[1])
                        );

                        falseWays += leftCount[0]*rightCount[0];
                    }

                    else {

                        trueWays += (
                            (leftCount[1]*rightCount[0]) +
                            (leftCount[0]*rightCount[1])
                        );

                        falseWays += (
                            (leftCount[1]*rightCount[1]) +
                            (leftCount[0]*rightCount[0])
                        );
                    }
                }

                // Store both answers together
                // because future states
                // require both TRUE and FALSE counts.
                dp[start][end][1] = trueWays;

                dp[start][end][0] = falseWays;
            }
        }

        return dp[0][n-1][1];
    }
}