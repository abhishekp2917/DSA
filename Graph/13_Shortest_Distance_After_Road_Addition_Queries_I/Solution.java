import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        // create adjacency list to represent the graph
        List<List<Integer>> adjList = new ArrayList<>();
        // shortestDistance array to store the shortest distance from source city to all other cities
        int[] shortestDistance = new int[n];
        // array to store the answer for each query
        int[] ans = new int[queries.length];
        // initialize the adjacency list and shortestDistance array
        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
            // add the edge between city i and city i+1 if i is not the last city (n-1)
            // the edges are uni-directional
            if(i!=n-1) adjList.get(i).add(i+1);
            // since the soiurce city is 0, we will set the shortest distance of source city to 0 and all other cities to infinity
            if(i!=0) shortestDistance[i] = Integer.MAX_VALUE;
        }
        // calculate the shortest distance from source city (0) to all other cities and store it in the shortestDistance array
        getShortestDistance(adjList, 0, shortestDistance);
        // for each query, add the edge between the two cities and recalculate the shortest distance from source city to all other cities
        for(int i=0; i<queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            adjList.get(u).add(v);
            // recalculate the shortest distance from source city to all other cities
            // since an edge is added only from city 'u', this will effect only those cities whose shortest distance path goes through city 'u'
            // so recalculate the shortest distance considering city 'u' as the source city
            getShortestDistance(adjList, u, shortestDistance);
            // store the shortest distance from source city to the last city (n-1) after adding the edge between city 'u' and city 'v'
            ans[i] = shortestDistance[n-1];
        }
        // return the answer array
        return ans;
    }

    // method to get the shortest distance from source city to all other cities using Dijkstra's algorithm and update the shortestDistance array
    private void getShortestDistance(List<List<Integer>> adjList, int sourceCity, int[] shortestDistance) {
        PriorityQueue<Pair> minHeap = new PriorityQueue<>((pair1, pair2) -> pair1.distance-pair2.distance);
        minHeap.add(new Pair(sourceCity, shortestDistance[sourceCity]));
        while(!minHeap.isEmpty()) {
            int currCity = minHeap.peek().city;
            int currCityDistance = minHeap.peek().distance;
            minHeap.poll();
            for(Integer neighbourCity : adjList.get(currCity)) {
                int newDistance = 1 + currCityDistance;
                int prevDistance = shortestDistance[neighbourCity];
                if(newDistance<=prevDistance) {
                    shortestDistance[neighbourCity] = newDistance;
                    minHeap.add(new Pair(neighbourCity, newDistance));
                }
            }
        }
    }
}

// class to represent a pair of city and its distance from source city
class Pair {
    int city;
    int distance;
    Pair(int city, int distance) {
        this.city = city;
        this.distance = distance;
    }
}