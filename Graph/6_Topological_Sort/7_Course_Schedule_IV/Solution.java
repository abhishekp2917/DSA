import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {

    public List<Boolean> checkIfPrerequisite(
        int numCourses,
        int[][] prerequisites,
        int[][] queries
    ) {

        List<Boolean> ans = new ArrayList<>();

        // visited[i] indicates whether course i has already been processed
        // in the topological traversal
        boolean[] visited = new boolean[numCourses];

        // inDegree[i] stores how many prerequisites course i still has
        int[] inDegree = new int[numCourses];

        // Build adjacency list and fill in-degree array
        List<List<Integer>> adjList = getAdjList(
            numCourses,
            prerequisites,
            inDegree
        );

        // Queue for courses whose prerequisites are fully satisfied
        Queue<Integer> queue = new PriorityQueue<>();

        // prerequisiteMatrix[u][v] = true means
        // course u is a prerequisite (direct or indirect) of course v
        boolean[][] prerequisiteMatrix =
            new boolean[numCourses][numCourses];

        // Initialization:
        // - Every course is trivially a prerequisite of itself
        // - Courses with in-degree 0 can be taken first
        for (int i = 0; i < numCourses; i++) {
            prerequisiteMatrix[i][i] = true;
            if (inDegree[i] == 0) queue.add(i);
        }

        // Process courses in topological order
        while (!queue.isEmpty()) {

            // Current course whose prerequisites are fully resolved
            int prerequisiteCourse = queue.poll();

            if (!visited[prerequisiteCourse]) {
                visited[prerequisiteCourse] = true;

                // Traverse all courses that depend on prerequisiteCourse
                for (Integer course : adjList.get(prerequisiteCourse)) {

                    // Propagate prerequisite relationships:
                    // If some otherCourse is a prerequisite of prerequisiteCourse,
                    // then it is also a prerequisite of course.
                    for (int otherCourse = 0;
                         otherCourse < numCourses;
                         otherCourse++) {

                        if (prerequisiteMatrix[otherCourse][prerequisiteCourse]) {
                            prerequisiteMatrix[otherCourse][course] = true;
                        }
                    }

                    // Reduce in-degree since prerequisiteCourse is now resolved
                    inDegree[course]--;

                    // If all prerequisites of course are resolved,
                    // it can now be processed
                    if (inDegree[course] == 0) {
                        queue.add(course);
                    }
                }
            }
        }

        // Answer each query using the precomputed matrix
        for (int[] query : queries) {
            int courseU = query[0];
            int courseV = query[1];
            ans.add(prerequisiteMatrix[courseU][courseV]);
        }

        return ans;
    }

    // Builds adjacency list and computes in-degrees
    private List<List<Integer>> getAdjList(
        int n,
        int[][] prerequisites,
        int[] inDegree
    ) {

        List<List<Integer>> adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        // Each prerequisite [u, v] means u must be taken before v
        for (int i = 0; i < prerequisites.length; i++) {
            int u = prerequisites[i][0];
            int v = prerequisites[i][1];
            adjList.get(u).add(v);
            inDegree[v]++;
        }

        return adjList;
    }
}
