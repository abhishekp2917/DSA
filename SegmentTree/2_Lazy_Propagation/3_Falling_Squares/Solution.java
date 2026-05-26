import java.util.ArrayList;
import java.util.List;

class Solution {

    public List<Integer> fallingSquares(int[][] positions) {

        List<Integer> ans = new ArrayList<>();

        // Dynamic Segment Tree root.
        //
        // Tree represents coordinate range:
        // [0 ... 1e9]
        //
        // Dynamic tree is necessary because:
        // coordinates are huge,
        // but only few ranges are updated.
        Node root = new Node();

        int globalMaxHeight = 0;

        for(int[] position : positions) {

            int length = position[1];

            int start = position[0];

            // IMPORTANT:
            // square occupies:
            // [start, start+length-1]
            //
            // because side length is inclusive.
            int end = start + length - 1;

            // Find highest existing stack
            // underneath current square.
            //
            // Current square will sit on top of this height.
            int baseHeight =
                getBaseHeight(0, 1000_000_000, start, end, root);

            // New height after placing square.
            int maxHeight = baseHeight + length;

            // Update entire covered interval
            // with new height.
            update(0, 1000_000_000, start, end, root, maxHeight);

            // Maintain global maximum stack height.
            globalMaxHeight = Math.max(globalMaxHeight, maxHeight);

            ans.add(globalMaxHeight);
        }

        return ans;
    }

    private int getBaseHeight(int low, int high, int start, int end, Node node) {

        // No overlap:
        // contributes no height.
        if(high<start || end<low) return 0;

        // Complete overlap:
        // current segment fully inside query range.
        //
        // Reuse stored maximum height directly.
        if(start<=low && high<=end) {
            return node.maxHeight;
        }

        // Push pending lazy updates before deeper recursion.
        //
        // WHY?
        // Children must contain correct heights
        // before querying partial overlaps.
        push(node);

        int mid = low + (high-low)/2;

        // Query both halves and take maximum.
        //
        // We need tallest stack under current square.
        return Math.max(
            getBaseHeight(low, mid, start, end, node.left), 
            getBaseHeight(mid+1, high, start, end, node.right));
    }

    private void update(int low, int high, int start, int end, Node node, int maxHeight) {

        // No overlap:
        // current segment unaffected.
        if(high<start || end<low) return;

        // Complete overlap:
        // current segment fully covered by square.
        //
        // Entire segment height becomes maxHeight.
        //
        // pending stores lazy propagation value.
        if(start<=low && high<=end) {

            node.maxHeight = maxHeight;

            node.pending = maxHeight;

            return;
        }

        // Push pending updates before recursion.
        push(node);

        int mid = low + (high-low)/2;

        // Update left half:
        // [low...mid]
        update(low, mid, start, end, node.left, maxHeight);

        // Update right half:
        // [mid+1...high]
        update(mid+1, high, start, end, node.right, maxHeight);

        // Merge child answers.
        //
        // Current segment height =
        // max height among children.
        node.maxHeight =
            Math.max(node.left.maxHeight, node.right.maxHeight);
    }

    private void push(Node node) {

        // Dynamic child creation.
        if(node.left==null) node.left = new Node();

        if(node.right==null) node.right = new Node();

        // No pending lazy update.
        if(node.pending!=0) {

            // Propagate pending height into left child.
            node.left.maxHeight = node.pending;

            // Propagate pending height into right child.
            node.right.maxHeight = node.pending;

            // Store lazy value for future descendants.
            node.left.pending = node.pending;

            node.right.pending = node.pending;

            // Current lazy state fully propagated.
            node.pending = 0;
        }
    }
}

class Node {

    // Maximum height inside this segment.
    int maxHeight;

    // Pending lazy propagation height.
    //
    // Means:
    // all descendants inside this segment
    // should eventually become this height.
    int pending;

    // Dynamic child nodes.
    Node left, right;
}