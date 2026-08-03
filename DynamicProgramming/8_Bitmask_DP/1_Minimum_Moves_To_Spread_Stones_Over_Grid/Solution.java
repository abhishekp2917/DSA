import java.util.ArrayList;
import java.util.List;

class Solution1 {
    public int minimumMoves(int[][] grid) {
        List<int[]> extraStones = new ArrayList<>();
        List<int[]> emptyCells = new ArrayList<>();
        for(int row=0; row<3; row++) {
            for(int col=0; col<3; col++) {
                int stones = grid[row][col];
                if(stones==0) emptyCells.add(new int[]{ row, col });
                for(int count=1; count<=stones-1; count++) {
                    extraStones.add(new int[] { row, col });
                }
            }
        }
        int n = extraStones.size();
        int m = emptyCells.size();
        int emptyCellsMask = (1<<m)-1;
        Integer[][] memo = new Integer[n][emptyCellsMask+1];
        return recursion(extraStones, emptyCells, 0, emptyCellsMask, memo);
    }

    private int recursion(List<int[]> extraStones, List<int[]> emptyCells, int i, int emptyCellsMask, Integer[][] memo) {
        if(i==extraStones.size()) return 0;
        if(memo[i][emptyCellsMask]!=null) return memo[i][emptyCellsMask];
        int minCost = Integer.MAX_VALUE/2;
        for(int bit=0; bit<emptyCells.size(); bit++) {
            int bitValue = (emptyCellsMask>>bit)&1;
            if(bitValue==1) {
                int newEmptyCellsMask = emptyCellsMask^(1<<bit);
                int cost = calcCost(extraStones, emptyCells, i, bit);
                minCost = Math.min(
                    minCost,
                    cost + recursion(extraStones, emptyCells, i+1, newEmptyCellsMask, memo)
                );  
            }
        }
        memo[i][emptyCellsMask] = minCost;
        return minCost;
    }

    private int calcCost(List<int[]> extraStones, List<int[]> emptyCells, int i, int j) {
        int x1 = extraStones.get(i)[0];
        int y1 = extraStones.get(i)[1];
        int x2 = emptyCells.get(j)[0];
        int y2 = emptyCells.get(j)[1];
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}

class Solution2 {
    public int minimumMoves(int[][] grid) {
        List<int[]> extraStones = new ArrayList<>();
        List<int[]> emptyCells = new ArrayList<>();
        for(int row=0; row<3; row++) {
            for(int col=0; col<3; col++) {
                int stones = grid[row][col];
                if(stones==0) emptyCells.add(new int[]{ row, col });
                for(int count=1; count<=stones-1; count++) {
                    extraStones.add(new int[] { row, col });
                }
            }
        }
        int n = extraStones.size();
        int m = emptyCells.size();
        int emptyCellsMask = (1<<m)-1;
        int[][] dp = new int[n+1][emptyCellsMask+1];
        for(int i=n-1; i>=0; i--) {
            for(int mask=1; mask<=emptyCellsMask; mask++) {
                int minCost = Integer.MAX_VALUE/2;
                for(int bit=0; bit<m; bit++) {
                    int bitValue = (mask>>bit)&1;
                    if(bitValue==1) {
                        int newMask = mask^(1<<bit);
                        int cost = calcCost(extraStones, emptyCells, i, bit);
                        minCost = Math.min(
                            minCost,
                            cost + dp[i+1][newMask]
                        );  
                    }
                }
                dp[i][mask] = minCost;
            }
        }
        return dp[0][emptyCellsMask];
    }

    private int calcCost(List<int[]> extraStones, List<int[]> emptyCells, int i, int j) {
        int x1 = extraStones.get(i)[0];
        int y1 = extraStones.get(i)[1];
        int x2 = emptyCells.get(j)[0];
        int y2 = emptyCells.get(j)[1];
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}