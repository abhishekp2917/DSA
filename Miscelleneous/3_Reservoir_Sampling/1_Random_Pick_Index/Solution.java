import java.util.Random;

class Solution {

    private int[] nums;
    private Random random;

    public Solution(int[] nums) {
        this.nums = nums; 
        this.random = new Random();   
    }
    
    public int pick(int target) {
        int answer = -1;
        int count = 0;

        for(int i=0; i<this.nums.length; i++) {
            if(this.nums[i]!=target) continue;
            count++; 
            if(this.random.nextInt(count)==0) {
                answer = i;
            }
        }
        return answer;
    }
}
