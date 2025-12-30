import java.util.Arrays;

class Solution {
    public boolean makesquare(int[] matchsticks) {
        long totalLen = 0;
        Arrays.sort(matchsticks);
        for(int len : matchsticks) totalLen += len;
        if(totalLen%4!=0) return false;
        return recursion(matchsticks, matchsticks.length-1, new long[4], totalLen/4);
    }

    private boolean recursion(int[] matchsticks, int ptr, long[] sidelen, long expectedLen) {
        if(ptr<0) {
            return (sidelen[0]==sidelen[1] && sidelen[1]==sidelen[2] && sidelen[2]==sidelen[3]);
        }
        for(int i=0; i<4; i++) {
            if(sidelen[i] + matchsticks[ptr]>expectedLen) continue;
            sidelen[i] += matchsticks[ptr];
            if(recursion(matchsticks, ptr-1, sidelen, expectedLen)) return true;
            sidelen[i] -= matchsticks[ptr];
        }
        return false;
    }
}