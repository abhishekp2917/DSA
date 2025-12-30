import java.util.HashMap;
import java.util.Map;

class Solution {
    public int findTheLongestSubstring(String s) {
        int maxLen = 0; 
        Map<Integer, Integer> xorMap = new HashMap<>();
        xorMap.put(0, -1);
        int xor = 0;
        for(int i=0; i<s.length(); i++) {
            char ch = s.charAt(i);
            if(ch=='a' || ch=='e' || ch=='i' || ch=='o' || ch=='u') {
                xor ^= (1<<(ch-'a'));
            }
            if(xorMap.containsKey(xor)) maxLen = Math.max(maxLen, i-xorMap.get(xor));
            else xorMap.put(xor, i);
        }
        return maxLen;
    }
}