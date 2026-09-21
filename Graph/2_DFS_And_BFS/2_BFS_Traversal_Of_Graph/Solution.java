import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {

    public List<Integer> bfsTraversal(int n, HashMap<Integer, List<Integer>> adjacencyList, int startNode) {

        // List to store the BFS traversal of the graph
        List<Integer> bfsTraversal = new ArrayList<>();

        // Array to keep track of visited nodes in the graph
        // This is used to avoid visiting the same node multiple times
        boolean[] visited = new boolean[n];

        // Queue to store the nodes that are to be visited
        // We are using a queue to implement BFS because it is a level order traversal that means we visit a node's all 
        // neighbours first before moving to the next level
        Queue<Integer> queue = new LinkedList<>();

        // Add the start node to the queue and mark it as visited
        queue.add(startNode);
        visited[startNode] = true;

        // While the queue is not empty, keep visiting the nodes
        // The nodes are visited in the order they are added to the queue
        while(!queue.isEmpty()) {

            // Get the node from the front of the queue and add it to the BFS traversal list
            int node = queue.poll();
            bfsTraversal.add(node);

            // Visit all the neighbours of the node and add them to the queue if they are not visited
            // Mark the neighbours as visited because we don't want to visit them again
            for(int neighbour : adjacencyList.get(node)) {
                if(!visited[neighbour]) {
                    queue.add(neighbour);
                    visited[neighbour] = true;
                }
            }
        }
        
        return bfsTraversal;
    }
}