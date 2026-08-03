import java.util.Arrays;

class Solution {
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        long minCost = 0;

        // Create and initialize a 26x26 matrix to store the minimum conversion cost
        // minCostMatrix[i][j] represents the minimum cost to convert character i+'a' to j+'a'
        long[][] minCostMatrix = new long[26][26];

        // Initially, set cost of converting i to j as infinity, and i to i as 0
        for (int i = 0; i < 26; i++) {
            Arrays.fill(minCostMatrix[i], Integer.MAX_VALUE);
            minCostMatrix[i][i] = 0; // No cost to convert a character to itself
        }

        // Fill in the direct transformation costs provided in the input
        for (int i = 0; i < original.length; i++) {
            int u = original[i] - 'a'; // from character
            int v = changed[i] - 'a';  // to character
            long edgeCost = cost[i];

            // If there are multiple conversions between the same characters, keep the minimum cost
            minCostMatrix[u][v] = Math.min(minCostMatrix[u][v], edgeCost);
        }

        // Use Floyd-Warshall algorithm to compute all-pairs minimum transformation cost
        // This helps find the cheapest indirect transformation path between any two characters
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    long i_To_k = minCostMatrix[i][k];
                    long k_To_j = minCostMatrix[k][j];

                    // Skip if there's no valid path from i to k or k to j
                    if (i_To_k == Integer.MAX_VALUE || k_To_j == Integer.MAX_VALUE) continue;

                    long i_To_j_newCost = i_To_k + k_To_j;
                    long i_To_j_prevCost = minCostMatrix[i][j];

                    // Update with the minimum cost between existing and newly found path
                    minCostMatrix[i][j] = Math.min(i_To_j_newCost, i_To_j_prevCost);
                }
            }
        }

        // Calculate the total cost to convert source string to target string
        for (int i = 0; i < source.length(); i++) {
            int srcChar = source.charAt(i);
            int trgChar = target.charAt(i);

            // If no valid transformation exists between characters, return -1
            if (minCostMatrix[srcChar - 'a'][trgChar - 'a'] == Integer.MAX_VALUE) return -1;

            // Add the cost of transforming current character to the total cost
            minCost += minCostMatrix[srcChar - 'a'][trgChar - 'a'];
        }

        // Return the total minimum cost to transform the source to target string
        return minCost;
    }
}
