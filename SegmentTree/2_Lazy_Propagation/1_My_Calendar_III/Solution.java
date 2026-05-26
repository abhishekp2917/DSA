class MyCalendarThree {

    // Dynamic Segment Tree root.
    //
    // Tree represents timeline:
    // [0 ... 1e9]
    //
    // Dynamic tree is necessary because:
    // coordinate range is huge,
    // but only few ranges are updated.
    Node root = new Node();

    public int book(int start, int end) {

        // Add booking interval.
        //
        // IMPORTANT:
        // end is exclusive in problem.
        //
        // So actual covered range becomes:
        // [start ... end-1]
        update(root, 0, 1_000_000_000, start, end - 1);

        // Root always stores:
        // maximum overlap across whole timeline.
        return root.max;
    }

    private void update(Node node, int l, int r, int start, int end) {

        // No overlap:
        // current segment unaffected.
        if (start > r || end < l) return;

        // Complete overlap:
        // current segment fully covered by booking.
        //
        // Increase overlap count for entire segment.
        //
        // lazy means:
        // every child inside this segment
        // should also eventually receive this increment.
        if (start <= l && r <= end) {
            node.max += 1;
            node.lazy += 1;
            return;
        }

        // Push pending lazy updates before going deeper.
        //
        // WHY?
        // Children must reflect correct overlap counts
        // before further recursion.
        push(node);

        int mid = l + (r - l) / 2;

        // Dynamic child creation:
        // create nodes only when traversal needs them.
        if (node.left == null) node.left = new Node();

        if (node.right == null) node.right = new Node();

        // Recurse into left half:
        // [l...mid]
        update(node.left, l, mid, start, end);

        // Recurse into right half:
        // [mid+1...r]
        update(node.right, mid + 1, r, start, end);

        // Merge child answers.
        //
        // Current segment maximum overlap =
        // max overlap among children.
        //
        // node.lazy contribution already exists
        // inside child max values because push propagated it.
        node.max = Math.max(node.left.max, node.right.max);
    }

    private void push(Node node) {

        // No pending lazy update.
        if (node.lazy != 0) {

            // Create children if absent.
            if (node.left == null) node.left = new Node();

            if (node.right == null) node.right = new Node();

            // Propagate pending overlap increment
            // into left child.
            node.left.max += node.lazy;

            // Propagate pending overlap increment
            // into right child.
            node.right.max += node.lazy;

            // Store lazy increment for future descendants.
            node.left.lazy += node.lazy;

            node.right.lazy += node.lazy;

            // Current node lazy fully propagated.
            node.lazy = 0;
        }
    }
}

class Node {

    // Maximum overlap inside this segment.
    int max = 0;

    // Pending lazy propagation increment.
    //
    // Means:
    // all descendants inside this segment
    // should eventually receive this increment.
    int lazy = 0;

    // Dynamic children.
    Node left, right;
}