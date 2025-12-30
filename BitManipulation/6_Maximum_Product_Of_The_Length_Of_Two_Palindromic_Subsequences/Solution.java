import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxProduct(String s) {
        int maxProd = 0;
        Map<Integer, Integer> palindromeMap = new HashMap<>();
        buildPalindrome(s, 0, 0, new StringBuilder(), palindromeMap);
        for(Integer mask1 : palindromeMap.keySet()) {
            for(Integer mask2 : palindromeMap.keySet()) {
                if((mask1&mask2)==0) {
                    maxProd = Math.max(maxProd, palindromeMap.get(mask1)*palindromeMap.get(mask2));
                }
            }
        }
        return maxProd;
    }

    private void buildPalindrome(String s, int ptr, int bitmask, StringBuilder str, Map<Integer, Integer> palindromeMap) {
        if(str.length()>0 && isPalindrome(str)) palindromeMap.put(bitmask, str.length());
        if(ptr==s.length()) return;
        str.append(s.charAt(ptr));
        buildPalindrome(s, ptr+1, bitmask|(1<<ptr), str, palindromeMap);
        str.deleteCharAt(str.length()-1);
        buildPalindrome(s, ptr+1, bitmask, str, palindromeMap);
    }

    private boolean isPalindrome(StringBuilder s) {
        for(int i=0; i<=s.length()/2; i++) {
            if(s.charAt(i)!=s.charAt(s.length()-i-1)) return false;
        }
        return true;
    }
}
