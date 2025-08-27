import java.util.ArrayList;
import java.util.List;

class Solution {
    public void solveSudoku(char[][] board) {
        List<Integer[]> voids = new ArrayList<>();
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                char ch = board[i][j];
                if(ch=='.') {
                    voids.add(new Integer[] {i, j});
                    continue;
                }
            }
        }
        recurrsion(board, 0, voids);
    }

    private boolean recurrsion(char[][] board, int ptr, List<Integer[]> voids) {
        if(ptr==voids.size()) return true;
        int row = voids.get(ptr)[0];
        int col = voids.get(ptr)[1];
        for(char num='1'; num<='9'; num++) {
            if(!isValid(board, row, col, num)) continue;   
            board[row][col] = num;
            if(recurrsion(board, ptr+1, voids)) return true;
            board[row][col] = '.';
        }
        return false;
    }

    private boolean isValid(char[][]board, int row, int col, char num) {
        for(int i=0; i<9; i++) {
            if(board[i][col]==num) return false;
            if(board[row][i]==num) return false;
            if(board[3*(row/3)+i/3][3*(col/3)+i%3]==num) return false;
        }
        return true;
    }
}
