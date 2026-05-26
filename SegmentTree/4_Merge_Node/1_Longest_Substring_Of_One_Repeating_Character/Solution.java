class Solution {

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {

        int n = s.length();
        int q = queryIndices.length;
        int[] result = new int[q];

        // Segment Tree node stores:
        //
        // 1. longest repeating substring inside segment
        // 2. longest repeating prefix
        // 3. longest repeating suffix
        // 4. prefix character
        // 5. suffix character
        //
        // These are enough to merge two child segments.
        Node[] segmentTree = new Node[4*n];
        for(int i=0; i<4*n; i++) segmentTree[i] = new Node();

        // Build tree from original string.
        build(0, 0, n-1, s, segmentTree);
        for(int i=0; i<q; i++) {
            char ch = queryCharacters.charAt(i);
            int idx = queryIndices[i];

            // Update character at given index.
            update(0, 0, n-1, idx, ch, segmentTree);

            // Root always stores answer for whole string.
            result[i] = segmentTree[0].longestLen;
        }

        return result;
    }

    private void update(int idx, int low, int high, int i, char ch, Node[] segmentTree) {

        Node node = segmentTree[idx];

        // No overlap:
        // current segment does not contain target index.
        if(low>i || high<i) return;

        // Leaf node:
        // replace character at this position.
        //
        // Single character segment always has:
        // prefixLen = suffixLen = longestLen = 1
        if(low==high && low==i) {
            node.prefixLen = 1;
            node.suffixLen = 1;
            node.prefixChar = ch;
            node.suffixChar = ch;
            node.longestLen = 1;
            return;
        }
        int mid = low + (high-low)/2;
        int leftIdx = 2*idx + 1;
        int rightIdx = 2*idx + 2;

        // Only one side contains target index,
        // but calling both keeps implementation simpler.
        update(leftIdx, low, mid, i, ch, segmentTree);
        update(rightIdx, mid+1, high, i, ch, segmentTree);

        // Recompute current node after child update.
        modifyNode(idx, low, mid, high, segmentTree);
    }

    private void build(int idx, int low, int high, String s, Node[] segmentTree) {

        Node node = segmentTree[idx];

        // Leaf node:
        // single character segment.
        if(low==high) {
            char ch = s.charAt(low);
            node.prefixLen = 1;
            node.suffixLen = 1;
            node.prefixChar = ch;
            node.suffixChar = ch;
            node.longestLen = 1;
            return;
        }
        int mid = low + (high-low)/2;
        int leftIdx = 2*idx + 1;
        int rightIdx = 2*idx + 2;

        // Build left half:
        // [low...mid]
        build(leftIdx, low, mid, s, segmentTree);

        // Build right half:
        // [mid+1...high]
        build(rightIdx, mid+1, high, s, segmentTree);

        // Merge child information into current node.
        modifyNode(idx, low, mid, high, segmentTree);
    }

    private void modifyNode(int idx, int low, int mid, int high, Node[] segmentTree) {

        int leftIdx = 2*idx + 1;
        int rightIdx = 2*idx + 2;
        Node node = segmentTree[idx];
        Node leftNode = segmentTree[leftIdx];
        Node rightNode = segmentTree[rightIdx];
        int leftLen = mid-low+1;
        int rightLen = high-mid;

        // Current segment prefix character
        // comes from left segment prefix.
        node.prefixChar = leftNode.prefixChar;

        // Current segment suffix character
        // comes from right segment suffix.
        node.suffixChar = rightNode.suffixChar;

        // Important merge condition:
        //
        // If left suffix character equals right prefix character,
        // then repeating substring can extend across boundary.
        if(leftNode.suffixChar==rightNode.prefixChar) {

            // Combined repeating substring crossing midpoint.
            int combinedLen = leftNode.suffixLen + rightNode.prefixLen;

            // Longest substring may come from:
            // 1. left segment
            // 2. right segment
            // 3. crossing midpoint
            node.longestLen = Math.max(combinedLen, Math.max(leftNode.longestLen, rightNode.longestLen));

            // Entire left segment forms repeating prefix,
            // so prefix can extend into right prefix.
            //
            // Example:
            // left = "aaa"
            // right starts with "aa"
            //
            // merged prefix = "aaaaa"
            node.prefixLen = (leftLen==leftNode.prefixLen) ? combinedLen : leftNode.prefixLen;

            // Entire right segment forms repeating suffix,
            // so suffix can extend into left suffix.
            node.suffixLen = (rightLen==rightNode.suffixLen) ? combinedLen : rightNode.suffixLen;
        }

        // Boundary characters differ,
        // so repeating substring cannot cross midpoint.
        else {
            node.prefixLen = leftNode.prefixLen;
            node.suffixLen = rightNode.suffixLen;
            node.longestLen = Math.max(leftNode.longestLen, rightNode.longestLen);
        }
    }
}

class Node {
    
    int prefixLen; // Length of longest repeating prefix.
    int suffixLen; // Length of longest repeating suffix.
    char prefixChar; // Character forming prefix.
    char suffixChar; // Character forming suffix.    
    int longestLen; // Longest repeating substring inside segment.
}