class NumArray {

    // --------------------------------------------------
    // fenwickTree[i]
    // stores partial prefix information
    // --------------------------------------------------
    //
    // IMPORTANT:
    // Fenwick Tree uses 1-based indexing
    //
    // fenwickTree[i] stores sum of range:
    //
    //      (i - lowbit(i) + 1) → i
    //
    // where:
    //      lowbit(i) = i & -i
    //
    int[] fenwickTree;

    // Original array
    int[] nums;

    int n;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================
    public NumArray(int[] nums) {

        this.n = nums.length;

        // Clone original array
        this.nums = nums.clone();

        // BIT size = n+1 because BIT is 1-indexed
        this.fenwickTree = new int[n + 1];

        build(nums);
    }


    // ==================================================
    // UPDATE INDEX
    // ==================================================
    //
    // Update nums[i] = val
    //
    // Fenwick tree stores PREFIX INFORMATION,
    // so changing one value affects MULTIPLE nodes
    //
    public void update(int i, int val) {

        // Difference added to array
        int delta = val - this.nums[i];

        // Update original array
        this.nums[i] = val;

        // Convert to 1-indexed
        i++;

        // --------------------------------------------------
        // Propagate delta upward
        // --------------------------------------------------
        //
        // Move to next responsible node
        //
        // i += lowbit(i)
        while (i <= n) {

            this.fenwickTree[i] += delta;

            i += (i & -i);
        }
    }


    // ==================================================
    // RANGE SUM QUERY
    // ==================================================
    //
    // sum(left → right)
    //
    // = prefix(right) - prefix(left-1)
    //
    public int sumRange(int left, int right) {

        return query(right) - query(left - 1);
    }


    // ==================================================
    // BUILD BIT IN O(n)
    // ==================================================
    //
    // Standard insertion build:
    //      O(n log n)
    //
    // This optimized version:
    //      O(n)
    //
    private void build(int[] nums) {

        for (int i = 1; i <= n; i++) {

            // Add current value
            this.fenwickTree[i] += nums[i - 1];

            // --------------------------------------------------
            // Push contribution upward
            // --------------------------------------------------
            //
            // next responsible parent
            int next = i + (i & -i);

            if (next <= n) {

                this.fenwickTree[next] += this.fenwickTree[i];
            }
        }
    }


    // ==================================================
    // PREFIX SUM QUERY
    // ==================================================
    //
    // query(i)
    // = sum of nums[0 → i]
    //
    private int query(int i) {

        // Convert to 1-indexed
        i++;

        int prefixSum = 0;

        // --------------------------------------------------
        // Move upward toward root
        // --------------------------------------------------
        //
        // i -= lowbit(i)
        //
        // Each step removes last set bit
        while (i > 0) {

            prefixSum += this.fenwickTree[i];

            i -= (i & -i);
        }

        return prefixSum;
    }
}