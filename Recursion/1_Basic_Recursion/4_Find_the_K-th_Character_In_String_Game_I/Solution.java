class Solution {
    public char kthCharacter(int k) {
        return getChar(k, 512);
    }

    private char getChar(int k, int n) {
        if(k==1) return 'a';
        if(k<=n/2) {
            return getChar(k, n/2);
        }
        else {
            char ch = getChar(k-(n/2), n/2);
            char nextCh = (char)('a' + (ch+1-'a')%26);
            return nextCh;
        }
    }
}