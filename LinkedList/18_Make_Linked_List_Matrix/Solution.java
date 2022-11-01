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
        Node[][] matrix= new Node[n+1][n+1];
        
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                matrix[i][j] = new Node(arr[i][j]);
            }
        }
        
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                matrix[i][j].right = matrix[i][j+1];
                matrix[i][j].down = matrix[i+1][j];
            }
        }
        return matrix[0][0];
    }
}
