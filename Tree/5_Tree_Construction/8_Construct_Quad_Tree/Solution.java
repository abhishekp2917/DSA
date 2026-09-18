class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;

    
    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }
    
    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }
    
    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}

class Solution {

    public Node construct(int[][] grid) {
        int n = grid.length;
        return buildQuadTree(grid, 0, 0, n);
    }

    private Node buildQuadTree(int[][] grid, int startRow, int startCol, int size) {
        if (size==1) return new Node(grid[startRow][startCol] == 1, true);
        int halfSize = size / 2;
        Node topLeft = buildQuadTree(grid, startRow, startCol, halfSize);
        Node topRight = buildQuadTree(grid, startRow, startCol + halfSize, halfSize);
        Node bottomLeft = buildQuadTree(grid, startRow + halfSize, startCol, halfSize);
        Node bottomRight = buildQuadTree(grid, startRow + halfSize, startCol + halfSize, halfSize);

        boolean isLeaf =
                topLeft.isLeaf &&
                topRight.isLeaf &&
                bottomLeft.isLeaf &&
                bottomRight.isLeaf &&
                topLeft.val == topRight.val &&
                topRight.val == bottomRight.val &&
                bottomRight.val == bottomLeft.val;

        if (isLeaf) return new Node(topLeft.val, true);
        else return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }
}