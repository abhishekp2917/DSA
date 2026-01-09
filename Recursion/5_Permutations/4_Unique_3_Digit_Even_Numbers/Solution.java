class Solution {
    
    public int totalNumbers(int[] digits) {
        int[] digitCount = new int[10];
        for(int digit : digits) digitCount[digit]++;
        return countNumbers(digitCount, 0, 3);
    }

    private int countNumbers(int[] digitCount, int num, int n) {
        if(n==0) {
            if(num%2==0 && num>=100) return 1;
            else return 0;
        }
        int ans = 0;
        for(int i=0; i<10; i++) {
            if(digitCount[i]>0) {
                digitCount[i]--;
                ans += countNumbers(digitCount, num*10+i, n-1);
                digitCount[i]++;
            }
        }
        return ans;
    }
}


