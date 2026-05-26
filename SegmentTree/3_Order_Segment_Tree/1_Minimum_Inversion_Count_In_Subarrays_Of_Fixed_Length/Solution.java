import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {

    public long minInversionCount(int[] nums, int k) {

        int n = nums.length;
        long minInversionCount = Integer.MAX_VALUE; 

        // Coordinate Compression:
        //
        // Segment Tree works on compact rank range.
        //
        // Original values may be large or negative,
        // but inversion logic only depends on ordering.
        Map<Integer, Integer> numToRankMap = new HashMap<>();
        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums);
        int maxRank = 0;

        // Assign ranks preserving relative ordering.
        //
        // Equal values receive same rank.
        for(int num : sortedNums) {
            if(!numToRankMap.containsKey(num)) {
                numToRankMap.put(num, maxRank);
                maxRank++;
            }
        }

        // Segment Tree stores:
        // frequency of ranks inside current sliding window.
        int[] segmentTree = new int[4*maxRank];
        long inversionCount = 0;


        // Build inversion count for first window.
        for(int i=0; i<k; i++) {
            int addNumRank = numToRankMap.get(nums[i]);

            // Inversion condition:
            // previous elements > current element
            //
            // So count elements having:
            // rank > currentRank
            int left = addNumRank+1;
            int right = maxRank-1;
            inversionCount += getElementsCountInRankRange(0, 0, maxRank-1, left, right, segmentTree);

            // Insert current rank into Segment Tree.
            update(0, addNumRank, 0, maxRank-1, segmentTree, true);
        }

        minInversionCount = inversionCount;


        // Sliding Window:
        //
        // Remove outgoing element contribution
        // and add incoming element contribution.
        for(int i=k; i<n; i++) {
            int addNumRank = numToRankMap.get(nums[i]);

            // New inversions created by incoming element:
            // existing elements greater than incoming element.
            int addLeft = addNumRank+1;
            int addRight = maxRank-1;
            int removeNumRank =
                numToRankMap.get(nums[i - k]);

            // Inversions removed:
            // outgoing element was greater than
            // smaller elements still inside window.
            //
            // So count elements:
            // rank < removeRank
            int removeLeft = 0;
            int removeRight = removeNumRank-1;


            // Remove outgoing element first.
            //
            // IMPORTANT:
            // outgoing element itself should not participate
            // in removal query.
            update(0, removeNumRank, 0, maxRank-1, segmentTree, false);

            // Remove inversions contributed by outgoing element.
            inversionCount -= getElementsCountInRankRange(0, 0, maxRank-1, removeLeft, removeRight, segmentTree);

            // Add inversions contributed by incoming element.
            inversionCount += getElementsCountInRankRange(0, 0, maxRank-1, addLeft, addRight, segmentTree);
            // Insert incoming element into current window.
            update(0, addNumRank, 0, maxRank-1, segmentTree, true);
            minInversionCount = Math.min(minInversionCount, inversionCount);
        }
        return minInversionCount;
    }

    private void update(int idx, int rank, int lowRank, int highRank, int[] segmentTree, boolean toAdd) {

        // Leaf node:
        // frequency of one rank.
        if(lowRank==highRank) {
            // Add or remove rank frequency.
            segmentTree[idx] += (toAdd)?1:-1;
            return;
        }

        int midRank = lowRank + (highRank-lowRank)/2;
        int leftIdx = 2*idx + 1;
        int rightIdx = 2*idx + 2;

        // Only one side contains target rank.
        if(rank<=midRank) {
            update(leftIdx, rank, lowRank, midRank, segmentTree, toAdd);
        }
        else {
            update(rightIdx, rank, midRank+1, highRank, segmentTree, toAdd);
        }
        // Merge child frequencies.
        segmentTree[idx] = segmentTree[leftIdx] + segmentTree[rightIdx];
    }

    private int getElementsCountInRankRange(int idx, int low, int high, int left, int right, int[] segmentTree) {
        // No overlap:
        // contributes nothing.
        if(high<left || right<low) return 0;

        // Complete overlap:
        // directly reuse stored frequency count.
        if(left<=low && high<=right) return segmentTree[idx];

        int mid = low + (high-low)/2;
        int leftIdx = 2*idx + 1;
        int rightIdx = 2*idx + 2;

        // Partial overlap:
        // query both halves and merge counts.
        return getElementsCountInRankRange(leftIdx, low, mid, left, right, segmentTree) + getElementsCountInRankRange(rightIdx, mid+1, high, left, right, segmentTree);
    }
}