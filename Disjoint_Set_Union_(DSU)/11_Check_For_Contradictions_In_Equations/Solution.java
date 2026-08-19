import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    /*
     * Core intuition:
     *
     * Treat every equation a / b = value as a weighted DSU relationship.
     *
     * parents[x] represents the parent of variable x.
     * weights[x] represents value(x / parent[x]).
     *
     * Therefore, after path compression:
     *
     *     weights[x] = value(x / root)
     *
     * If two variables already belong to the same component, their ratio
     * is already determined by the existing equations. If that determined
     * ratio differs from the newly given ratio, we have a contradiction.
     *
     * If they belong to different components, the new equation can be used
     * to connect the two components by calculating the required weight
     * between their roots.
     */
    public boolean checkContradictions(List<List<String>> equations, double[] values) {

        // First assign every unique variable a compact integer ID so that
        // it can be represented efficiently inside the DSU arrays.
        int n = 0;
        Map<String, Integer> variablesMap = new HashMap<>();

        for(List<String> equation : equations) {
            for(String variable : equation) {
                if(!variablesMap.containsKey(variable)) {
                    variablesMap.put(variable, n++);
                }
            }
        }

        int[] parents = new int[n];

        /*
            weights[a] = value(a/parent[a]) 
            initially, parent[a] = a 
            so, value(a/a) = 1 

            Example:

                a ---> b ---> c
                       ^
                       |
                d ---> e

                1. value(a/c) = value(a/b) * value(b/c) = value(a/b) * value(b/parent[b]) = value(a/b) * weights[b]
                2. value(d/a)
                        = value(d/e) * value(e/b) * (1/value(a/b)) 
                        = [value(d/e) * value(e/b) * value(b/c)] / [value(a/b) * value(b/c)]
                        = [value(d/e) * value(e/b) * weights(b)] / value(a/c)
                        = [value(d/e) * value(e/b) * weights(b)] / value(a/b) * value(b/c)
                        = [value(d/e) * weights(e)] / weights(a)
        */

        // Every variable initially forms a component by itself.
        // Since value(x / x) = 1, every initial weight is also 1.
        double[] weights = new double[n];
        Arrays.fill(weights, 1.0);

        for(int var=0; var<n; var++) parents[var] = var;

        for(int i=0; i<equations.size(); i++) {
            int var1 = variablesMap.get(equations.get(i).get(0));
            int var2 = variablesMap.get(equations.get(i).get(1));
            double value = values[i];

            // Find the roots of both variables.
            //
            // Path compression performed by findRootVariable() also updates
            // weights so that weights[var] becomes value(var / root).
            int var1Root = findRootVariable(parents, weights, var1);
            int var2Root = findRootVariable(parents, weights, var2);

            // weights[var1] = value(var1/parent[var1]) = value(var1/var1Root)
            // weights[var2] = value(var2/parent[var2]) = value(var2/var2Root) 

            if(var1Root==var2Root) {

                // Both variables are already connected, so the existing
                // equations already determine var1 / var2.
                //
                // Since:
                //
                //     value(var1 / var2)
                //       = value(var1 / root) / value(var2 / root)
                //
                // compare the implied ratio with the newly supplied equation.
                double actualRatio = weights[var1]/weights[var2];

                // A significant difference means the new equation contradicts
                // the ratios already established in this connected component.
                if (Math.abs(actualRatio - value) >= 1e-5) {
                    return true;
                }
            }
            else {

                // The two variables belong to different components.
                // Therefore, this equation introduces a relationship between
                // the two previously independent components.
                parents[var1Root] = var2Root;

                // weights[var1Root]  = value(var1Root / var2Root)
                //
                // value(var1Root / var2Root) 
                // 
                // = value(var1Root / var1) * value(var1 / var2) * value(var2 / var2Root)
                // = (1 / weights[var1]) * value * weights[var2]
                //
                // Therefore:
                // weights[var1Root] = value * weights[var2] / weights[var1]

                // This stores the exact ratio between the two roots required
                // by the newly processed equation.
                weights[var1Root] = value*weights[var2]/weights[var1];
            }
        }

        // Every equation was consistent with the ratios already established.
        return false;
    }


    private int findRootVariable(int[] parents, double[] weights, int var) {

        // If var is not its own parent, recursively find the component root.
        if(parents[var]!=var) {

            // Save the old parent because its weight is needed after the
            // recursive call. The recursive call may change parents[var].
            int oldParentVar = parents[var];

            // Path compression:
            // make var point directly to the root.
            parents[var] = findRootVariable(parents, weights, oldParentVar);

            // Before compression:
            //
            //     weights[var] = value(var / oldParentVar)
            //
            //     weights[oldParentVar] = value(oldParentVar / root)
            //
            // Therefore:
            //
            //     weights[var]
            //       = value(var / oldParentVar)
            //         * value(oldParentVar / root)
            //       = value(var / root)
            //
            // This allows all future ratio calculations to use var's direct
            // relationship with the root.
            weights[var] *= weights[oldParentVar];
        }

        return parents[var];
    }
}