class Solution {
    
    public int[] getTicTacToeOptimalPos(char[][] board, char player) {
        float[] playerWinOptimalPos = recursion(board, player);
        return new int[] {(int)playerWinOptimalPos[0], (int)playerWinOptimalPos[1]};
    }

    private float[] recursion(char[][] board, char player) {
        char opponent = (player=='O')? 'X' : 'O';
        if(isWinner(board, player)) return new float[] {-1, -1, 1};
        if(isWinner(board, opponent)) return new float[] {-1, -1, 0};
        if(isDraw(board)) return new float[] {-1, -1, 0.5f}; 
        float[] optimalProb = new float[] {-1, -1, -1};
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j]!='.') continue;
                board[i][j] = player;
                float[] temp = recursion(board, opponent);
                float opponentWinningProb = temp[2];
                if((1-opponentWinningProb)>optimalProb[2]) {
                    optimalProb = new float[] {i, j, (1-opponentWinningProb)};
                }
                board[i][j] = '.';
            }
        }
        return optimalProb;
    }

    private boolean isWinner(char[][] board, char player) {
        char tl = board[0][0], tm = board[0][1], tr = board[0][2];
        char ml = board[1][0], mm = board[1][1], mr = board[1][2];
        char bl = board[2][0], bm = board[2][1], br = board[2][2];
        return (
            (tl==tm && tm==tr && tr==player) ||
            (ml==mm && mm==mr && mr==player) ||
            (bl==bm && bm==br && br==player) ||
            (tl==ml && ml==bl && bl==player) ||
            (tm==mm && mm==bm && bm==player) ||
            (tr==mr && mr==br && br==player) ||
            (tl==mm && mm==br && br==player) ||
            (tr==mm && mm==bl && bl==player));
    }

    private boolean isDraw(char[][] board) {
        boolean isDraw = true;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(board[i][j]=='.') isDraw = false;
            }
        }
        return isDraw;
    }
}

