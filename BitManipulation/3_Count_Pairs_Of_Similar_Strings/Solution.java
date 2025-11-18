class Solution {
    public int similarPairs(String[] words) {
        int count = 0;
        int[] bitmask = new int[words.length];
        for(int i=0; i<words.length; i++) {
            String word = words[i];
            int mask = 0;
            for(char ch : word.toCharArray()) {
                mask = mask | (1 << (ch-'a'));
            }
            bitmask[i] = mask;
        }
        for(int i=0; i<words.length-1; i++) {
            for(int j=i+1; j<words.length; j++) {
                count += ((bitmask[i]^bitmask[j])==0)?1:0;
            }
        }
        return count;
    }

}

