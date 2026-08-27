class Solution {

    // Pattern: Recursion + Index Transformation (Josephus / Elimination Game)
    //
    // Idea:
    // Each round eliminates one person, reducing the problem size from n to n-1.
    // The winner position from the smaller problem is then mapped back to the
    // original circle using modular arithmetic.

    public int findTheWinner(int n, int k) {

        // Base case: only one person remains
        if (n == 1) return 1;

        // Calculate the starting position for the next round
        // This represents where counting resumes after elimination
        // If Kth is removed, next start would be k+1, but since circular problems are easiest in 0-based indexing,
        // first we convert it to 0-based by subtracting k+1 by 1
        // then apply the %n wrap-around
        // and finally convert it back to 1-based indexing. 
        int nextStart = ((k + 1) - 1) % n + 1;

        // Recursively find the winner in the reduced circle
        int winner = findTheWinner(n - 1, k);

        // Map the winner's position back to the current circle
        // first we would convert nextStart and winner to 0-based, then apply %n wrap-around and 
        // finally convert it back to 1-based by adding one to result. 
        return ((nextStart-1 ) + (winner - 1)) % n + 1;
    }
}


