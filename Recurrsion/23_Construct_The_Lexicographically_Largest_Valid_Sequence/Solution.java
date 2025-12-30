class Solution {
    public int[] constructDistancedSequence(int n) {
        int[] sequence = new int[2*n-1];
        recursion(n, 0, new boolean[n+1], sequence);
        return sequence;
    }

    private boolean recursion(int n, int ptr, boolean[] used, int[] sequence) {
        while(ptr<sequence.length && sequence[ptr]!=0) {
            ptr++;
        }
        if(ptr==sequence.length) {
            return true;
        }
        for(int i=n; i>=1; i--) {
            if(used[i]) continue;
            if(i==1) {
                used[i] = true;
                sequence[ptr] = i;
                if(recursion(n, ptr+1, used, sequence)) return true;
                sequence[ptr] = 0;
                used[i] = false;
            }
            else {
                if(ptr+i>=sequence.length || sequence[ptr+i]!=0) continue;
                used[i] = true;
                sequence[ptr] = i;
                sequence[ptr+i] = i;
                if(recursion(n, ptr+1, used, sequence)) return true;
                sequence[ptr] = 0;
                sequence[ptr+i] = 0;
                used[i] = false;
            }
        }
        return false;
    }
}