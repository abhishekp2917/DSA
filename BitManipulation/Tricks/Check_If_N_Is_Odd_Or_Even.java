package BitManipulation.Tricks;

class Check_If_N_Is_Odd_Or_Even {

    public static void main(String[] args) {
        int n = 11;

        // Explanation:
        //
        // The last bit (LSB) of any number determines if it's odd or even.
        // If last bit = 1 → number is odd
        // If last bit = 0 → number is even
        //
        // The operation -> 'n & 1' extracts the last bit.
        // Example: 
        // (1) 11 -> 1011
        //     1011 & 0001 = 0001 -> odd
        // (2) 10 -> 1010
        //     1010 & 0001 = 0000 -> even
        if ((n & 1) == 1) {
            System.out.println(n + " is odd");
        } else {
            System.out.println(n + " is even");
        }
    }
}
