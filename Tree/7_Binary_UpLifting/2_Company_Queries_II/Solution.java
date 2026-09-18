import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {

    private final int[][] ancestors;
    private final int MAX_POW;
    private final int[] depth;

    public Solution(int n, int[] parent) {
        this.MAX_POW = (int)(Math.log(n)/Math.log(2))+1;
        this.ancestors = new int[n][this.MAX_POW];
        this.depth = getNodeDepth(n, parent);
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

    public int lowestCommonAncestor(int p, int q) {
        if(this.depth[p]<this.depth[q]) {
            int temp = p;
            p = q;
            q = temp;
        }
        int depthDiff = this.depth[p]-this.depth[q];
        if(depthDiff>0) p = getKthAncestor(p, depthDiff);
        if(p==q) return p;
        for(int pow=this.MAX_POW-1; pow>=0; pow--) {
            if(this.ancestors[p][pow]==this.ancestors[q][pow]) continue;
            p = this.ancestors[p][pow];
            q = this.ancestors[q][pow];
        }
        return this.ancestors[p][0];
    }

    private int getKthAncestor(int node, int k) {
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

    private int[] getNodeDepth(int n, int[] parents) {
        List<Integer>[] children = new ArrayList[n];
        int[] depth = new int[n];
        int root = -1;
        for(int node=0; node<n; node++) children[node] = new ArrayList<>();
        for(int node=0; node<n; node++) {
            int parent = parents[node];
            if(parent!=-1) {
                children[parent].add(node);
            }
            else root = node;
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            int node = queue.poll();
            for(int child : children[node]) {
                depth[child] = depth[node]+1;
                queue.add(child);
            }
        }
        return depth;
    }
}