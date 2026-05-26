class RangeModule {

    // Dynamic Segment Tree root.
    //
    // Tree represents coordinate range:
    // [0 ... 1e9]
    //
    // Dynamic tree is necessary because:
    // coordinate range is huge,
    // but updates are sparse.
    Node root = new Node();

    public void addRange(int left, int right) {

        // Track interval.
        //
        // IMPORTANT:
        // problem intervals are:
        // [left, right)
        //
        // So actual inclusive range becomes:
        // [left, right-1]
        update(0, 1_000_000_000, left, right - 1, root, true);
    }

    public void removeRange(int left, int right) {

        // Remove tracking from interval.
        update(0, 1_000_000_000, left, right - 1, root, false);
    }

    public boolean queryRange(int left, int right) {

        // Check whether every point
        // inside interval is fully tracked.
        return query(0, 1_000_000_000, left, right - 1, root);
    }

    private void update(int low, int high, int left, int right, Node node, boolean val) {

        // No overlap:
        // current segment unaffected.
        if (high < left || right < low) return;

        // Complete overlap:
        // current segment fully lies inside update range.
        //
        // Entire segment becomes:
        // tracked OR untracked.
        //
        // pending:
        // stores lazy propagation state.
        if (left <= low && high <= right) {

            node.isTracked = val;

            // pending = 1:
            // entire segment tracked
            //
            // pending = -1:
            // entire segment untracked
            node.pending = val ? 1 : -1;
            return;
        }

        // Optimization:
        //
        // If current segment already has pending state
        // identical to required update,
        // recursion becomes unnecessary.
        //
        // Example:
        // whole segment already tracked
        // and update also wants tracking.
        if (node.pending != 0 && ((node.pending == 1) == val)) return;

        // Push pending lazy updates before deeper recursion.
        //
        // WHY?
        // Children must reflect correct state
        // before partial updates happen.
        if (node.pending != 0) push(node);

        int mid = low + (high - low) / 2;

        // Dynamic child creation.
        if (node.left == null) node.left = new Node();

        if (node.right == null) node.right = new Node();

        // Recurse into left half:
        // [low...mid]
        update(low, mid, left, right, node.left, val);

        // Recurse into right half:
        // [mid+1...high]
        update(mid + 1, high, left, right, node.right, val);

        // Current segment is fully tracked
        // only if BOTH children are fully tracked.
        node.isTracked = node.left.isTracked && node.right.isTracked;
    }

    private boolean query(int low, int high, int left, int right, Node node) {

        // No overlap contributes TRUE.
        //
        // WHY TRUE?
        // Query checks:
        // "is every point tracked?"
        //
        // No-overlap segment should not affect AND result.
        if (high < left || right < low) return true;

        // Pending state means:
        // entire current segment has same value.
        //
        // So recursion becomes unnecessary.
        if (node.pending != 0) return node.pending == 1;

        // Complete overlap:
        // directly reuse stored tracking state.
        if (left <= low && high <= right) return node.isTracked;

        int mid = low + (high - low) / 2;

        // Dynamic child creation.
        if (node.left == null) node.left = new Node();

        if (node.right == null) node.right = new Node();

        // Entire query range is tracked
        // only if BOTH halves are tracked.
        return query(low, mid, left, right, node.left) &&
               query(mid + 1, high, left, right, node.right);
    }

    private void push(Node node) {

        // No pending lazy update.
        if (node.pending == 0) return;

        // Create children if absent.
        if (node.left == null) node.left = new Node();

        if (node.right == null) node.right = new Node();

        boolean val = node.pending == 1;

        // Propagate tracking state into children.
        node.left.isTracked = val;

        node.right.isTracked = val;

        // Propagate lazy state into children.
        node.left.pending = node.pending;

        node.right.pending = node.pending;

        // Current lazy state fully propagated.
        node.pending = 0;
    }
}

class Node {

    // Whether entire segment is fully tracked.
    boolean isTracked = false;

    // Lazy propagation marker.
    //
    //  1 -> fully tracked
    // -1 -> fully untracked
    //  0 -> no pending update
    int pending = 0;

    // Dynamic child nodes.
    Node left, right;
}