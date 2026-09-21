import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Solution {

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] courseOrder = new int[numCourses];
        List<Integer>[] adjList = new ArrayList[numCourses];
        int[] indegree = new int[numCourses];
        
        for(int course=0; course<numCourses; course++) adjList[course] = new ArrayList<>();
        for(int[] prerequisite : prerequisites) {
            int prerequisiteCourse = prerequisite[1];
            int course = prerequisite[0];
            adjList[prerequisiteCourse].add(course);
            indegree[course]++;
        } 
        
        Queue<Integer> queue = new ArrayDeque<>();
        for(int course=0; course<numCourses; course++) {
            if(indegree[course]==0) queue.add(course);
        } 
        
        int index = 0;
        
        while(!queue.isEmpty()) {

            int prerequisiteCourse = queue.poll();
            courseOrder[index++] = prerequisiteCourse;
            
            for(int course : adjList[prerequisiteCourse]) {
                indegree[course]--;
                if(indegree[course]==0) queue.add(course);
            }
        }
        return (index==numCourses)? courseOrder : new int[0];
    }
}
