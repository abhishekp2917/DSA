import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean divisorGame(int n) {
        return canPlayerWin(n, new HashMap<>());
    }

    private boolean canPlayerWin(int n, Map<Integer, Boolean> memo) {
        if(n==1) return false;
        if(memo.containsKey(n)) return memo.get(n);
        for(int i=1; i*i<n; i++) {
            if(n%i!=0) continue;
            if(!canPlayerWin(n-i, memo)) {
                memo.put(n, true);
                return true;
            }
        }
        memo.put(n, false);
        return false;
    }
}