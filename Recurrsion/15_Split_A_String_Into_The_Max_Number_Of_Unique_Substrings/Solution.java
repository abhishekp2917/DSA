import java.util.HashSet;
import java.util.Set;

class Solution {
    public int maxUniqueSplit(String s) {
        return recurrsion(s, 0, new HashSet<>());
    }

    private int recurrsion(String s, int start, Set<String> set) {
        if(start==s.length()) {
            return set.size();
        }
        int len = 0;
        for(int end=start+1; end<=s.length(); end++) {
            String substr = s.substring(start, end);
            if(!set.contains(substr)) {
                set.add(substr);
                len = Math.max(len, recurrsion(s, end, set));
                set.remove(substr);
            }
        }
        return len;
    }
}