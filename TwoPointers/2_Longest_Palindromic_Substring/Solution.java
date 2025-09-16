class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        int start = 0, end = 0;
        for(int i=0; i<n; i++) {
            int left = i, right = i;
            while(left>=0 && right<n && s.charAt(left)==s.charAt(right)) {
                if((end-start+1)<(right-left+1)) {
                    start = left;
                    end = right;
                }
                left--;
                right++;
            }
            left = i;
            right = i+1;
            while(left>=0 && right<n && s.charAt(left)==s.charAt(right)) {
                if((end-start+1)<(right-left+1)) {
                    start = left;
                    end = right;
                }                
                left--;
                right++;
            }
        }
        return s.substring(start, end+1);
    }   
}

