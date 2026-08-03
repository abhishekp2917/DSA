import java.util.Arrays;

class Solution1 {
    public int bestTeamScore(int[] scores, int[] ages) {
        int n = scores.length;
        int maxScore = 0;
        int[][] players = new int[n][2];
        for(int i=0; i<n; i++) {
            players[i][0] = ages[i];
            players[i][1] = scores[i];
        }
        Arrays.sort(players, (p1, p2) -> {
            if(p1[0]==p2[0]) return p1[1] - p2[1];
            return p1[0] - p2[0];
        });
        Integer[] memo = new Integer[n];
        for(int start=0; start<n; start++) {
            maxScore = Math.max(
                maxScore,
                recursion(players, start, memo)
            );
        }
        return maxScore;
    }

    private int recursion(int[][] players, int start, Integer[] memo) {
        if(start==players.length) return 0;
        if(memo[start]!=null) return memo[start];
        int currAge = players[start][0];
        int currScore = players[start][1];
        int maxScore = currScore;
        for(int next=start+1; next<players.length; next++) {
            int nextAge = players[next][0];
            int nextScore = players[next][1];
            if(currAge==nextAge || nextScore>=currScore) {
                maxScore = Math.max(
                    maxScore,
                    currScore + recursion(players, next, memo)
                );
            }
        }
        memo[start] = maxScore;
        return maxScore;
    }
}

class Solution2 {
    public int bestTeamScore(int[] scores, int[] ages) {
        int n = scores.length;
        int maxScore = 0;
        int[][] players = new int[n][2];
        for(int i=0; i<n; i++) {
            players[i][0] = ages[i];
            players[i][1] = scores[i];
        }
        Arrays.sort(players, (p1, p2) -> {
            if(p1[0]==p2[0]) return p1[1] - p2[1];
            return p1[0] - p2[0];
        });
        int[] dp = new int[n+1];
        for(int start=n-1; start>=0; start--) {
            int currAge = players[start][0];
            int currScore = players[start][1];
            dp[start] = currScore;
            for(int next=start+1; next<players.length; next++) {
                int nextAge = players[next][0];
                int nextScore = players[next][1];
                if(currAge==nextAge || nextScore>=currScore) {
                    dp[start] = Math.max(
                        dp[start],
                        currScore + dp[next]
                    );
                }
            }
            maxScore = Math.max(maxScore, dp[start]);
        }
        return maxScore;
    }
}
