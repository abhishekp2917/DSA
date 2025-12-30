import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean queryString(String s, int n) {
        for(int len=1; len<=30; len++) {
            int idx = 0;
            int num = 0;
            Set<Integer> set = new HashSet<>();
            for(int i=(1<<(len-1)); i<(1<<len) && i<=n; i++) set.add(i);
            while(idx<s.length() && idx<(len-1)) {
                num = (num<<1) | (s.charAt(idx)-'0');
                idx++;
            }
            while(idx<s.length()) {
                num = (num<<1) | (s.charAt(idx)-'0');
                if(set.contains(num)) {
                    set.remove(num);
                } 
                num &= ~(1<<(len-1));
                idx++;
            }
            if(!set.isEmpty()) return false;
        }
        return true;
    }
}


