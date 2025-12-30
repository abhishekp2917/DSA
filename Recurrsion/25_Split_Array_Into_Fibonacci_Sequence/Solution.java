import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> splitIntoFibonacci(String str) {
        List<Integer> fibSeq = new ArrayList<>();
        recursion(str, 0, 0, fibSeq);
        return fibSeq;
    }

    private boolean recursion(String str, long secLast, long last, List<Integer> fibSeq) {
        if(str=="") {
            if(fibSeq.size()>=3) return true;
            return false;
        }
        for(int len=1; len<=str.length(); len++) {
            long num = Long.parseLong(str.substring(0, len));
            if(len==2 && str.charAt(0)=='0') return false;
            if(num>Integer.MAX_VALUE || (fibSeq.size()>=2 && num>last+secLast)) break;
            if(fibSeq.size()>=2 && num!=last+secLast) continue;
            fibSeq.add((int)num);
            if(recursion(str.substring(len, str.length()), last, num, fibSeq)) return true;
            fibSeq.remove(fibSeq.size()-1);
        }
        return false;
    }
}