import java.util.ArrayList;
import java.util.PriorityQueue;

// class iPair represents a node and its weight in the graph
// variable first represents the node and second represents the weight
class iPair {
    int first, second;

    iPair(int first, int second) {
        this.first = first;
        this.second = second;
    }
}

class Solution {
    ArrayList<Integer> dijkstra(ArrayList<ArrayList<iPair>> adj, int src) {
        // minHeap is used to store the nodes and their distances from the source node
        // we will explaore the nodes with minimum distance first
        PriorityQueue<Pair> minHeap = new PriorityQueue<>((p1, p2) -> p1.distance-p2.distance);
        // shortestDistance is used to store the shortest distance of each node from the source node
        ArrayList<Integer> shortestDistance = new ArrayList<>();
        // initially, we will set the distance of all nodes to infinity except the source node since the distance of source node from itself is 0
        for(int i=0; i<adj.size(); i++) {
            shortestDistance.add(Integer.MAX_VALUE);
        }
        shortestDistance.set(src, 0);
        // we will add the source node to the minHeap since we will start exploring from the source node
        minHeap.add(new Pair(src, 0));

        // we will explore the nodes until the minHeap is empty 
        while(!minHeap.isEmpty()) {
            // poll the node with minimum distance from source node from the minHeap
            int currNode = minHeap.peek().node;
            int currNodeDistance = minHeap.peek().distance;
            minHeap.poll();
            // explore the neighbours of the current node 
            for(iPair neighbour : adj.get(currNode)) {
                int neighbourNode = neighbour.first;
                int neighbourWeight = neighbour.second;
                // calculate the distance of the neighbour node from the source node which will be : (distance of the current node from the source node) + (the weight of the edge between the current node and the neighbour node)
                int newDistance = currNodeDistance + neighbourWeight;
                // fetch the previously calculated distance of the neighbour node from source node from the shortestDistance array
                int prevDistance = shortestDistance.get(neighbourNode);
                // if the new distance is less than the previously calculated distance, then update the distance of the neighbour node from the source node 
                // and add the neighbour node to the minHeap
                // else don't add the neighbour node to the minHeap since we will not explore it further
                if(newDistance<prevDistance) {
                    shortestDistance.set(neighbourNode, newDistance);
                    minHeap.add(new Pair(neighbourNode, newDistance));
                }
            }
        }
        return shortestDistance;
    }
}

class Pair {
    int node;
    int distance;
    Pair(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }
}