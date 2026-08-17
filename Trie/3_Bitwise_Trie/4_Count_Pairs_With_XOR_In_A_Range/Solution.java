class Solution {
    public int countPairs(int[] nums, int low, int high) {
        // Convert the range condition:
        // low <= (nums[i] ^ nums[j]) <= high
        //
        // into:
        // count(XOR < high + 1) - count(XOR < low).
        //
        // Using two separate Tries ensures the two counts are independent.
        return count(nums, new Trie(), high+1) - count(nums, new Trie(), low);
    }

    private int count(int[] nums, Trie root, int high) {
        int count = 0;

        for(int num : nums) {

            // Count previously inserted numbers whose XOR with num
            // is strictly smaller than high.
            //
            // We query before inserting num so that every pair is counted
            // exactly once, when its second element is processed.
            count += query(num, root, high);

            // Make num available for all subsequent numbers.
            insert(num, root);
        }

        return count;
    }

    private void insert(int num, Trie root) {

        // Store the number bit by bit in a binary Trie.
        // count at every node tells us how many inserted numbers
        // have passed through that node.
        for(int i=31; i>=0; i--) {
            int bit = (num>>i)&1;

            if(root.map[bit]==null) root.map[bit] = new Trie();

            root = root.map[bit];
            root.count++;
        }
    }

    private int query(int num, Trie root, int high) {
        int count = 0;

        // We want to count numbers x such that:
        //
        //     num ^ x < high
        //
        // Process from the most significant bit because the first
        // differing bit determines which number is smaller.
        for(int i=31; i>=0; i--) {
            if(root==null) break;

            int bit = (num>>i)&1;
            int oppBit = bit^1;
            int highBit = (high>>i)&1;

            if(highBit==1) {

                // If x has the SAME bit as num here, then:
                //
                //     numBit ^ xBit = 0
                //
                // while high has 1.
                //
                // Therefore the XOR is already smaller at this bit,
                // regardless of all remaining lower bits.
                //
                // Every number in this branch is therefore valid.
                if(root.map[bit]!=null) {
                    count += root.map[bit].count;
                }

                // To continue looking for XOR values that are still equal
                // to high in all processed bits, we must choose the opposite
                // bit, producing XOR bit 1.
                root = root.map[oppBit];
            }

            else {
                // high has 0 at this bit.
                //
                // To keep XOR < high possible, XOR must also be 0 here.
                // Therefore x must have the same bit as num.
                root = root.map[bit];
            }
        }

        return count;
    }
}


class Trie {
    // map[0] -> numbers having bit 0
    // map[1] -> numbers having bit 1
    Trie[] map;

    // Number of inserted values whose binary path passes through
    // this node. Used to count an entire valid subtree in O(1).
    int count;

    Trie() {
        this.map = new Trie[2];
    }
}