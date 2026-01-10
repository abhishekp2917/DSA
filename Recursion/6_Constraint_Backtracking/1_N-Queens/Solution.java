import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    // Pattern: Backtracking / DFS (Permutation with Constraints)
    //
    // Core intuition:
    // We are placing one queen in each row of the chessboard.
    // For every row, we must decide which column to place the queen in,
    // such that no two queens attack each other.
    //
    // Each row contributes exactly one queen, and columns are chosen
    // under strict constraints (column + diagonals).

    public List<List<String>> solveNQueens(int n) {

        List<List<String>> boards = new ArrayList<>();

        // queenRowPos[row] = column index where the queen is placed in that row
        int[] queenRowPos = new int[n];
        Arrays.fill(queenRowPos, -1);

        // Start placing queens row by row
        recurrsion(n, 0, queenRowPos, boards);

        return boards;
    }

    private void recurrsion(
        int n,
        int row,
        int[] queenRowPos,
        List<List<String>> boards
    ) {

        // Base case:
        // If all rows are processed, a valid board is formed
        if (row == n) {

            List<String> board = new ArrayList<>();

            // Construct the board representation
            for (int i = 0; i < n; i++) {
                char[] boardRow = new char[n];
                Arrays.fill(boardRow, '.');
                boardRow[queenRowPos[i]] = 'Q';
                board.add(new String(boardRow));
            }

            boards.add(board);
        }

        // Try placing a queen in every column of the current row
        for (int col = 0; col < n; col++) {

            // Check if placing a queen at (row, col) is safe
            if (isValid(row, col, queenRowPos)) {

                // Place the queen
                queenRowPos[row] = col;

                // Recurse to place queen in next row
                recurrsion(n, row + 1, queenRowPos, boards);

                // Backtrack: remove queen from current row
                queenRowPos[row] = -1;
            }
        }
    }

    // Checks whether placing a queen at (x1, y1) conflicts
    // with any previously placed queen
    private boolean isValid(int x1, int y1, int[] queenRowPos) {

        int n = queenRowPos.length;

        for (int x2 = 0; x2 < n; x2++) {

            int y2 = queenRowPos[x2];
            if (y2 == -1) continue;

            // Same row or same column
            if (x1 == x2 || y1 == y2) return false;

            // Same main diagonal
            if ((x1 - y1) == (x2 - y2)) return false;

            // Same anti-diagonal
            if ((x1 - (n - y1 - 1)) == (x2 - (n - y2 - 1))) return false;
        }

        return true;
    }
}
