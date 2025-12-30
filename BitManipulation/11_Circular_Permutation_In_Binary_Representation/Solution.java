import java.util.List;
import java.util.ArrayList;

class Solution {
    public List<Integer> circularPermutation(int n, int start) {
        List<Integer> perm = new ArrayList<>();
        int grayCodeCount = (1<<n);
        int[] grayCode = new int[grayCodeCount];
        for(int i=0; i<grayCodeCount; i++) {
            grayCode[i] = i^(i>>1);
        }
        for(int i=0; i<grayCodeCount; i++) {
            perm.add(start^grayCode[i]);
        }
        return perm;
    }
}

