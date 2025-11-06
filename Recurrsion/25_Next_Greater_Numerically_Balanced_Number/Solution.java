class Solution {
    public int nextBeautifulNumber(int n) {
        int[] smallestNum = new int[] {Integer.MAX_VALUE};
        recursion(n, new int[] {0, 1, 2, 3, 4, 5, 6}, 0, smallestNum);
        return smallestNum[0];
    }

    private void recursion(int n, int[] count, int num, int[] smallestNum) {
        if(num>=smallestNum[0]) return;
        if(num>n) {  
            boolean isValid = true;
            for(int i=1; i<=6; i++) {
                if((count[i]!=0 && count[i]!=i)) isValid = false;
            }
            if(isValid) {
                smallestNum[0] = Math.min(smallestNum[0], num);
                return;
            }
        }
        for(int i=1; i<=6; i++) {
            if(count[i]==0) continue;
            count[i]--;
            recursion(n, count, num*10+i, smallestNum);
            count[i]++;
        }
    }
}






