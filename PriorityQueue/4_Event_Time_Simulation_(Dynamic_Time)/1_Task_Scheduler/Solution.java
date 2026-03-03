import java.util.*;

class Solution {

    public int leastInterval(char[] tasks, int n) {

        // currInterval represents current time unit.
        int currInterval = 0;

        // Count frequency of each task (A-Z).
        //
        // Why count first?
        // Because scheduling priority depends
        // on how many times a task still needs to run.
        int[] taskCount = new int[26];

        for (char task : tasks)
            taskCount[task - 'A']++;

        // Queue for tasks that are in cooldown.
        //
        // Each element = {remainingCount, nextValidTime}
        //
        // Why queue?
        // Because tasks enter cooldown in chronological order,
        // and they will become valid in the same order.
        Queue<int[]> invalidTaskQueue = new LinkedList<>();

        // Max heap for selecting the task
        // with highest remaining frequency.
        //
        // Why max heap?
        // Because greedy rule:
        // Always execute task with highest remaining count.
        //
        // That minimizes idle time.
        PriorityQueue<int[]> maxTaskCountHeap =
            new PriorityQueue<>((a, b) -> b[0] - a[0]);

        // Initialize heap with all tasks.
        for (int i = 0; i < 26; i++) {
            if (taskCount[i] > 0) {
                // nextValidTime = -1 initially
                maxTaskCountHeap.add(new int[]{ taskCount[i], -1 });
            }
        }

        // Continue until:
        // - No tasks left in heap
        // AND
        // - No tasks left in cooldown.
        while (!maxTaskCountHeap.isEmpty()
                || !invalidTaskQueue.isEmpty()) {

            // Step 1:
            // Move tasks whose cooldown expired
            // back into max heap.
            //
            // Why check <= currInterval?
            // Because at that time,
            // task becomes available again.
            while (!invalidTaskQueue.isEmpty()
                    && invalidTaskQueue.peek()[1] <= currInterval) {

                maxTaskCountHeap.add(invalidTaskQueue.poll());
            }

            // Step 2:
            // If any task is available,
            // execute the one with highest frequency.
            if (!maxTaskCountHeap.isEmpty()) {

                int[] task = maxTaskCountHeap.poll();

                // Decrease remaining count
                task[0]--;

                // Set next valid time after cooldown.
                //
                // Why currInterval + n + 1 ?
                //
                // If we execute at time t,
                // next same task can run at:
                // t + n + 1
                //
                // Example:
                // n = 2
                // If executed at time 0,
                // must wait time 1 and 2,
                // next allowed at time 3.
                task[1] = currInterval + n + 1;

                // If still remaining,
                // push into cooldown queue.
                if (task[0] > 0) {
                    invalidTaskQueue.add(task);
                }
            }

            // Move time forward.
            currInterval++;
        }

        return currInterval;
    }
}
