// Structure of Node Class
class Node {
    int data;
    Node right, down;

    public Node(int data){
        this.data = data;
    }
}

class Solution
{
    static Node construct(int arr[][],int n)
    {
        // Create a Node object for every matrix cell.
        // The extra row and column in the Node matrix allow
        // boundary cells to safely point to null.
        Node[][] matrix= new Node[n+1][n+1];

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {

                // Store the matrix value in the corresponding Node.
                matrix[i][j] = new Node(arr[i][j]);
            }
        }

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {

                // Connect each node to the node immediately to its right.
                // For the last column, matrix[i][n] is null by default.
                matrix[i][j].right = matrix[i][j+1];

                // Connect each node to the node immediately below it.
                // For the last row, matrix[n][j] is null by default.
                matrix[i][j].down = matrix[i+1][j];
            }
        }

        // The top-left node is the entry point to the entire
        // matrix because every other node can be reached through
        // right and down pointers.
        return matrix[0][0];
    }
}