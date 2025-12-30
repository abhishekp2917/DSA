import java.util.List;
import java.util.ArrayList;

class Solution {
    public List<Integer> grayCode(int n) {
        List<Integer> grayCode = new ArrayList<>();
        int grayCodeCount = 1<<n;
        for(int i=0; i<grayCodeCount; i++) {
            grayCode.add(i^(i>>1));
        }
        return grayCode;
    }
}