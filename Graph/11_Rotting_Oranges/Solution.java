class Solution {
    public int orangesRotting(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        // variable to store the time taken to rot all the oranges
        int time = 0;
        // we will not modify the input grid to mark the visited oranges so create a visited matrix to keep track of visited oranges
        boolean[][] visited = new boolean[n][m];
        // create a queue for bfs traversal
        Queue<Node> queue = new LinkedList<>();

        // insert all the rotten oranges in the queue because the rotten oranges will be the starting point of bfs traversal
        // we will use Node class to store the x and y coordinates of the oranges
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(grid[i][j]==2) {
                    queue.add(new Node(i, j));
                }
            }
        } 

        // start the bfs traversal and continue until the queue is empty
        while(!queue.isEmpty()) {
            int queueSize = queue.size();
            for(int i=0; i<queueSize; i++) {
                // poll the front of the queue and check for all the neighbours of the current orange
                Node node = queue.poll();
                Node leftNode = new Node(node.x-1, node.y);
                Node rightNode = new Node(node.x+1, node.y);
                Node topNode = new Node(node.x, node.y+1);
                Node bottomNode = new Node(node.x, node.y-1);
                // if the neighbour is valid then mark it as visited which means that orange is rotten now and add it to the queue
                // validation logic is in the isValidNode function
                if(isValidNode(leftNode, grid, visited, n, m)) {
                    visited[leftNode.x][leftNode.y] = true;
                    queue.add(leftNode);
                }
                if(isValidNode(rightNode, grid, visited, n, m)) {
                    visited[rightNode.x][rightNode.y] = true;
                    queue.add(rightNode);
                }
                if(isValidNode(topNode, grid, visited, n, m)) {
                    visited[topNode.x][topNode.y] = true;
                    queue.add(topNode);
                }
                if(isValidNode(bottomNode, grid, visited, n, m)) {
                    visited[bottomNode.x][bottomNode.y] = true;
                    queue.add(bottomNode);
                }
            }
            // increment the time after processing all the neighbours of the current oranges
            // if the queue is empty then it means that all the oranges are rotten so we don't need to increment the time
            if(!queue.isEmpty()) time++;
        }
        // check if all the oranges are rotten after the bfs traversal
        // if all the oranges are rotten then return the time taken to rot all the oranges else return -1
        return (hasAllOrangesRotten(grid, visited, n, m))?time:-1;
    }

    // check is the node is valid to be added to the queue
    private boolean isValidNode(Node node, int[][] grid, boolean[][] visited, int n, int m) {
        // if the node is out of bounds then it is not valid so return false
        if(node.x>=n || node.x<0 || node.y>=m || node.y<0) return false;
        // if the node is fresh orange and not visited then it is valid for bfs traversal so return true
        if(grid[node.x][node.y]==1 && !visited[node.x][node.y]) return true;
        // else return false
        return false;
    }

    // check if all the oranges are rotten after the bfs traversal
    private boolean hasAllOrangesRotten(int[][] grid, boolean[][] visited, int n, int m) {
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                // if there is any fresh orange which is not visited then it means that the bfs traversal didn't reach that orange
                // so return false
                if(grid[i][j]==1 && !visited[i][j]) return false;
            }
        } 
        return true;
    }
}

// class to store the x and y coordinates of the oranges
class Node {
    int x;
    int y;
    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

