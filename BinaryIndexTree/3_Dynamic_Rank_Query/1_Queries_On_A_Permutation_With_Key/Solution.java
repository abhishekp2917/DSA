class Solution {

    public int[] processQueries(int[] queries, int m) {

        int n = queries.length;

        int[] result = new int[n];

        // We reserve first n positions for future "move to front" operations.
        //
        // Initial arrangement:
        //
        // [empty...empty][1,2,3,4...m]
        //
        // This allows moving queried numbers to front
        // without shifting elements physically.
        int lowestRank = n;
        
        // Actual numbers start after reserved front space.
        int highestRank = n + 1;

        // rankMap[num] = current rank(position) of number.
        int[] rankMap = new int[m + 1];

        // Initial positions:
        //
        // 1 -> n+1
        // 2 -> n+2
        // ...
        for(int i=1; i<=m; i++) {
            rankMap[i] = highestRank++;
        }

        // Fenwick Tree stores frequency of occupied ranks.
        //
        // Position contains:
        //      1 -> number exists
        //      0 -> empty
        int[] fenwickTree = new int[highestRank];

        // Build initial BIT frequencies.
        build(fenwickTree, m, rankMap);

        for(int i=0; i<n; i++) {

            int num = queries[i];

            // Current position of queried number.
            int oldRank = rankMap[num];

            // New front position.
            //
            // We keep decreasing because front grows backward.
            int newRank = lowestRank--;

            // query(oldRank - 1):
            // gives count of occupied positions before oldRank.
            //
            // That directly equals current index of number.
            int lowerRankCount = query(fenwickTree, oldRank - 1);

            // Remove number from old position.
            update(fenwickTree, oldRank, -1);

            // Insert number at front position.
            update(fenwickTree, newRank, 1);

            // Update current position.
            rankMap[num] = newRank;

            result[i] = lowerRankCount;
        }

        return result;
    }


    private int query(int[] fenwickTree, int rank) {

        int rankCount = 0;

        // Standard BIT prefix query:
        // collect occupied positions from [1...rank]
        while(rank > 0) {
            rankCount += fenwickTree[rank];

            // move to parent prefix chunk
            rank -= (rank & -rank);
        }
        return rankCount;
    }


    private void update(int[] fenwickTree, int rank, int val) {
        // Standard BIT update:
        // update all parent binary ranges containing this position.
        while(rank < fenwickTree.length) {
            fenwickTree[rank] += val;

            // move to next responsible parent range
            rank += (rank & -rank);
        }
    }


    private void build(int[] fenwickTree, int m, int[] rankMap) {

        // O(n) BIT build.
        //
        // Insert all initial positions into BIT.
        for(int num=1; num<=m; num++) {
            int rank = rankMap[num];
            fenwickTree[rank]++;

            // push contribution upward
            int nextRank = rank + (rank & -rank);
            if(nextRank < fenwickTree.length) {
                fenwickTree[nextRank] += fenwickTree[rank];
            }
        }
    }
}