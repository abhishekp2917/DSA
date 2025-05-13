import java.util.ArrayList;
import java.util.List;

class Solution {

    public List<Integer> numOfIslands(int rows, int cols, int[][] operators) {
        List<Integer> ans = new ArrayList<>(); // This will store the number of islands after each operator.
        int componentCount = 0; // Initialize component count, which represents the number of islands.
        int[][] matrix = new int[rows][cols]; // 2D matrix to track whether a cell is land (1) or water (0).
        int[][] parents = new int[rows][cols]; // DSU parent array to represent connected components (islands).
        int[][] size = new int[rows][cols]; // DSU size array to store the size of each component.
        
        // Directions array to check the 4 neighboring cells (up, down, left, right)
        int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        // Initialize the parents array. Each cell initially points to itself as its own parent.
        for(int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                parents[i][j] = (cols * i) + j; // This assigns a unique identifier to each cell.
            }
        }
        
        // Process each operator (each operator adds land to the matrix)
        for(int i=0; i<operators.length; i++) {
            int x = operators[i][0]; // Row index of the current operator (land to be added).
            int y = operators[i][1]; // Column index of the current operator (land to be added).
            
            // If this cell is not already land, we convert it to land and update the component count.
            if(matrix[x][y] != 1) {
                componentCount++; // Increment the component count as we add a new piece of land.
                matrix[x][y] = 1; // Mark this cell as land.
                
                // Check the 4 neighboring cells (up, down, left, right).
                for(int[] direction : directions) {
                    int X = x + direction[0];
                    int Y = y + direction[1];
                    
                    // If the neighboring cell is within bounds and is land, we try to merge the components.
                    if(X >= 0 && X < rows && Y >= 0 && Y < cols && matrix[X][Y] == 1) {
                        // Get the parent of the neighboring cell and the current cell.
                        int uParentNode = parents[X][Y];
                        int vParentNode = parents[x][y];
                        
                        // Find the roots of the two components (islands) they belong to.
                        int uRootNode = getRootNode(parents, rows, cols, uParentNode);
                        int vRootNode = getRootNode(parents, rows, cols, vParentNode);
                        
                        // If they are in different components, we merge them and decrease the component count.
                        if(uRootNode != vRootNode) {
                            merge(parents, size, rows, cols, uRootNode, vRootNode);
                            componentCount--; // After merging, the component count decreases by 1 since two island merged into 1
                        }
                    }
                }
            }
            // After processing the current operator, we add the current component count to the answer list.
            ans.add(componentCount);
        }
        
        return ans; // Return the list of component counts after each operator.
    }

    // Merge two sets (components) represented by u and v
    private void merge(int[][] parents, int[][] size, int rows, int cols, int u, int v) {
        // Convert the root nodes to positions (row, col).
        int[] uPos = getPos(u, rows, cols);
        int[] vPos = getPos(v, rows, cols);
        
        // Get the size of the components.
        int uSize = size[uPos[0]][uPos[1]];
        int vSize = size[vPos[0]][vPos[1]];
        
        // Merge the smaller component into the larger one to keep the tree flat.
        if(uSize < vSize) {
            parents[uPos[0]][uPos[1]] = v; // Make the parent of u point to v.
            size[vPos[0]][vPos[1]] += uSize; // Add the size of u's component to v's size.
        } else {
            parents[vPos[0]][vPos[1]] = u; // Make the parent of v point to u.
            size[uPos[0]][uPos[1]] += vSize; // Add the size of v's component to u's size.
        }
    }

    // Find the root of the component that the node u belongs to using path compression.
    private int getRootNode(int[][] parents, int rows, int cols, int u) {
        // Convert the node index u to position (x, y).
        int[] pos = getPos(u, rows, cols);
        int x = pos[0];
        int y = pos[1];
        
        // If u is its own parent, it is the root of its component.
        if(parents[x][y] == u) return u;
        
        // Otherwise, recursively find the root and apply path compression.
        int parentNode = parents[x][y];
        int rootNode = getRootNode(parents, rows, cols, parentNode);
        parents[x][y] = rootNode; // Path compression: direct connection to root.
        return rootNode;
    }

    // Convert a 1D index (num) to 2D coordinates (row, col).
    private int[] getPos(int num, int rows, int cols) {
        int x = num / cols; // Row index.
        int y = num % cols; // Column index.
        return new int[] {x, y}; // Return the (x, y) position.
    }
}
