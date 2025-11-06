package BitManipulation.Tricks;

class Check_If_Kth_Bit_Is_Set {

    public static void main(String[] args) {
        int n = 10; // binary: 1010
        int k = 1;  // check 1st bit from right (0-indexed)

        // Explanation:
        //
        // If we shift the bits k times to the right, the kth bit will eventually be placed at last bit (LSB).
        // Now extract the last bit via operation -> 'n & 1' and check:
        //
        // If last bit = 1 -> kth bit is set
        // If last bit = 0 -> kth bit is not set
        //
        // Example:
        // (1) n=10, k=1
        //     Shift n right by k positions: 1010 >> 1 = 0101
        //     Now extract last bit: 0101 & 0001 = 1 -> kth bit is set
        int bit = (n >> k) & 1;

        System.out.println("Is " + k + "-th bit set? " + (bit == 1));
    }
}
