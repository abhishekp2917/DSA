class Solution {
    public long wonderfulSubstrings(String word) {
        int[] stateFreq = new int[(int)Math.pow(2, 10)];
        stateFreq[0] = 1;
        long count = 0;
        int stateMask = 0;
        for(char ch : word.toCharArray()) {
            stateMask ^= (1<<(ch-'a'));
            count += stateFreq[stateMask];
            for(int i=0; i<10; i++) {
                int newState = stateMask ^ (1<<i);
                count += stateFreq[newState];
            }
            stateFreq[stateMask]++;
        } 
        return count;
    }
}

