import java.util.ArrayList;
import java.util.List;

class Solution {
    
    public int[] gardenNoAdj(int n, int[][] paths) {
        int[] gardenColor = new int[n];
        List<Integer>[] adjList = new ArrayList[n];
        for(int garden=0; garden<n; garden++) adjList[garden] = new ArrayList<>();
        for(int[] path : paths) {
            adjList[path[0]-1].add(path[1]-1);
            adjList[path[1]-1].add(path[0]-1);
        }
        for(int garden=0; garden<n; garden++) {
            for(int color=1; color<=4; color++) {
                if(!isColorAvail(adjList, gardenColor, color, garden)) continue;
                gardenColor[garden] = color;
                break;
            }
        }
        return gardenColor;
    }

    private boolean isColorAvail(List<Integer>[] adjList, int[] gardenColor, int color, int garden) {
        for(int neighbour : adjList[garden]) {
            if(gardenColor[neighbour]==color) return false;
        }
        return true;
    }
}  