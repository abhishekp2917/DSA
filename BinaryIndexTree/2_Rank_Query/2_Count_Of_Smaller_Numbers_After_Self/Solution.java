import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    public List<Integer> countSmaller(int[] nums) {

        int n = nums.length;

        // answer[i] = count of smaller elements on RIGHT of nums[i]
        Integer[] smallestNumCount = new Integer[n];

        // Coordinate Compression:
        // Fenwick Tree works on compact ranges efficiently.
        // Since nums may contain large/negative values,
        // we convert every unique number into rank.
        //
        // Example:
        // nums  = [100, 500, 200]
        // ranks = [1, 3, 2]
        Map<Integer, Integer> rankMap = getNumToRankMap(nums);

        // Fenwick Tree stores frequency of processed ranks.
        //
        // fenwickTree[i] stores frequency contribution
        // for a binary prefix chunk ending at i.
        int[] fenwickTree = new int[n + 1];

        // Process from RIGHT to LEFT.
        //
        // WHY?
        // While processing nums[i],
        // all previously processed elements belong to RIGHT side.
        //
        // So BIT automatically represents:
        //      elements on right of current index
        for (int i = n - 1; i >= 0; i--) {

            int rank = rankMap.get(nums[i]);

            // query(rank - 1):
            // gives count of processed elements
            // having smaller rank than current number.
            //
            // We use rank - 1 because we need STRICTLY smaller elements.
            smallestNumCount[i] = query(fenwickTree, rank - 1);

            // Insert current rank into BIT.
            //
            // So future left-side elements can use it
            // while calculating their answer.
            update(fenwickTree, rank);
        }

        return Arrays.asList(smallestNumCount);
    }


    private void update(int[] fenwickTree, int rank) {

        int n = fenwickTree.length;

        // Standard BIT update.
        //
        // Every parent binary range containing this rank
        // must also increase its frequency.
        while (rank < n) {

            fenwickTree[rank]++;

            // Move to next responsible parent range.
            //
            // lowbit(rank) gives size of current binary chunk.
            rank += (rank & -rank);
        }
    }


    private int query(int[] fenwickTree, int rank) {

        int prefixSum = 0;

        // Standard BIT prefix query.
        //
        // Collect all binary prefix chunks contributing to:
        //      [1 ... rank]
        while (rank > 0) {

            prefixSum += fenwickTree[rank];

            // Move to parent prefix chunk.
            //
            // Removing last set bit moves upward in BIT tree.
            rank -= (rank & -rank);
        }

        return prefixSum;
    }


    private Map<Integer, Integer> getNumToRankMap(int[] nums) {

        Map<Integer, Integer> rankMap = new HashMap<>();

        // Clone + sort for coordinate compression.
        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums);

        int rank = 1;

        // Assign compressed ranks.
        //
        // IMPORTANT:
        // Equal numbers must get same rank,
        // because only relative ordering matters.
        for (int num : sortedNums) {

            if (!rankMap.containsKey(num)) {
                rankMap.put(num, rank++);
            }
        }

        return rankMap;
    }
}