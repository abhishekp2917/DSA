import java.util.ArrayList;
import java.util.List;

class Solution {

    private long fuelCost;

    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length+1;
        List<Integer>[] adjList = new ArrayList[n];
        for(int city=0; city<n; city++) {
            adjList[city] = new ArrayList<>();
        }
        for(int[] road : roads) {
            adjList[road[0]].add(road[1]);
            adjList[road[1]].add(road[0]);
        }
        calculateFuelCost(adjList, 0, -1, seats);
        return this.fuelCost;
    }

    private int calculateFuelCost(List<Integer>[] adjList, int city, int prevCity, int seats) {
        int representativeCount = 1;
        for(int nextCity : adjList[city]) {
            if(nextCity==prevCity) continue;
            representativeCount += calculateFuelCost(adjList, nextCity, city, seats);
        }
        if(city!=0) this.fuelCost += (representativeCount+seats-1)/seats;
        return representativeCount;
    }
}
