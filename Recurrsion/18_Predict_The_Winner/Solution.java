import java.util.Arrays;

class Solution1 {
    public boolean predictTheWinner(int[] nums) {
        return isPlayer1Winner(nums, 0, nums.length-1, 0, 0, true);
    }

    private boolean isPlayer1Winner(int[] nums, int start, int end, int player1, int player2, boolean player1Turn) {
        if(start>end) {
            return player1>=player2;
        }
        if(player1Turn) {
            return isPlayer1Winner(nums, start+1, end, player1+nums[start], player2, !player1Turn) || isPlayer1Winner(nums, start, end-1, player1+nums[end], player2, !player1Turn);
        }
        else {
            return isPlayer1Winner(nums, start+1, end, player1, player2+nums[start], !player1Turn) && isPlayer1Winner(nums, start, end-1, player1, player2+nums[end], !player1Turn);
        }
    }
}

class Solution2 {
    public boolean predictTheWinner(int[] nums) {
        int total = Arrays.stream(nums).reduce(0, (a, b)->a+b);
        int player1Score = player1Score(nums, 0, nums.length-1);
        return 2*player1Score>=total;
    }

    private int player1Score(int[] nums, int start, int end) {
        if(start>end) {
            return 0;
        }
        int score1 = nums[start] + Math.min(player1Score(nums, start+1, end-1), player1Score(nums, start+2, end));
        int score2 = nums[end] + Math.min(player1Score(nums, start+1, end-1), player1Score(nums, start, end-2));
        return Math.max(score1, score2);
    }
}