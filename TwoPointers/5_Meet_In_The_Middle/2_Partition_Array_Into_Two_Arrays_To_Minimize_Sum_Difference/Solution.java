import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Solution {
    public int minimumDifference(int[] nums) {

        int n = nums.length;

        // We want to split array into two groups of equal size (n/2 elements each)
        // such that absolute difference of their sums is minimum.
        //
        // This is NOT normal partition problem.
        // We must pick exactly n/2 elements.

        int minAbsDiff = Integer.MAX_VALUE;

        int totalSum = 0;

        // Compute total sum.
        // Later:
        // If firstHalfSum is sum of chosen n/2 elements,
        // then secondHalfSum = totalSum - firstHalfSum.
        for(int num : nums) totalSum += num;

        // Meet-in-the-middle:
        // Split array into two halves.
        //
        // For each half, we generate:
        //    (subsetSum, subsetSize)
        //
        // subsetSize is CRUCIAL because
        // we must ensure final chosen elements count = n/2.
        List<int[]> leftSubsetSum = new ArrayList<>();
        List<int[]> rightSubsetSum = new ArrayList<>();

        generateSubsetSum(nums, 0, n/2-1, 0, 0, leftSubsetSum);
        generateSubsetSum(nums, n/2, n-1, 0, 0, rightSubsetSum);

        // Sort both lists by:
        // 1) subsetSize
        // 2) subsetSum
        //
        // Why?
        // Because we only want to combine subsets
        // where:
        //     leftSize + rightSize = n/2
        //
        // Sorting allows structured two-pointer traversal.
        Comparator<int[]> subsetComparator = (s1, s2) -> {
            if(s1[1]==s2[1]) return s1[0]-s2[0];
            return s1[1]-s2[1];
        };

        Collections.sort(leftSubsetSum, subsetComparator);
        Collections.sort(rightSubsetSum, subsetComparator);

        int leftLen = leftSubsetSum.size(), rightLen = rightSubsetSum.size();

        int i=0, j=rightLen-1;

        while(i<leftLen && j>=0) {

            int firstHalfSum = leftSubsetSum.get(i)[0] + rightSubsetSum.get(j)[0];

            int secondHalfSum = totalSum-firstHalfSum;

            int diff = Math.abs(firstHalfSum-secondHalfSum);

            minAbsDiff = Math.min(minAbsDiff, diff);

            // If firstHalfSum is too large,
            // we need to reduce it → decrease right side.
            if(firstHalfSum>secondHalfSum) {

                j--;

                // After moving j,
                // ensure subset sizes still add up to n/2.
                //
                // If not, move i until size condition matches.
                while(i<leftLen && j>=0 &&
                      leftSubsetSum.get(i)[1]+rightSubsetSum.get(j)[1]!=n/2) {
                    i++;
                }
            }

            // If firstHalfSum is too small,
            // increase total → move left pointer.
            else if(firstHalfSum<secondHalfSum) {

                i++;

                // Again maintain exact size constraint.
                while(i<leftLen && j>=0 &&
                      leftSubsetSum.get(i)[1]+rightSubsetSum.get(j)[1]!=n/2) {
                    j--;
                }
            }

            // Perfect balance.
            else break;
        }

        return minAbsDiff;
    }

    private void generateSubsetSum(int[] nums,
                                   int start,
                                   int end,
                                   int subsetSize,
                                   int sum,
                                   List<int[]> subset) {

        // When we pass the segment,
        // we record:
        //    current sum
        //    number of elements chosen
        if(start>end) {
            subset.add(new int[] {sum, subsetSize});
            return;
        }

        // Choice 1: Include nums[start]
        generateSubsetSum(nums,
                          start+1,
                          end,
                          subsetSize+1,
                          sum + nums[start],
                          subset);

        // Choice 2: Exclude nums[start]
        generateSubsetSum(nums,
                          start+1,
                          end,
                          subsetSize,
                          sum,
                          subset);
    } 
}
