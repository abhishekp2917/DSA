import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    
    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        int edgeCount = 0;
        int[] indegree = new int[n];
        for(int node=0; node<n; node++) {
            int left = leftChild[node];
            int right = rightChild[node];
            if(left!=-1) {
                edgeCount++;
                indegree[left]++;
            }
            if(right!=-1) {
                edgeCount++;
                indegree[right]++;
            }
        }
        if(edgeCount!=n-1) return false;
        int root = -1;
        for(int node=0; node<n; node++) {
            if(indegree[node]==0) {
                if(root!=-1) return false;
                root = node;
            }
        }
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(root);
        int nodeCount = 0;
        while(!queue.isEmpty()) {
            int node = queue.poll();
            nodeCount++;
            if(leftChild[node]!=-1) queue.add(leftChild[node]);
            if(rightChild[node]!=-1) queue.add(rightChild[node]);
        }   
        return nodeCount==n;
    }
}

