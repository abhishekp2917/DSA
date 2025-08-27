import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> boards = new ArrayList<>();
        int[] queenRowPos = new int[n];
        Arrays.fill(queenRowPos, -1);
        recurrsion(n, 0, queenRowPos, boards);
        return boards;
    }

    private void recurrsion(int n, int row, int[] queenRowPos, List<List<String>> boards) {
        if(row==n) {
            List<String> board = new ArrayList<>();
            for(int i=0; i<n; i++) {
                char[] boardRow = new char[n];
                Arrays.fill(boardRow, '.');
                boardRow[queenRowPos[i]] = 'Q';
                board.add(new String(boardRow));
            }
            boards.add(board);
        }
        for(int col=0; col<n; col++) {
            if(isValid(row, col, queenRowPos)) {
                queenRowPos[row] = col;
                recurrsion(n, row+1, queenRowPos, boards);
                queenRowPos[row] = -1;
            }
        }
    }

    private boolean isValid(int x1, int y1, int[] queenRowPos) {
        int n = queenRowPos.length;
        for(int x2=0; x2<n; x2++) {
            int y2 = queenRowPos[x2];
            if(y2==-1) continue;
            if(x1==x2 || y1==y2 || (x1-y1)==(x2-y2) || (x1-(n-y1-1))==(x2-(n-y2-1))) return false;
        }
        return true;
    }
}
