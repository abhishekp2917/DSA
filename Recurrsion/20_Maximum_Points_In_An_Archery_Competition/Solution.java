class Solution {
    public int[] maximumBobPoints(int numArrows, int[] aliceArrows) {
        int[] maxBobArrows = new int[12];
        recurrsion(numArrows, aliceArrows, 11, new int[12], new int[1], maxBobArrows, new int[1]);
        return maxBobArrows;
    }

    private void recurrsion(int numArrows, int[] aliceArrows, int startScore, int[] bobArrows, int[] bobScore, int[] maxBobArrows, int[] maxBobScore) {
        if(bobScore[0]>maxBobScore[0]) {
            maxBobScore[0] = bobScore[0];
            for(int i=0; i<bobArrows.length; i++) maxBobArrows[i] = bobArrows[i];
            maxBobArrows[0] += numArrows;
        }
        for(int score=startScore; score>=1; score--) {
            if(numArrows>aliceArrows[score]) {
                bobScore[0] += score;
                bobArrows[score] = aliceArrows[score]+1;
                recurrsion(numArrows-bobArrows[score], aliceArrows, score-1, bobArrows, bobScore, maxBobArrows, maxBobScore);
                bobScore[0] -= score;
                bobArrows[score] = 0;
            }
        }
    }
}