class Solution {
    public int findTheWinner(int n, int k) {
        if(n==1) return 1;
        int nextStart = ((k+1)-1)%n+1;
        int winner = findTheWinner(n-1, k);
        return ((nextStart+winner-1)-1)%n+1;
    }
}


