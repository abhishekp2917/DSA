import java.util.Arrays;

class Solution {

    public int[] maximizeXor(int[] nums, int[][] queries) {
        int n = queries.length;
        int[] maxXor = new int[n];
        Trie1 root = new Trie1();

        // Store each query as [x, m, originalIndex].
        // originalIndex is needed because sorting queries by m changes their
        // original order, while the answer must be returned in that order.
        int[][] qArr = new int[n][3];

        for (int i=0; i<n; i++) {
            qArr[i][0] = queries[i][0];
            qArr[i][1] = queries[i][1];
            qArr[i][2] = i;
        }

        // Process queries in increasing order of m so that we can
        // incrementally add only numbers satisfying nums[i] <= m.
        Arrays.sort(qArr, (a, b) -> a[1] - b[1]);

        // Sorting nums allows us to add eligible numbers incrementally
        // without scanning nums again for every query.
        Arrays.sort(nums);

        int i = 0; 

        for (int[] query : qArr) {
            int x = query[0];
            int m = query[1];
            int idx = query[2];

            // Add every newly eligible number to the Trie.
            // Since queries are processed by increasing m, numbers already
            // inserted remain valid for all later queries.
            while (i<nums.length && nums[i]<=m) {
                insert(root, nums[i]);
                i++;
            }

            // If no number has been inserted, there is no nums[i] <= m,
            // so this query has no valid answer.
            if(i==0) maxXor[idx] = -1;

            // The Trie now contains exactly the numbers satisfying nums[i] <= m,
            // so we can find the maximum XOR without checking m again.
            else maxXor[idx] = getMaxXor(root, x);
        }

        // Answers were stored using the original query indices,
        // restoring the required input order.
        return maxXor;
    }

    private void insert(Trie1 root, int num) {
        Trie1 node = root;

        // Store the number as a 32-bit binary path.
        // The Trie is later traversed in the same MSB-to-LSB order.
        for (int i=31; i>=0; i--) {
            int bit = (num>>i) & 1;

            // Create the required bit path if it does not exist.
            if (node.map[bit] == null) node.map[bit] = new Trie1();

            node = node.map[bit];
        }
    }

    private int getMaxXor(Trie1 root, int x) {
        int maxXor = 0;

        // Process higher bits first because maximizing a higher XOR bit
        // is always more important than any combination of lower bits.
        for (int i=31; i>=0; i--) {
            int bit = (x>>i) & 1;
            int oppBit = bit^1;

            // Prefer the opposite bit because it makes the current XOR bit 1.
            // Since the Trie contains only numbers <= m, no additional
            // constraint check is necessary here.
            if (root.map[oppBit]!=null) {
                maxXor |= (1<<i);
                root = root.map[oppBit];
            }

            // If the opposite bit does not exist, we must take the same bit,
            // making the current XOR bit 0.
            else root = root.map[bit];
        }

        return maxXor;
    }
}


class Trie1 {

    // map[0] represents a child containing bit 0,
    // while map[1] represents a child containing bit 1.
    Trie1[] map = new Trie1[2];
}

class Solution2 {

    public int[] maximizeXor(int[] nums, int[][] queries) {
        int q = queries.length;
        int[] maxXor = new int[q];
        Trie2 root = new Trie2();

        // Insert every number into a binary Trie.
        // Each Trie node will also store the smallest number that passes through it,
        // allowing us to determine whether that entire Trie branch contains
        // at least one number <= the query limit m.
        for(int num : nums) insert(root, num);

        for (int i=0; i<q; i++) {
            int x = queries[i][0];
            int m = queries[i][1];

            // Find the maximum XOR with x among numbers whose value <= m.
            maxXor[i] = getMaxXor(root, x, m);
        }

        return maxXor;
    }

    private void insert(Trie2 root, int num) {
        Trie2 node = root;

        // Represent the number as a 32-bit binary path.
        // Processing from MSB to LSB allows the query to greedily maximize
        // the most significant possible XOR bits.
        for (int i=31; i>=0; i--) {
            int bit = (num>>i) & 1;

            // Create the required bit path if it does not exist.
            if (node.map[bit] == null) node.map[bit] = new Trie2();

            node = node.map[bit];

            // Store the smallest number passing through this node.
            // If minNum <= m, this branch contains at least one valid
            // number satisfying the query's upper-bound constraint.
            node.minNum = Math.min(node.minNum, num);
        }
    }

    private int getMaxXor(Trie2 root, int x, int m) {
        int maxXor = 0;

        // Greedily process from the most significant bit because
        // a higher XOR bit contributes more to the final answer.
        for (int i=31; i>=0; i--) {
            int bit = (x>>i) & 1;
            int oppBit = bit^1;

            // Prefer the opposite bit because it makes the current XOR bit 1.
            // But we can take this branch only if it contains at least one
            // number <= m.
            if (root.map[oppBit]!=null && root.map[oppBit].minNum<=m) {
                maxXor |= (1<<i);
                root = root.map[oppBit];
            } 

            // If the opposite branch is unavailable, try the same bit.
            // This branch is valid only if it also contains a number <= m.
            else if(root.map[bit]!=null && root.map[bit].minNum<=m) root = root.map[bit];

            // Neither branch contains a valid number <= m,
            // so no number satisfies the query constraint.
            else return -1;
        }

        return maxXor;
    }
}


class Trie2 {

    // map[0] stores numbers whose current bit is 0,
    // while map[1] stores numbers whose current bit is 1.
    Trie2[] map;

    // Smallest number among all numbers whose binary path passes through
    // this node. It lets us check the <= m constraint for the entire branch.
    int minNum;

    Trie2() {
        map = new Trie2[2];
        minNum = Integer.MAX_VALUE;
    } 
}