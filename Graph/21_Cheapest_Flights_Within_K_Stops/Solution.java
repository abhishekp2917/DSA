import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // Get adjacency list of the graph flights
        List<List<List<Integer>>> adjList = getAdjList(n, flights);
        // list to store the flight prices from src to all other cities
        List<Integer> flightPrices = new ArrayList<>();
        // Initialize the flight prices list with max value for all cities except src city which is 0
        for(int i=0; i<n; i++) flightPrices.add(Integer.MAX_VALUE);
        flightPrices.set(src, 0);
        // Queue to store the city, flight cost and stop count
        // we are not using priority queue because we need to explore the cities with least stop count first which would be the level of BFS
        // we are visiting the cities with minimum flight stops first because we need to find the flight within k stops
        Queue<List<Integer>> queue = new LinkedList<>(); 
        // Add the src city to the queue with flight cost 0 and stop count 0
        queue.add(new ArrayList<>(Arrays.asList(src, 0, 0)));
        while(!queue.isEmpty()) {
            // Get the city, flight cost and stop count from the queue
            int currCity = queue.peek().get(0);
            int currCityFlightCost = queue.peek().get(1);
            int currCityStopCount = queue.peek().get(2);
            queue.poll();
            // explore the neighbours of the current city
            for(List<Integer> neighbour : adjList.get(currCity)) {
                // Get the neighbour city and the cost of the flight to reach the neighbour city
                int neighbourCity = neighbour.get(0);
                int cost = neighbour.get(1);
                int neighbourCityNewFlightCost = currCityFlightCost + cost;
                int neighbourCityNewStopCount = currCityStopCount+1;
                int neighbourCityPrevFlightCost = flightPrices.get(neighbourCity);
                // if the new stop count is greater than k+1 then skip the neighbour city because we could never reach the destination city with k stops
                // if the new stop count is greater than k and the neighbour city is not the destination city then skip the neighbour city else explore the neighbour city (destination city)
                if((neighbourCityNewStopCount>k && neighbourCity!=dst) || (neighbourCityNewStopCount>k+1)) continue;
                // since we are exploring the cities with minimum stop count first, we can update the flight cost of the neighbour city only if the new flight cost is less than the previous flight cost
                if(neighbourCityNewFlightCost<neighbourCityPrevFlightCost) {
                    flightPrices.set(neighbourCity, neighbourCityNewFlightCost);
                    queue.add(new ArrayList<>(Arrays.asList(neighbourCity, neighbourCityNewFlightCost, neighbourCityNewStopCount)));
                }
            }
        }
        // return the flight cost of the destination city if it is not Integer.MAX_VALUE else return -1
        return flightPrices.get(dst)!=Integer.MAX_VALUE?flightPrices.get(dst):-1;
    }

    // Get adjacency list of the graph flights
    private List<List<List<Integer>>> getAdjList(int n, int[][] flights) {
        List<List<List<Integer>>> adjList = new ArrayList<>();
        for(int i=0; i<n; i++) adjList.add(new ArrayList<>());
        for(int i=0; i<flights.length; i++) {
            adjList.get(flights[i][0]).add(new ArrayList<>(Arrays.asList(flights[i][1], flights[i][2])));
        }
        return adjList;
    }
}