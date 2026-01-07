import java.util.Arrays;

class Solution1 {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        int totalStones = 0;
        int[][] memo = new int[n][n];
        for(int i=0; i<n; i++) Arrays.fill(memo[i], -1);
        for(int stones : piles) totalStones += stones; 
        return totalStones<2*aliceStones(piles, 0, n-1, memo);
    }

    private int aliceStones(int[] piles, int start, int end, int[][] memo) {
        if(start>end) return 0;
        if(memo[start][end]!=-1) return memo[start][end];
        int minScoreAliceGetOnTakingFirst = piles[start] + Math.min(aliceStones(piles, start+2, end, memo), aliceStones(piles, start+1, end-1, memo));
        int minScoreAliceGetOnTakingLast = piles[end] + Math.min(aliceStones(piles, start, end-2, memo), aliceStones(piles, start+1, end-1, memo));
        memo[start][end] = Math.max(minScoreAliceGetOnTakingFirst, minScoreAliceGetOnTakingLast);
        return memo[start][end];
    }
}

class Solution2 {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        int[][] memo = new int[n][n];
        for(int i=0; i<n; i++) Arrays.fill(memo[i], -1);
        return aliceBobStonesDiff(piles, 0, n-1, memo)>0;
    }

    private int aliceBobStonesDiff(int[] piles, int start, int end, int[][] memo) {
        if(start>end) return 0;
        if(memo[start][end]!=-1) return memo[start][end];
        int stonesDiffIfTakenFirst = piles[start] - aliceBobStonesDiff(piles, start+1, end, memo);
        int stonesDiffIfTakenLast = piles[end] - aliceBobStonesDiff(piles, start, end-1, memo);
        memo[start][end] = Math.max(stonesDiffIfTakenFirst, stonesDiffIfTakenLast);
        return memo[start][end];
    }
}

