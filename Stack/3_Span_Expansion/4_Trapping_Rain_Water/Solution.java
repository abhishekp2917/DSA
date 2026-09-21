class Solution {
    
    public int trap(int[] height) {
        int n = height.length;
        int trappedWater = 0;
        int[] greatestRight = new int[n];
        int[] greatestLeft = new int[n];
        for(int i=n-2; i>=0; i--) {
            greatestRight[i] = Math.max(greatestRight[i+1], height[i+1]);
        }
        for(int i=1; i<n; i++) {
            greatestLeft[i] = Math.max(greatestLeft[i-1], height[i-1]);
        }
        for(int i=0; i<n; i++) {
            trappedWater += Math.max(Math.min(greatestLeft[i], greatestRight[i])-height[i], 0);
        }
        return trappedWater;
    }
}