import java.util.Arrays;

class Solution {

    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {

        int n = difficulty.length;
        int m = worker.length;

        int totalProfit = 0;

        // Combine difficulty and profit into a single structure
        // so that sorting keeps them aligned.
        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(difficulty[i], profit[i]);
        }

        // Sort jobs by difficulty ascending.
        // If same difficulty, keep higher profit first.
        //
        // Sorting by difficulty allows us to process jobs
        // in increasing order of ability requirement.
        Arrays.sort(jobs, (job1, job2) -> {
            if (job1.difficulty == job2.difficulty)
                return job2.profit - job1.profit;
            return job1.difficulty - job2.difficulty;
        });

        // Sort workers by ability.
        // This allows us to process weaker workers first
        // and reuse computed best profits.
        Arrays.sort(worker);

        int i = 0;  // pointer for jobs
        int j = 0;  // pointer for workers

        int maxProfit = 0;

        // Process each worker in increasing ability order.
        while (j < m) {

            int workerAbility = worker[j];

            // Move through all jobs that this worker can perform.
            // Since jobs are sorted by difficulty,
            // once difficulty exceeds ability, we stop.
            while (i < n && jobs[i].difficulty <= workerAbility) {

                // Track the maximum profit among all jobs
                // that current and future workers can do.
                maxProfit = Math.max(maxProfit, jobs[i].profit);

                i++;
            }

            // Assign the best available job to this worker.
            totalProfit += maxProfit;

            j++;
        }

        return totalProfit;
    }
}

// Helper class to bundle difficulty and profit together.
class Job {
    int difficulty;
    int profit;

    Job(int difficulty, int profit) {
        this.difficulty = difficulty;
        this.profit = profit;
    }
}
