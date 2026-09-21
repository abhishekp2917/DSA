import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    int minimumMultiplications(int[] array, int start, int end) {
        // The idea is to use Dijkstra's algorithm to find the shortest path from start to end.
        // The nodes are the numbers that can be reached by multiplying the current number with the numbers in the array.
        // since we are modulating the numbers by 100000, the maximum number of nodes is 100000.
        int[] steps = new int[100000];
        // Initialize the steps array with Integer.MAX_VALUE.
        Arrays.fill(steps, Integer.MAX_VALUE);
        // The number of steps to reach the start node is 0.
        steps[start] = 0;
        // Create a min heap to store the nodes in the order of the number of steps to reach them from the start node.
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((num1, num2) -> Integer.compare(steps[num1], steps[num2]));
        // Add the start node to the min heap.
        minHeap.add(start);
        while(!minHeap.isEmpty()) {
            // Get the current number from the min heap which has the minimum number of steps to reach it from the start node.
            int currNum = minHeap.poll();
            int currNumSteps = steps[currNum];
            // multiply the current number with the numbers in the array 
            for(int num : array) {
                // The next number is the current number multiplied by the number in the array modulated by 100000.
                int nextNum = (currNum*num)%100000;
                // get the number of steps to reach the next number from the start node from the steps array and also calculate the new steps count.
                int nextNumNewSteps = currNumSteps+1;
                int nextNumPrevSteps = steps[nextNum];
                // if the new steps count is less than the previous steps count, update the steps array and add the next number to the min heap.
                if(nextNumNewSteps<nextNumPrevSteps) {
                    steps[nextNum] = nextNumNewSteps;
                    minHeap.add(nextNum);
                }
            }
        }
        // return the number of steps to reach the end node from the start node.
        return (steps[end]!=Integer.MAX_VALUE)?steps[end]:-1;
    }
}