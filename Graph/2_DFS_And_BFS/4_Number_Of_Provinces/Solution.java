class Solution {
    
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int provincesCount = 0;
        boolean[] visited = new boolean[n];
        for(int city=0; city<n; city++) {
            if(!visited[city]) {
                traverseProvince(isConnected, visited, city);
                provincesCount++;
            }
        }
        return provincesCount;
    }

    private void traverseProvince(int[][] isConnected, boolean[] visited, int currCity) {
        int n = isConnected.length;
        visited[currCity] = true;
        for(int nextCity=0; nextCity<n; nextCity++) {
            if(isConnected[currCity][nextCity]==1 && !visited[nextCity]) {
                traverseProvince(isConnected, visited, nextCity);
            }
        }
    }
}