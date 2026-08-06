import java.util.Arrays;

class Solution1 {

    public int minSessions(int[] tasks, int sessionTime) {

        int n = tasks.length;

        // Initially,
        // every task
        // is still unassigned.
        int availTaskMask = (1<<n)-1;

        // memo[mask][taskHrs]:
        // stores the minimum additional sessions
        // required after assigning the remaining tasks
        // represented by 'mask',
        // when the current session
        // already contains taskHrs hours of work.
        Integer[][] memo = new Integer[availTaskMask+1][sessionTime+1];

        // recursion() counts only the NEW sessions
        // opened after the current one.
        //
        // Therefore,
        // add one session initially
        // for the currently active session.
        return 1 + recursion(tasks, sessionTime, availTaskMask, 0, memo);
    }

    private int recursion(int[] tasks, int sessionTime, int availTaskMask, int taskHrs, Integer[][] memo) {

        int n = tasks.length;

        // Every task
        // has already been scheduled.
        if(availTaskMask==0) return 0;

        // Reuse previously computed state.
        if(memo[availTaskMask][taskHrs]!=null) return memo[availTaskMask][taskHrs];

        int minSession = n;

        // Try scheduling
        // every remaining task next.
        for(int bit=0; bit<n; bit++) {

            int bitValue = (availTaskMask>>bit)&1;

            if(bitValue==1) {

                int currTaskHr = tasks[bit];

                // Remove this task
                // from the remaining set.
                int newAvailTaskMask = availTaskMask^(1<<bit);

                int sessionCount = 0;

                // If the task fits,
                // continue filling
                // the current session.
                if(taskHrs+currTaskHr<=sessionTime) {

                    sessionCount =
                        recursion(
                            tasks,
                            sessionTime,
                            newAvailTaskMask,
                            taskHrs+currTaskHr,
                            memo
                        );
                }
                else {

                    // Otherwise,
                    // the current session
                    // must be closed
                    // and a new one started.
                    sessionCount =
                        1 +
                        recursion(
                            tasks,
                            sessionTime,
                            newAvailTaskMask,
                            currTaskHr,
                            memo
                        );
                }

                minSession = Math.min(minSession, sessionCount);
            }
        }

        memo[availTaskMask][taskHrs] = minSession;

        return minSession;
    }
}

class Solution2 {

    public int minSessions(int[] tasks, int sessionTime) {

        int n = tasks.length;

        // Initially,
        // every task
        // is still available.
        int availTaskMask = (1<<n)-1;

        // dp[mask][taskHrs]:
        // stores the minimum additional sessions
        // required after scheduling
        // the remaining tasks in 'mask',
        // when the current session
        // already contains taskHrs hours.
        int[][] dp = new int[availTaskMask+1][sessionTime+1];

        // Initialize every state
        // with the worst possible answer.
        for(int mask=0; mask<=availTaskMask; mask++) {
            Arrays.fill(dp[mask], n);
        }

        // No remaining tasks
        // require no additional sessions,
        // regardless of the current session usage.
        for(int hrs=0; hrs<=sessionTime; hrs++) {
            dp[0][hrs] = 0;
        }

        // Build states
        // from smaller masks
        // towards larger masks
        // because every transition
        // removes one task.
        for(int mask=1; mask<=availTaskMask; mask++) {

            for(int taskHrs=sessionTime; taskHrs>=0; taskHrs--) {

                int minSession = n;

                // Try scheduling
                // every remaining task next.
                for(int bit=0; bit<n; bit++) {

                    // Check whether
                    // the current task
                    // is still available.
                    int bitValue = (mask>>bit)&1;

                    if(bitValue==1) {

                        int currTaskHr = tasks[bit];

                        // Remove the chosen task.
                        int newMask = mask^(1<<bit);

                        int sessionCount = 0;

                        // Continue using
                        // the current session
                        // if enough time remains.
                        if(taskHrs+currTaskHr<=sessionTime) {

                            sessionCount =
                                dp[newMask][taskHrs+currTaskHr];
                        }
                        else {

                            // Otherwise,
                            // close the current session
                            // and begin a new one.
                            sessionCount =
                                1 +
                                dp[newMask][currTaskHr];
                        }

                        minSession = Math.min(
                            minSession,
                            sessionCount
                        );
                    }
                }

                dp[mask][taskHrs] = minSession;
            }
        }

        // Add the very first session
        // because dp counts
        // only future sessions.
        return 1 + dp[availTaskMask][0];
    }
}