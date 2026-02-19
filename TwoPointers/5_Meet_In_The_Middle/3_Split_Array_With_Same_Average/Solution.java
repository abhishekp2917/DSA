import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public boolean splitArraySameAverage(int[] nums) {

        int n = nums.length;

        int totalSum = 0;

        // Compute total sum.
        // If we split into two parts A and B:
        // We want:
        //      avg(A) = avg(B)
        //
        // This is equivalent to:
        //      sum(A)/size(A) = (totalSum - sum(A)) / (n - size(A))
        //
        // So totalSum is needed for checking condition.
        for(int num : nums) totalSum += num;

        // Meet-in-the-middle:
        // We split array into two halves.
        //
        // Instead of storing just subset sums,
        // we store subset sums grouped by subset size.
        //
        // Why?
        // Because the average condition depends on subset size.
        Map<Integer, List<Integer>> leftSubsetSumMap = new HashMap<>();
        Map<Integer, List<Integer>> rightSubsetSumMap = new HashMap<>();

        generateSubsetSum(nums, 0, n/2-1, 0, 0, leftSubsetSumMap);
        generateSubsetSum(nums, n/2, n-1, 0, 0, rightSubsetSumMap);

        // For each subset size, sort the sums.
        //
        // Sorting enables two-pointer search
        // when combining left and right halves.
        for(List<Integer> subset : leftSubsetSumMap.values())
            Collections.sort(subset);

        for(List<Integer> subset : rightSubsetSumMap.values())
            Collections.sort(subset);

        // Try all possible combinations of:
        // leftSubsetSize and rightSubsetSize.
        //
        // total chosen size = leftSize + rightSize.
        for(int leftSubsetSize=0; leftSubsetSize<=n/2; leftSubsetSize++) {

            for(int rightSubsetSize=0; rightSubsetSize<=(n-n/2); rightSubsetSize++) {

                int firstPartSize = leftSubsetSize + rightSubsetSize;

                // Cannot choose empty set or full set.
                if(firstPartSize==0 || firstPartSize==n) continue;

                int secondPartSize = n-firstPartSize;

                List<Integer> leftSubsetSum =
                        leftSubsetSumMap.get(leftSubsetSize);

                List<Integer> rightSubsetSum =
                        rightSubsetSumMap.get(rightSubsetSize);

                int leftLen = leftSubsetSum.size(),
                    rightLen = rightSubsetSum.size();

                int i=0, j=rightLen-1;

                // Combine sums using two pointer approach.
                while(i<leftLen && j>=0) {

                    double firstPartSum =
                            leftSubsetSum.get(i)
                          + rightSubsetSum.get(j);

                    double secondPartSum =
                            totalSum-firstPartSum;

                    double firstPartAvg =
                            firstPartSum/firstPartSize;

                    double secondPartAvg =
                            secondPartSum/secondPartSize;

                    // If first average too large,
                    // reduce sum by decreasing right pointer.
                    if(firstPartAvg>secondPartAvg) {
                        j--;
                    }

                    // If first average too small,
                    // increase sum by increasing left pointer.
                    else if(firstPartAvg<secondPartAvg) {
                        i++;
                    }

                    // If averages equal,
                    // valid split found.
                    else return true;
                }
            }
        }

        return false;
    }

    private void generateSubsetSum(int[] nums,
                                   int start,
                                   int end,
                                   int subsetSize,
                                   int sum,
                                   Map<Integer, List<Integer>> subsetMap) {

        // When segment is exhausted,
        // store sum grouped by subsetSize.
        if(start>end) {

            List<Integer> subset =
                    subsetMap.getOrDefault(subsetSize,
                                           new ArrayList<>());

            subset.add(sum);

            subsetMap.put(subsetSize, subset);

            return;
        }

        // Include nums[start]
        generateSubsetSum(nums,
                          start+1,
                          end,
                          subsetSize+1,
                          sum + nums[start],
                          subsetMap);

        // Exclude nums[start]
        generateSubsetSum(nums,
                          start+1,
                          end,
                          subsetSize,
                          sum,
                          subsetMap);
    } 
}
