class CountIntervals {

    // Dynamic Segment Tree root.
    //
    // Tree represents range:
    // [0 ... 1e9]
    //
    // Dynamic tree is required because:
    // coordinate range is huge,
    // but only few intervals are updated.
    private Node root;

    public CountIntervals() {

        this.root = new Node();    
    }
    
    public void add(int left, int right) {

        // Add interval into Segment Tree.
        addSegment(0, 1000_000_000, left, right, this.root);
    }

    private void addSegment(int low, int high, int left, int right, Node node) {

        // No overlap:
        // current segment contributes nothing.
        //
        // OR
        //
        // Current segment already fully covered.
        //
        // Example:
        // current segment size = 10
        // node.count = 10
        //
        // meaning:
        // entire segment already marked.
        //
        // So no need to recurse further.
        if(high<left || right<low || (high-low+1)==node.count) return;

        // Complete overlap:
        // current segment fully lies inside update range.
        //
        // Mark entire segment as covered.
        //
        // count = segment length
        if(left<=low && high<=right) {

            node.count = (high-low+1);

            return;
        }

        int mid = low + (high-low)/2;

        // Dynamic node creation:
        // child nodes created only when needed.
        //
        // This saves huge memory for sparse updates.
        if(node.left==null) node.left = new Node();

        if(node.right==null) node.right = new Node();

        // Recurse into left half:
        // [low...mid]
        addSegment(low, mid, left, right, node.left);

        // Recurse into right half:
        // [mid+1...high]
        addSegment(mid+1, high, left, right, node.right);

        // Merge child results.
        //
        // Current node count =
        // covered count in left + right halves.
        node.count = node.left.count + node.right.count;
    }
    
    public int count() {

        // Root stores total covered integers
        // across entire range.
        return this.root.count;
    }
}

class Node {

    // count:
    // number of covered integers
    // inside this segment.
    int count;

    // Dynamic child nodes.
    Node left, right;
}