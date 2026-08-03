class Solution1 {
    static int countWays(String s) {
        int n = s.length();
        Integer[][][] memo = new Integer[n][n][2];
        return recursion(s, 0, n-1, memo)[1];
    }
    
    private static Integer[] recursion(String s, int start, int end, Integer[][][] memo) {
        if(start==end) {
            if(s.charAt(start)=='T') return new Integer[]{0, 1};
            else return new Integer[]{1, 0};
        }
        if(memo[start][end][1]!=null) return memo[start][end];
        int trueWays = 0;
        int falseWays = 0;
        for(int partIdx=start+1; partIdx<end; partIdx+=2) {
            char operand = s.charAt(partIdx);
            Integer[] leftCount = recursion(s, start, partIdx-1, memo);
            Integer[] rightCount = recursion(s, partIdx+1, end, memo);
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
        memo[start][end][1] = trueWays;
        memo[start][end][0] = falseWays;
        return memo[start][end];
    }
}

class Solution2 {
    static int countWays(String s) {
        int n = s.length();
        int[][][] dp = new int[n][n][2];
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
        for(int start=n-1; start>=0; start-=2) {
            for(int end=start+2; end<n; end+=2) {
                int trueWays = 0;
                int falseWays = 0;
                for(int partIdx=start+1; partIdx<end; partIdx+=2) {
                    char operand = s.charAt(partIdx);
                    int[] leftCount = dp[start][partIdx-1];
                    int[] rightCount = dp[partIdx+1][end];
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
                dp[start][end][1] = trueWays;
                dp[start][end][0] = falseWays;
            }
        }
        return dp[0][n-1][1];
    }
}