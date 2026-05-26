import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    public int reversePairs(int[] nums) {

        int n = nums.length;

        // Coordinate Compression:
        //
        // We need ordering comparisons involving:
        //
        // nums[i]
        // and
        // 2 * nums[j]
        //
        // Values may be large or negative,
        // so direct indexing is impossible.
        //
        // Compression maps values into compact ranks.
        Map<Long, Integer> numToRankMap = getNumToRankMap(nums);
        int maxRank = numToRankMap.size();

        // Segment Tree stores:
        // frequency of already processed numbers.
        int[] segmentTree = new int[4*(maxRank+1)];
        int reversePairs = 0;
        
        for(int i=0; i<n; i++) {

            int num = nums[i];

            // Rank of current number.
            int rank = numToRankMap.get((long)num);

            // Reverse pair condition:
            //
            // nums[i] > 2 * nums[j]
            //
            // While iterating left to right,
            // current num acts as nums[j].
            //
            // We need count of previous numbers:
            //
            // previousNum > 2 * currentNum
            //
            // So query all ranks STRICTLY greater than:
            // 2 * currentNum
            int leftRank = numToRankMap.get(2L * num) + 1;
            int rightRank = maxRank;
            reversePairs += getRankCountInRange(0, 1, maxRank, leftRank, rightRank, segmentTree);

            // Insert current number into Segment Tree
            // for future elements.
            update(0, rank, 1, maxRank, segmentTree);
        }
        return reversePairs;
    }

    private int getRankCountInRange(int idx, int lowestRank, int highestRank, int leftRank, int rightRank, int[] segmentTree) {

        // No overlap:
        // contributes nothing.
        if(highestRank<leftRank || rightRank<lowestRank)
            return 0;

        // Complete overlap:
        // directly reuse stored frequency count.
        if(leftRank<=lowestRank && highestRank<=rightRank)
            return segmentTree[idx];

        int midRank = lowestRank + (highestRank-lowestRank)/2;
        int leftIdx = 2*idx + 1;
        int rightIdx = 2*idx + 2;

        // Partial overlap:
        // query both halves and merge counts.
        return getRankCountInRange(leftIdx, lowestRank, midRank, leftRank, rightRank, segmentTree) + getRankCountInRange(rightIdx, midRank+1, highestRank, leftRank, rightRank, segmentTree);
    }

    private void update(int idx, int rank, int lowestRank, int highestRank, int[] segmentTree) {

        // Leaf node:
        // frequency of one rank.
        if(lowestRank==highestRank) {
            segmentTree[idx]++;
            return;
        }

        int midRank = lowestRank + (highestRank-lowestRank)/2;
        int leftIdx = 2*idx + 1;
        int rightIdx = 2*idx + 2;

        // Only one side contains target rank.
        if(rank<=midRank) {
            update(leftIdx, rank, lowestRank, midRank, segmentTree);
        } 
        else {
            update(rightIdx, rank, midRank+1, highestRank, segmentTree);
        }

        // Merge child frequencies.
        segmentTree[idx] = segmentTree[leftIdx] + segmentTree[rightIdx];
    }

    private Map<Long, Integer> getNumToRankMap(int[] nums) {

        Map<Long, Integer> numToRankMap = new HashMap<>();
        List<Long> sortedNums = new ArrayList<>();

        // IMPORTANT:
        // Add BOTH:
        //
        // num and (2*num)
        //
        // WHY?
        //
        // Query boundary depends on (2*currentNum)
        //
        // So compressed ranks must exist for doubled values too.
        for(int num : nums) {
            sortedNums.add((long)num);
            sortedNums.add(num * 2L);
        }
        Collections.sort(sortedNums);
        int rank = 1;

        // Assign compressed ranks.
        // Equal values receive same rank.
        for(Long num : sortedNums) {
            if(!numToRankMap.containsKey(num)) {
                numToRankMap.put(num, rank);
                rank++;
            }
        }
        return numToRankMap;
    }
}