import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        List<Boolean> ans = new ArrayList<>();
        boolean[] visited = new boolean[numCourses];
        int[] inDegree = new int[numCourses];
        List<List<Integer>> adjList = getAdjList(numCourses, prerequisites, inDegree);
        Queue<Integer> queue = new PriorityQueue<>();
        boolean[][] prerequisiteMatrix = new boolean[numCourses][numCourses];
        for(int i=0; i<numCourses; i++) {
            prerequisiteMatrix[i][i] = true;
            if(inDegree[i]==0) queue.add(i);
        }

        while(!queue.isEmpty()) {
            int prerequisiteCourse = queue.poll();
            if(!visited[prerequisiteCourse]) {
                visited[prerequisiteCourse] = true;
                for(Integer course : adjList.get(prerequisiteCourse)) {
                    for(int otherCourse=0; otherCourse<numCourses; otherCourse++) {
                        if(prerequisiteMatrix[otherCourse][prerequisiteCourse]) {
                            prerequisiteMatrix[otherCourse][course] = true;   
                        }
                    }
                    inDegree[course]--;
                    if(inDegree[course]==0) queue.add(course);
                }
                
            }
        }
        for(int[] query : queries) {
            int courseU = query[0];
            int courseV = query[1];
            boolean isUPrerequisiteOfV = prerequisiteMatrix[courseU][courseV];
            ans.add(isUPrerequisiteOfV);
        }
        return ans;
    }

    private List<List<Integer>> getAdjList(int n, int[][] prerequisites, int[] inDegree) {
        List<List<Integer>> adjList = new ArrayList<>();
        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
        }
        for(int i=0; i<prerequisites.length; i++) {
            int u = prerequisites[i][0];
            int v = prerequisites[i][1];
            adjList.get(u).add(v);
            inDegree[v]++;
        }
        return adjList;
    }
}
