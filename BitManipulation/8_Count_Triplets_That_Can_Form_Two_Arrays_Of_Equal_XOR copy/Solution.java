class Solution1 {
    public int countTriplets(int[] arr) {
        int count = 0;
        int[] xorArr = new int[arr.length+1];
        for(int i=0; i<arr.length; i++) {
            xorArr[i+1] = xorArr[i] ^ arr[i];
        }
        for(int i=0; i<arr.length-1; i++) {
            for(int j=i+1; j<arr.length; j++) {
                for(int k=j; k<arr.length; k++) { 
                    int a = xorArr[j]^xorArr[i];
                    int b = xorArr[k+1]^xorArr[j];
                    if(a==b) count++;
                }
            }
        }
        return count;
    }
}

