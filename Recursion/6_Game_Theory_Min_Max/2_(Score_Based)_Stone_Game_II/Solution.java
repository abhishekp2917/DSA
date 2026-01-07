import java.util.Arrays;

class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        int[] pilesSuffix = new int[n+1];
        int[][] memo = new int[n][n+1];
        for(int i=n-1; i>=0; i--) {
            pilesSuffix[i] = pilesSuffix[i+1] + piles[i];
            Arrays.fill(memo[i], -1);
        } 
        return playerStones(pilesSuffix, n, 0, 1, memo);
    }

    private int playerStones(int[] pilesSuffix, int n, int start, int m, int[][] memo) {
        if(start>=n) return 0;
        if(memo[start][m]!=-1) return memo[start][m];
        int stones = 0;
        for(int x=1; x<=2*m && start+x<=n; x++) {
            stones = Math.max(
                stones, 
                pilesSuffix[start] - playerStones(pilesSuffix, n, start+x, Math.max(m, x), memo));
        }
        memo[start][m] = stones;
        return stones;
    }
}