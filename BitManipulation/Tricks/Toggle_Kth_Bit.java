package BitManipulation.Tricks;

class Toggle_Kth_Bit {

    public static void main(String[] args) {
        int n = 10; // 1010
        int k = 1;

        // Explanation:
        // 
        // Left Shift 1 by k times (1 << k) will create a mask with only k-th bit = 1
        // Now, if we will perform XOR operation on the number with this mask, then XOR with mask flips the kth bit
        // bacause 1 ^ 1 -> 0 and 0 ^ 1 -> 1
        // 
        // Example: 
        // (1) n=10, k=1
        //     10 = 1010
        //     mask = 1 << 1 = 0010
        //     1010 ^ mask = 1010 ^ 0010 = 1000 (8)
        //     1000 ^ mask = 1000 ^ 0010 = 1010 (10)
        int result = n ^ (1 << k);

        System.out.println("After toggling " + k + "-th bit: " + result);
    }
}