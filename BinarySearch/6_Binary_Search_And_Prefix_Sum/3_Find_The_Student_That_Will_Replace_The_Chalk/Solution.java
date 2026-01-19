class Solution {

    public int chalkReplacer(int[] chalk, int k) {

        // Each student consumes chalk in order, repeatedly in cycles.
        // We need to find the FIRST student who cannot be served
        // when chalk runs out.

        int numberOfStudents = chalk.length;

        // prefixSum[i] = total chalk used by students from index 0 to i
        // This represents cumulative chalk consumption in one full round
        long[] prefixSum = new long[numberOfStudents];

        // First student consumes chalk[0]
        prefixSum[0] = chalk[0];

        // Build prefix sum so we know:
        // how much chalk is used up to each student in one round
        for (int student = 1; student < numberOfStudents; student++) {
            prefixSum[student] = prefixSum[student - 1] + chalk[student];
        }

        // Total chalk used in one full round = prefixSum[last]
        long totalChalkPerRound = prefixSum[numberOfStudents - 1];

        // Instead of simulating many full rounds,
        // we only care about the REMAINING chalk after complete rounds
        //
        // Because once full rounds are done,
        // the problem reduces to a single pass again
        long remainingChalk = k % totalChalkPerRound;

        // Now we need to find the FIRST student such that:
        // prefixSum[student] > remainingChalk
        //
        // That student will NOT have enough chalk and is the answer

        int left = 0;
        int right = numberOfStudents - 1;

        // This will store the index of the student who runs out of chalk
        int studentIndex = 0;

        // Binary search on prefix sum array
        // to find the first prefixSum > remainingChalk
        while (left <= right) {

            int mid = left + (right - left) / 2;

            // If cumulative chalk needed up to mid exceeds remaining chalk,
            // then this student might be the answer
            // But we try to find an earlier one
            if (prefixSum[mid] > remainingChalk) {

                // mid is a valid candidate
                studentIndex = mid;

                // Search left to find the first such student
                right = mid - 1;
            }

            // If prefixSum[mid] <= remainingChalk,
            // then this student can still be served
            // So the failing student must be on the right
            else {
                left = mid + 1;
            }
        }

        // studentIndex is the first student who cannot be served
        return studentIndex;
    }
}
