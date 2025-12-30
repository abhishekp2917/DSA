package BitManipulation.Tricks;

class Get_Absolute_Value_Of_Num {

    public static void main(String[] args) {
        int n = -4;          // input number
        int mask = n >> 31;  // extracts sign bit: 0 for +ve, -1 for -ve
        int abs = (n ^ mask) - mask;

        // Explanation:
        // 
        // This trick computes absolute value using bit manipulation
        // without using branching (if-else).
        // 
        // mask = n >> 31
        // - If n is positive: mask = 0
        // - If n is negative: mask = -1 (all bits are 1)
        //
        // Formula used:
        // abs(n) = (n ^ mask) - mask
        //
        // Example:
        // (1) n = -4
        //
        //     n (binary)        mask            n ^ mask         (n ^ mask) - mask
        //     11111100 (-4)     11111111 (-1)   00000011 (3)     00000100 (4)
        //
        // (2) n = 4
        //
        //     n (binary)        mask            n ^ mask         (n ^ mask) - mask
        //     00000100 (4)      00000000 (0)    00000100 (4)     00000100 (4)
        //
        // For negative numbers:
        // XOR with mask flips all bits, and subtracting mask (+1)
        // completes two's complement conversion to positive value.

        System.out.println("Absolute value : " + abs);
    }
}
