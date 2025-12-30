class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int count = 0;
        boolean[] xorSpace = new boolean[1<<11];
        for(int num1 : nums) {
            for(int num2 : nums) {
                xorSpace[num1^num2] = true;
            }
        }
        for(int res=0; res<=(1<<11)-1; res++) {
            for(int num : nums) {
                if(xorSpace[num^res]) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}