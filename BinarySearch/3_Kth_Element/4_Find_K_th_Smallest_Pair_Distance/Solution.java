import java.util.Arrays;

class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int minDist = 0, maxDist = 1000000;
        int kthDist = -1;
        while(minDist<=maxDist) {
            int dist = minDist + (maxDist-minDist)/2;
            if(hasAtleastKPairLessOrEqual(nums, k, dist)) {
                kthDist = dist;
                maxDist = dist-1;
            }
            else minDist = dist+1;
        } 
        return kthDist;
    }

    private boolean hasAtleastKPairLessOrEqual(int[] nums, int k, int distance) {
        int count = 0;
        int i=0, j=1;
        while(j<nums.length) {
            while(i<=j && nums[j]-nums[i]>distance) i++;
            count += j-i;
            j++;
        }
        return count>=k;
    }
}





