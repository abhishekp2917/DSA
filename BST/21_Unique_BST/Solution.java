class Solution
{
    //Function to return the total number of possible unique BST.
    static int numTrees(int N)
    {
        int MOD = 1000000007;
        // declaring array to hold the answer
        long[] dp = new long[N+1];

        // dp[0] and dp[1] representing ossible combinatio of BST with size 0 and 1 resp
        dp[0] = 1;
        dp[1] = 1;

        // starting the loop from 2 as we already have answer for 0 and 1
        for(int i=2; i<dp.length; i++) {
            // local variable to store the result of possible combination of BST with size = i 
            long sum = 0;

            // calculating all the comination count of BST when one of the child tree size is j 
            for(int j=0; j<i; j++) {
                // if left subtree size is j and i is the size of entire tree
                // so right subtree will have size equal to total size - left subtree size - root 
                // i.e. i-j-1
                // so the possible BST combo when left subtree has size equal to j is 
                // (possible comination of i) * (possible comination of i-j-1)
                // finally adding the result to local variable
                sum = (sum + (dp[j]% MOD * dp[i - j - 1] % MOD) % MOD) % MOD;
            }

            // assigning the total possible combination of BST of size i to array
            dp[i] = sum;
        }
        return (int)dp[N];
    }
}