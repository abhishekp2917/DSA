import java.util.Random;

class Solution {

    private int[] nums;
    private Random random;

    public Solution(int[] nums) {
        this.nums = nums; 
        this.random = new Random();   
    }
    
    public int[] pick(int k) {
        
        int[] answer = new int[k];

        for(int i=0; i<this.nums.length; i++) {
            if(i<k) {
                answer[i] = this.nums[i];
            }
            else {
                int index = random.nextInt(i+1);
                if(index<k) {
                    answer[index] = this.nums[i];
                }
            }
        }
        return answer;
    }
}
