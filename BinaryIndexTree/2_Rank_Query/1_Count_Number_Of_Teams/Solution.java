import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int numTeams(int[] ratings) {

        int n = ratings.length;

        int count = 0;

        // --------------------------------------------------
        // Coordinate Compression
        // --------------------------------------------------
        //
        // Ratings can be large values.
        // Fenwick Tree works best on compact ranges: [1 ... n]
        // So we convert ratings into ranks.
        //
        // Example:
        // ratings: [100, 500, 200]
        // ranks: [1, 3, 2]
        int[] ranks = getRanks(ratings);


        // leftFenwickTree:
        // stores frequency of elements LEFT of current index
        //
        // rightFenwickTree:
        // stores frequency of elements RIGHT of current index
        int[] leftFenwickTree = new int[n + 1];
        int[] rightFenwickTree = new int[n + 1];


        // Initially: current index = 1
        // so:
        // left side contains: index 0
        //
        // right side contains: index 2 ... n-1
        build(leftFenwickTree, ranks, 0, 0);
        build(rightFenwickTree, ranks, 2, n - 1);


        // Treat every index as middle soldier.
        // We try to form:
        // increasing: left < mid < right
        //
        // decreasing: left > mid > right
        for (int i = 1; i < n - 1; i++) {
            int rank = ranks[i];
            int nextRank = ranks[i + 1];

            // Count smaller elements on LEFT:
            // query(rank-1) gives count of ranks smaller than current rank
            int leftLowerRankCount = query(leftFenwickTree, rank - 1);

            // Count smaller elements on RIGHT:
            int rightLowerRankCount = query(rightFenwickTree, rank - 1);

            // Count larger elements on RIGHT
            // totalRight - smallerOrEqual
            int rightHigherRankCount = query(rightFenwickTree, n) - query(rightFenwickTree, rank);


            // --------------------------------------------------
            // Count larger elements on LEFT
            // --------------------------------------------------
            int leftHigherRankCount =
                    query(leftFenwickTree, n)
                    - query(leftFenwickTree, rank);


            // Add increasing teams
            // left smaller × right greater
            // because: left < mid < right
            count += leftLowerRankCount * rightHigherRankCount;


            // Add decreasing teams:
            // left greater × right smaller
            // because: left > mid > right
            count += leftHigherRankCount * rightLowerRankCount;


            // Move current window forward:
            // current rank becomes part of LEFT side
            update(leftFenwickTree, rank, 1);
            // next element will no longer remain in RIGHT side
            update(rightFenwickTree, nextRank, -1);
        }
        return count;
    }


    private void build(int[] fenwickTree, int[] ranks, int start, int end) {

        // Insert all frequencies into BIT
        for (int i = start; i <= end; i++) {
            int rank = ranks[i];
            update(fenwickTree, rank, 1);
        }
    }


    private void update(int[] fenwickTree, int rank, int val) {

        // Standard BIT update
        //
        // Every parent range containing this rank
        // must also change.
        while (rank < fenwickTree.length) {
            fenwickTree[rank] += val;
            // move to next responsible node
            rank += (rank & -rank);
        }
    }


    private int query(int[] fenwickTree, int rank) {
        int prefixCount = 0;

        // Standard BIT prefix query:
        // Collect binary prefix chunks
        while (rank > 0) {
            prefixCount += fenwickTree[rank];
            // move to parent prefix chunk
            rank -= (rank & -rank);
        }
        return prefixCount;
    }


    private int[] getRanks(int[] ratings) {
        int n = ratings.length;
        int[] ranks = new int[n];
        Map<Integer, Integer> rankMap = new HashMap<>();

        // clone + sort for coordinate compression
        int[] sortedRatings = ratings.clone();
        Arrays.sort(sortedRatings);
        int rank = 1;


        // assign compressed rank
        //
        // IMPORTANT:
        // equal numbers must get same rank
        for (int rating : sortedRatings) {
            if (!rankMap.containsKey(rating)) {
                rankMap.put(rating, rank++);
            }
        }

        // build rank array
        for (int i = 0; i < n; i++) {
            ranks[i] = rankMap.get(ratings[i]);
        }

        return ranks;
    }
}