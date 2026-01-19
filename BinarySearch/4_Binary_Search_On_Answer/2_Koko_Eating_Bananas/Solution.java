class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int minHr=1, maxHr=1000000000;
        int minNoOfBananaPerHour = 0;
        while(minHr<=maxHr) {
            int noOfBananaPerHour = minHr + (maxHr-minHr)/2;
            if(calcHours(piles, noOfBananaPerHour)>h) minHr = noOfBananaPerHour+1;
            else {
                minNoOfBananaPerHour = noOfBananaPerHour;
                maxHr = noOfBananaPerHour-1;
            }
        }
        return minNoOfBananaPerHour;
    }

    private long calcHours(int[] piles, int noOfBananaPerHour) {
        long hour = 0;
        for(int i=0; i<piles.length; i++) {
            hour += piles[i]/noOfBananaPerHour;
            hour += (piles[i]%noOfBananaPerHour==0)?0:1;
        }
        return hour;
    }
}