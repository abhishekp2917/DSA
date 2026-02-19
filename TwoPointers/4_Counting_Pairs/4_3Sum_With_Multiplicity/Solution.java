import java.util.Arrays;

class Solution {
    public int threeSumMulti(int[] nums, int target) {

        int n = nums.length;

        // Modulo required because answer can be very large.
        final int MOD = 1000000007; 

        int count = 0;

        // Sorting enables two pointer traversal
        // and makes duplicate handling easier.
        Arrays.sort(nums);
        
        // Since constraints are 0 <= nums[i] <= 100,
        // we use a frequency map of fixed size 101.
        // freqMap[x] tells how many times value x appears.
        int[] freqMap = new int[101];
        for(int num : nums) freqMap[num]++;

        // Fix first element of triplet.
        for(int i=0; i<n-2; i++) {

            int currNum = nums[i];

            // We are fixing nums[i],
            // so reduce its available frequency.
            // This ensures we don't reuse same index.
            freqMap[currNum]--;

            int left = i+1, right = n-1;

            while(left<right) {

                int leftNum = nums[left];
                int rightNum = nums[right];

                int sum = currNum + leftNum + rightNum;

                if(sum>target) {
                    // Need smaller sum → move right.
                    right--;
                }
                else if(sum<target) {
                    // Need larger sum → move left.
                    left++;
                }
                else {

                    // We found matching values for target.
                    // Now count multiplicity carefully.

                    if(leftNum!=rightNum) {
                        // If values are different,
                        // number of ways =
                        // freq(leftNum) * freq(rightNum)
                        count = (count + (freqMap[leftNum]*freqMap[rightNum])%MOD)%MOD;
                    }
                    else {
                        // If values are same,
                        // choose 2 from remaining freq:
                        // C(freq, 2)
                        count = (count + (freqMap[leftNum]*(freqMap[leftNum]-1)/2)%MOD)%MOD;
                    }

                    // Skip duplicate values to avoid recounting
                    // same value combinations again.
                    while(left<right && nums[left]==nums[left+1]) left++;
                    while(left<right && nums[right]==nums[right-1]) right--;

                    left++;
                    right--;
                }
            }
        }

        return count;
    }
}
