package SegmentTree.Theory;

public class Main {
    
    public static void main(String[] args) {

        int[] arr = new int[] {40, 5, 6, 1, 3, 22, 4, 12, 11};
        int[] segmentTree = new int[4*arr.length];
        createSegmentTree(segmentTree, 0, arr, 0, arr.length-1);
        System.out.println(querySegmentTree(segmentTree, 5, 8, 0, 0, arr.length-1));
    }

    private static int createSegmentTree(int[] segmentTree, int idx, int[] arr, int left, int right) {
        if(left==right) {
            segmentTree[idx] = arr[left];
            return segmentTree[idx];
        }
        int mid = (left+right)/2;
        int leftMin = createSegmentTree(segmentTree, 2*idx+1, arr, left, mid);
        int rightMin = createSegmentTree(segmentTree, 2*idx+2, arr, mid+1, right);
        segmentTree[idx] = Math.min(leftMin, rightMin);
        return segmentTree[idx];
    }

    private static int querySegmentTree(int[] segmentTree, int i, int j, int idx, int left, int right) {
        if(j<left || i>right) return Integer.MAX_VALUE;
        if(i>=left && j<=right) {
            if(i==left && j==right) return segmentTree[idx];
            int leftValue, rightValue;
            int mid = (left+right)/2;
            leftValue = querySegmentTree(segmentTree, i, j, 2*idx+1, left, mid);
            rightValue = querySegmentTree(segmentTree, i, j, 2*idx+2, mid+1, right);
            return Math.min(leftValue, rightValue);
        }
        else {
            if(i<left) i=left;
            if(j>right) j = right;
            if(i==left && j==right) return segmentTree[idx];
            int leftValue, rightValue;
            int mid = (left+right)/2;
            leftValue = querySegmentTree(segmentTree, i, j, 2*idx+1, left, mid);
            rightValue = querySegmentTree(segmentTree, i, j, 2*idx+2, mid+1, right);
            return Math.min(leftValue, rightValue);
       }
    }
}
