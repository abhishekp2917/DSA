class NumArray {

    int[] segmentTree;
    int n;

    public NumArray(int[] nums) {
        n = nums.length;

        // Segment Tree is stored inside array representation.
        //
        // For node at index i:
        //
        // left child  -> 2*i + 1
        // right child -> 2*i + 2
        //
        // 4*n size safely fits complete recursive tree
        // even when n is not power of 2.
        segmentTree = new int[4 * n];

        // Build Segment Tree from original array.
        build(0, 0, n - 1, nums);
    }


    public void update(int index, int val) {
        // Update one array index
        // and propagate changes upward.
        //
        // Only one root-to-leaf path changes,
        // so complexity remains O(log n).
        updateSegmentTree(0, 0, n - 1, index, val);
    }


    public int sumRange(int left, int right) {
        // Query sum inside range [left, right]
        //
        // Segment Tree avoids scanning entire range
        // by reusing already computed segment sums.
        return query(0, 0, n - 1, left, right);
    }


    private void build(int idx, int low, int high, int[] nums) {
        // Leaf node:
        // segment contains exactly one element.
        //
        // So tree node directly stores array value.
        if(low == high) {
            this.segmentTree[idx] = nums[low];
            return;
        }

        int mid = low + (high - low) / 2;
        int leftIdx = 2 * idx + 1;
        int rightIdx = 2 * idx + 2;

        // Recursively build left half:
        // [low ... mid]
        build(leftIdx, low, mid, nums);

        // Recursively build right half:
        // [mid+1 ... high]
        build(rightIdx, mid + 1, high, nums);


        // Internal node stores merged result
        // of left and right child segments.
        //
        // Merge operation here is SUM.
        //
        // Meaning:
        // current node stores sum of:
        // [low ... high]
        this.segmentTree[idx] =
            this.segmentTree[leftIdx]
            + this.segmentTree[rightIdx];
    }


    private void updateSegmentTree(int idx, int low, int high, int index, int val) {
        // Reached target leaf node.
        // Replace old array value with new value.
        if(low == high) {
            this.segmentTree[idx] = val;
            return;
        }

        int mid = low + (high - low) / 2;
        int leftIdx = 2 * idx + 1;
        int rightIdx = 2 * idx + 2;

        // Only ONE half can contain target index.
        //
        // So recurse only into relevant child.
        //
        // This is why update complexity becomes O(log n)
        // instead of O(n).
        if(index <= mid) {
            updateSegmentTree(leftIdx, low, mid, index, val);
        }
        else {
            updateSegmentTree(rightIdx, mid + 1, high, index, val);
        }


        // Child subtree has been updated.
        //
        // Recompute current segment sum using updated children.
        //
        // Changes propagate upward while recursion backtracks.
        this.segmentTree[idx] = this.segmentTree[leftIdx] + this.segmentTree[rightIdx];
    }


    private int query( int idx, int low, int high, int left, int right) {

        // COMPLETE OVERLAP:
        //
        // Current segment lies completely inside query range.
        //
        // Reuse precomputed segment sum directly.
        //
        // This is the main optimization of Segment Tree.
        if(left <= low && high <= right) {
            return this.segmentTree[idx];
        }


        // NO OVERLAP:
        //
        // Current segment contributes nothing
        // to required query range.
        //
        // Since merge operation is SUM,
        // identity element becomes 0.
        if(left > high || low > right) { return 0; }


        // PARTIAL OVERLAP:
        //
        // Some part contributes and some part doesn't.
        //
        // Split query into left and right halves.
        int mid = low + (high - low) / 2;
        int leftIdx = 2 * idx + 1;
        int rightIdx = 2 * idx + 2;
        int leftSum = query(leftIdx, low, mid, left, right);
        int rightSum = query(rightIdx, mid + 1, high, left, right);

        // Merge left and right contributions.
        return leftSum + rightSum;
    }
}