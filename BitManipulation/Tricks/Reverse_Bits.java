package BitManipulation.Tricks;

class Reverse_Bits {

    public static void main(String[] args) {
        int n = 43261596; // 00000010100101000001111010011100
        int reverse = 0;

        // Explanation:
        // 
        // As we right shift 'n', we will left shift 'reverse' bit by bit
        // And as we left shift 'reverse', we will set or clear the LSB based on the LSB of 'n'
        // (n&1) will give if LSB of 'n' is set or clear
        // (reverse<<1) | (n&1) will left shift 'reverse' and will copy the LSB of 'n' to LSB of 'reverse'
        // 
        // Example: 
        // (1) n=43261596
        //     43261596, reverse                = 00000010100101000001111010011100, 00000000000000000000000000000000
        //     n >> 1, (reverse << 1) | (n&1)   = 00000001010010100000111101001110, 00000000000000000000000000000000
        //     n >> 1, (reverse << 1) | (n&1)   = 00000000101001010000011110100111, 00000000000000000000000000000001
        //     n >> 1, (reverse << 1) | (n&1)   = 00000000010100101000001111010011, 00000000000000000000000000000011
        //     n >> 1, (reverse << 1) | (n&1)   = 00000000001010010100000111101001, 00000000000000000000000000000111
        //     n >> 1, (reverse << 1) | (n&1)   = 00000000000101001010000011110100, 00000000000000000000000000001110
        //     n >> 1, (reverse << 1) | (n&1)   = 00000000000010100101000001111010, 00000000000000000000000000011100
        //     n >> 1, (reverse << 1) | (n&1)   = 00000000000001010010100000111101, 00000000000000000000000000111001
        //     ...
        for(int i=0; i<32; i++) {
            reverse = (reverse<<1) | (n&1);
            n >>= 1; 
        }

        System.out.println("Reversed bits : " + reverse); // 964176192 (00111001011110000010100101000000)
    }
}