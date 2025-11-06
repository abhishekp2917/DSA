package BitManipulation.Tricks;

class Clear_Kth_Bit {

    public static void main(String[] args) {
        int n = 14; // 1110
        int k = 1;

        // Explanation:
        //
        // Left Shift 1 by k times (1 << k) will create a mask with only k-th bit = 1
        // NOT operation on the mask will set all the other bits but k-th bit
        // AND operation of number with the new mask will clear the k-th bit as mask k-th bit = 0
        //
        // Example:
        // (1) n=14, k=1
        //     1 << k = 0010
        //     ~(1 << k) = 1101
        //     1110 & 1101 = 1100 (12) ANDing clears only that bit
        int result = n & ~(1 << k);

        System.out.println("After clearing " + k + "-th bit: " + result);
    }
}