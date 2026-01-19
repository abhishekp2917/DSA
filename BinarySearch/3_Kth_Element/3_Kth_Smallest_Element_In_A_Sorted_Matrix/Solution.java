class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length, m = matrix[0].length;
        int left = matrix[0][0], right = matrix[n-1][m-1];
        int ans = -1;
        while(left<=right) {
            int mid = (left+right)/2;
            int count = countElementLessOrEqual(matrix, n, m, mid);
            if(count<k) left = mid+1;
            else {
                ans = mid;
                right = mid-1;
            }
        } 
        return ans;
    }

    private int countElementLessOrEqual(int[][] matrix, int n, int m, int target) {
        int count = 0;
        int i = 0, j = m-1;
        while(i<n && j>=0) {
            if(matrix[i][j]<=target) {
                count += j+1;
                i++;
            }
            else if(matrix[i][j]>target) j--;
        }
        return count;
    }
}