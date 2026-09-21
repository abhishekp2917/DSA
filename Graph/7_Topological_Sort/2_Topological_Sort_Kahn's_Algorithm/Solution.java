import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    static ArrayList<Integer> topologicalSort(ArrayList<ArrayList<Integer>> adj) {
        int n = adj.size();
        // create a list to store the topologically sorted nodes
        ArrayList<Integer> topologicallySortedList = new ArrayList<>();
        // create a queue to store the nodes with 0 incoming edges to traverse
        Queue<Integer> queue = new LinkedList<>();
        // create an array to store the degree of each node
        int[] nodeIncomingDegree = new int[n];
        // calculate the degree (number of incoming edges) for each node
        // the adjacency list of U -> [V1, V2, ..] shows that for all Vi there is an incoming node from U
        // keeping above observation in mind, increment the degree of all Vi for each U 
        for(int i=0; i<n; i++) {
            adj.get(i).stream().forEach(neighbour -> nodeIncomingDegree[neighbour]++); 
        }
        // add all the nodes with 0 incoming edges to the queue
        // we will start traversing from these nodes as they don't have any incoming edges or ancestors
        for(int i=0; i<n; i++) {
            if(nodeIncomingDegree[i]==0) queue.add(i);
        }
        // traverse the nodes in the queue
        while(!queue.isEmpty()) {
            int node = queue.poll();
            // for each neighbour of the node, decrement the incoming edge count indicating that we have processed that edge
            adj.get(node).stream().forEach(neighbour -> {
                // if the incoming edge count becomes 0, it indicates all of it's ancestor node has been processed and added to the topological sort 
                // so add the neighbour to the queue
                if(--nodeIncomingDegree[neighbour]==0) {
                    queue.add(neighbour);
                } 
            });
            // add the node to the topologically sorted list
            topologicallySortedList.add(node);
        }
        return topologicallySortedList;
    }
}