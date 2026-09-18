class Solution {

    private final int[][] ancestors;
    private final int MAX_POW;

    public Solution(int n, int[] parent) {
        
        this.MAX_POW = (int)(Math.log(n)/Math.log(2))+1;
        this.ancestors = new int[n][this.MAX_POW];

        for(int node=0; node<n; node++) {
            this.ancestors[node][0] = parent[node];
        }
        for(int pow=1; pow<this.MAX_POW; pow++) {
            for(int node=0; node<n; node++) {
                int midAncestorNode = this.ancestors[node][pow-1];
                if(midAncestorNode==-1) {
                    this.ancestors[node][pow] = -1;
                }
                else {
                    this.ancestors[node][pow] = this.ancestors[midAncestorNode][pow-1];
                }
            }
        }
    }

    public int getKthAncestor(int node, int k) {
        int ancestor = node;
        for(int pow=this.MAX_POW-1; pow>=0; pow--) {
            int powOfTwo = 1<<pow;
            if(powOfTwo>k) continue;
            ancestor = this.ancestors[ancestor][pow];
            if(ancestor==-1) break;
            k -= powOfTwo;
        }
        return ancestor;
    }
}