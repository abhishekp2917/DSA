class Solution {
    public boolean isAdditiveNumber(String num) {
        return recursion(num, -1, -1, -1);
    }

    private boolean recursion(String num, long thirdLast, long secLast, long last) {
        if(num.length()==0) return (thirdLast!=-1 && secLast!=-1 && last!=-1);
        long newNum = 0;
        for(int i=0; i<num.length(); i++) {
            newNum = newNum*10 + num.charAt(i)-'0'; 
            if(String.valueOf(newNum).length()<(i+1)) return false;
            if(newNum>10000000000000000L || (secLast!=-1 && last!=-1 && newNum>secLast+last)) break;
            else if(secLast==-1) {
                if(recursion(num.substring(i+1, num.length()), secLast, last, newNum)) return true;
            }
            else if(newNum==secLast+last) {
                if(recursion(num.substring(i+1, num.length()), secLast, last, newNum)) return true;
            }
        }
        return false;
    }
}

