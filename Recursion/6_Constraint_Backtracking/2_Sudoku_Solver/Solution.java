import java.util.ArrayList;
import java.util.List;

class Solution {

    // Pattern: Backtracking / DFS (Permutation with Strong Constraints)
    //
    // Core intuition:
    // Some cells of the board are empty ('.').
    // For each empty cell, we must choose a digit from '1' to '9'
    // such that Sudoku rules are satisfied.
    //
    // The problem is solved by filling empty cells one by one
    // and backtracking whenever a conflict occurs.

    public void solveSudoku(char[][] board) {

        // Collect positions of all empty cells
        List<Integer[]> voids = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    voids.add(new Integer[]{i, j});
                }
            }
        }

        // Start recursion from the first empty cell
        recurrsion(board, 0, voids);
    }

    private boolean recurrsion(
        char[][] board,
        int ptr,
        List<Integer[]> voids
    ) {

        // Base case:
        // If all empty cells are filled, the board is solved
        if (ptr == voids.size()) return true;

        int row = voids.get(ptr)[0];
        int col = voids.get(ptr)[1];

        // Try placing digits from '1' to '9' in the current empty cell
        for (char num = '1'; num <= '9'; num++) {

            // Skip invalid placements
            if (!isValid(board, row, col, num)) continue;

            // Place the digit
            board[row][col] = num;

            // Recurse to fill the next empty cell
            if (recurrsion(board, ptr + 1, voids)) return true;

            // Backtrack: remove the digit
            board[row][col] = '.';
        }

        // No valid digit can be placed in this cell
        return false;
    }

    // Checks whether placing `num` at (row, col) violates Sudoku rules
    private boolean isValid(
        char[][] board,
        int row,
        int col,
        char num
    ) {

        for (int i = 0; i < 9; i++) {

            // Check column
            if (board[i][col] == num) return false;

            // Check row
            if (board[row][i] == num) return false;

            // Check 3x3 sub-grid
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == num) {
                return false;
            }
        }

        return true;
    }
}
