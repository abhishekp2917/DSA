import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[] minOperations(int[] nums) {
        int[] minOp = new int[nums.length];
        List<Integer> palindromes = new ArrayList<>();
        for(int i=1; i<=(1<<13)-1; i++) {
            if(isPalindrome(i)) palindromes.add(i);
        }
        for(int i=0; i<nums.length; i++) {
            minOp[i] = getMinOperation(nums[i], palindromes);
        }
        return minOp;
    }

    private int getMinOperation(int num, List<Integer> palindromes) {
        int left = 0, right = palindromes.size()-1;
        int minDiff = Integer.MAX_VALUE;
        while(left<=right) {
            int mid = (left+right)/2;
            int palindrome = palindromes.get(mid);
            int currDiff = num-palindrome;
            minDiff = Math.min(minDiff, Math.abs(currDiff));
            if(currDiff>=0) {
                left = mid+1;
            }
            else {
                right = mid-1;
            }
        }
        return minDiff;
    }

    private boolean isPalindrome(int num) {
        String binary = Integer.toBinaryString(num);
        int n = binary.length();
        for(int i=0; i<n/2; i++) {
            if(binary.charAt(i)!=binary.charAt(n-i-1)) return false;
        }
        return true;
    }
}
