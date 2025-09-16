class Solution {
    public int lastRemaining(int n) {
        return recurrsion(n, true);
    }

    private int recurrsion(int n, boolean fromLeft) {
        if(n==1) return 1;
        if(fromLeft) {
            return recurrsion(n/2, !fromLeft)*2;
        }
        else {
            if(n%2==0) {
                return (recurrsion(n/2, !fromLeft)*2-1);
            }
            else {
                return  recurrsion(n/2, !fromLeft)*2;
            }
        }
    }   
}

