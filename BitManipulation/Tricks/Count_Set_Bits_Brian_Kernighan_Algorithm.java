package BitManipulation.Tricks;

class Count_Set_Bits_Brian_Kernighan_Algorithm {

    public static void main(String[] args) {
        int n = 29; // 11101
        int count = 0;

        // Explanation:
        //
        // (n - 1) clears the lowest set bit and will act as a mask.
        // n & (n - 1) removes the lowest set bit.
        // Repeat until n becomes zero.
        //
        // 11101 & 11100 -> 11100
        // 11100 & 11011 -> 11000
        // 11000 & 10111 -> 10000
        // 10000 & 01111 -> 00000  (4 iterations = 4 set bits)
        while (n > 0) {
            n = n & (n - 1);
            count++;
        }

        System.out.println("Set bits count: " + count);
    }
}