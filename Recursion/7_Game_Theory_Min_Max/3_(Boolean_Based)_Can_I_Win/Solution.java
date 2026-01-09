import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        int sum = maxChoosableInteger * (maxChoosableInteger + 1) / 2;
        if (sum < desiredTotal) return false;
        if (desiredTotal <= 0) return true;   
        int availNums = (1<<(maxChoosableInteger+1))-2;     
        Map<Integer, Boolean> memo = new HashMap<>();
        return canPlayerwin(availNums, desiredTotal, memo);
    }

    private boolean canPlayerwin(int availNums, int desiredTotal, Map<Integer, Boolean> memo) {
        if(desiredTotal<=0) return false;
        if(memo.containsKey(availNums)) return memo.get(availNums);
        for(int i=1; i<=20; i++) {
            if(((availNums>>i)&1)==0) continue;
            if(!canPlayerwin(availNums^(1<<i), desiredTotal-i, memo)) {
                memo.put(availNums, true);
                return true;
            };
        }
        memo.put(availNums, false);
        return false;
    }
}
