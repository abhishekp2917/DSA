import java.util.*;

class Solution {

    public int[] getOrder(int[][] tasks) {

        int n = tasks.length;

        int[] taskProcessingOrder = new int[n];

        // Convert array into Task objects
        //
        // Why?
        // Because we need to track:
        // - original index
        // - enqueue time
        // - processing time
        List<Task> tasksList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            tasksList.add(
                new Task(i, tasks[i][0], tasks[i][1])
            );
        }

        // Sort tasks by enqueue time.
        //
        // Why?
        // Because CPU can only process tasks
        // that have already arrived.
        Collections.sort(
            tasksList,
            (t1, t2) -> t1.enqueTime - t2.enqueTime
        );

        // Min heap ordered by:
        // 1. Processing time
        // 2. Index (tie breaker)
        //
        // Why?
        // Because CPU rule:
        // - Choose task with shortest processing time.
        // - If tie, smaller index.
        PriorityQueue<Task> minHeap =
            new PriorityQueue<>((t1, t2) -> {
                if (t1.processingTime == t2.processingTime)
                    return t1.idx - t2.idx;
                return t1.processingTime - t2.processingTime;
            });

        // Current time starts at first task's arrival.
        int currTime = tasksList.get(0).enqueTime;

        int taskPtr = 0;  // pointer for sorted tasks
        int i = 0;        // pointer for result array

        while (i < n) {

            // Add all tasks that have arrived
            // by current time.
            //
            // Also important condition:
            // If heap is empty, we must push
            // next available task and jump time.
            while (taskPtr < n &&
                  (tasksList.get(taskPtr).enqueTime <= currTime
                   || minHeap.isEmpty())) {

                minHeap.add(tasksList.get(taskPtr));

                // If CPU was idle,
                // jump current time to this task's enqueue time.
                currTime = Math.max(
                    currTime,
                    tasksList.get(taskPtr).enqueTime
                );

                taskPtr++;
            }

            // Pick task with:
            // - smallest processing time
            // - smallest index if tie
            Task task = minHeap.poll();

            taskProcessingOrder[i++] = task.idx;

            // Advance time by processing duration.
            //
            // Why Math.max again?
            // Ensures time never goes backward
            // if CPU was idle.
            currTime = Math.max(currTime, task.enqueTime)
                       + task.processingTime;
        }

        return taskProcessingOrder;
    }
}

class Task {
    int idx;
    int enqueTime;
    int processingTime;

    Task(int idx, int enqueTime, int processingTime) {
        this.idx = idx;
        this.enqueTime = enqueTime;
        this.processingTime = processingTime;
    }
}
