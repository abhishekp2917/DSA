import java.util.Arrays;

class Solution1 {
    
    public int minSessions(int[] tasks, int sessionTime) {
        int n = tasks.length;
        int availTaskMask = (1<<n)-1;
        Integer[][] memo = new Integer[availTaskMask+1][sessionTime+1];
        return 1 + recursion(tasks, sessionTime, availTaskMask, 0, memo);
    }

    private int recursion(int[] tasks, int sessionTime, int availTaskMask, int taskHrs, Integer[][] memo) {
        int n = tasks.length;
        if(availTaskMask==0) return 0;
        if(memo[availTaskMask][taskHrs]!=null) return memo[availTaskMask][taskHrs];
        int minSession = n;
        for(int bit=0; bit<n; bit++) {
            int bitValue = (availTaskMask>>bit)&1;
            if(bitValue==1) {
                int currTaskHr = tasks[bit];
                int newAvailTaskMask = availTaskMask^(1<<bit);
                int sessionCount = 0;
                if(taskHrs+currTaskHr<=sessionTime) {
                    sessionCount = recursion(tasks, sessionTime, newAvailTaskMask, taskHrs+currTaskHr, memo);
                }
                else {
                    sessionCount = 1 + recursion(tasks, sessionTime, newAvailTaskMask, currTaskHr, memo);
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
        int availTaskMask = (1<<n)-1;
        int[][] dp = new int[availTaskMask+1][sessionTime+1];
        for (int mask=0; mask<=availTaskMask; mask++) {
            Arrays.fill(dp[mask], n);
        }
        for (int hrs=0; hrs<=sessionTime;hrs++) {
            dp[0][hrs] = 0;
        }
        for (int mask=1; mask<=availTaskMask; mask++) {
            for (int taskHrs= sessionTime; taskHrs>=0; taskHrs--) {
                int minSession = n;
                for(int bit=0; bit<n; bit++) {
                    int bitValue = (availTaskMask>>bit)&1;
                    if(bitValue==1) {
                        int currTaskHr = tasks[bit];
                        int newMask = mask^(1<<bit);
                        int sessionCount = 0;
                        if(taskHrs+currTaskHr<=sessionTime) {
                            sessionCount = dp[newMask][taskHrs+currTaskHr];
                        }
                        else {
                            sessionCount = 1 + dp[newMask][currTaskHr];
                        }
                        minSession = Math.min(minSession, sessionCount);
                    }
                }
                dp[mask][taskHrs] = minSession;
            }
        }
        return 1 + dp[availTaskMask][0];
    }
}