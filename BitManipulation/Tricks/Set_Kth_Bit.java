package BitManipulation.Tricks;

class Set_Kth_Bit {

    public static void main(String[] args) {
        int n = 10; // 1010
        int k = 2;

        // Explanation:
        //
        // Left Shift 1 by k times (1 << k) will create a mask with only k-th bit = 1
        // Now, if we will perform Or operation on the number with this mask, it will set the kth bit
        //
        // Example: 
        // (1) n=10, k=0 
        //     10 = 1010
        //     mask     = 1 << 2 = 0100
        //     n | mask = 1110 = 14
        int result = n | (1 << k); 

        System.out.println("After setting " + k + "-th bit: " + result);
    }
}