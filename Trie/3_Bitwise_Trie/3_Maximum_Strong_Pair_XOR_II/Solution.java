import java.util.Arrays;

class Solution {
    public int maximumStrongPairXor(int[] nums) {
        int n = nums.length;
        int maxXor = 0;
        Trie trie = new Trie();

        // Sort so that for nums[i], all valid partners form a contiguous
        // range [nums[i], 2 * nums[i]].
        Arrays.sort(nums);

        int i=0, j=0;

        while(i<n) {

            // nums[i] is the smallest number in the current window.
            // For any nums[j] >= nums[i], the strong-pair condition
            // |nums[i]-nums[j]| <= min(nums[i], nums[j])
            // becomes nums[j]-nums[i] <= nums[i],
            // hence nums[j] <= 2 * nums[i].
            while(j<n && nums[j]<=nums[i]*2) {
                insert(nums[j], trie);
                j++;
            }

            // The Trie contains exactly the numbers that can form
            // a strong pair with nums[i], so find the best XOR partner.
            maxXor = Math.max(maxXor, maxXor(nums[i], trie));

            // nums[i] will no longer be the left boundary after this iteration.
            // Remove its Trie path before moving to the next value.
            remove(nums[i], trie);
            i++;
        }

        return maxXor;
    }

    private int maxXor(int num, Trie root) {
        int maxXor = 0;

        // Process from the most significant bit because maximizing
        // a higher XOR bit is more important than all lower bits.
        for(int i=31; i>=0; i--) {
            if(root==null) return 0;

            int bit = (num>>i)&1;
            int oppBit = bit^1;

            // Prefer the opposite bit because it makes the current
            // XOR bit equal to 1.
            if(root.map[oppBit]!=null) {
                root = root.map[oppBit];
                maxXor |= (1<<i);
            }

            // If the opposite bit is unavailable, the same bit
            // is the only possible choice and this XOR bit becomes 0.
            else root = root.map[bit];
        }

        return maxXor;
    }

    private void insert(int num, Trie root) {

        // Insert the number as a 32-bit binary path.
        for(int i=31; i>=0; i--) {
            int bit = (num>>i)&1;

            // Create the required path if it does not already exist.
            if(root.map[bit]==null) root.map[bit] = new Trie();

            root = root.map[bit];
        }
    }

    private void remove(int num, Trie root) {

        // lca points to the deepest node where the path of num
        // has a branching point. Only the path after this point
        // belongs exclusively to num and can be safely removed.
        Trie lca = root;

        // idx stores which child of lca corresponds to num.
        int idx = (num>>31)&1;

        for(int i=31; i>=0; i--) {
            int bit = (num>>i)&1;

            // The number does not exist in the Trie.
            if(root.map[bit]==null) return;

            // If both children exist, other numbers use the opposite
            // branch, so everything up to this point is shared.
            // Keep this node as the last branching point.
            if(root.map[0]!=null && root.map[1]!=null) {
                lca = root;
                idx = bit;
            }

            root = root.map[bit];
        }

        // Everything below the last branching point belongs only
        // to this number, so detach that entire suffix of the path.
        //
        // Duplicate values do not cause an issue here: if another copy
        // exists, it follows exactly the same path and contributes
        // XOR = 0 with the current nums[i], so keeping that duplicate
        // is unnecessary for maximizing XOR.
        lca.map[idx] = null;
    }
}


class Trie {
    // map[0] represents bit 0 and map[1] represents bit 1.
    Trie[] map;

    Trie() {
        this.map = new Trie[2];
    }
}