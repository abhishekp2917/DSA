import java.util.Arrays;

class Solution {
    public int singleNumber(int[] nums) {
        return Arrays.stream(nums).reduce(0, (accumulated, num) -> accumulated^num);
    }
}