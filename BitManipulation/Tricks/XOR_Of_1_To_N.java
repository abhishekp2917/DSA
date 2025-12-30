package BitManipulation.Tricks;

class XOR_Of_1_To_N {

    public static void main(String[] args) {
        int n = 10;

        // Explanation:
        //
        // XOR follows a repeating pattern when applied from 1 to n.
        // The pattern repeats every 4 numbers.
        //
         // Step-by-step XOR:
        //
        // n = 1
        // 1                     = 1
        //
        // n = 2
        // 1 ^ 2                 = 3
        //
        // n = 3
        // 1 ^ 2 ^ 3             = 0
        //
        // n = 4
        // 1 ^ 2 ^ 3 ^ 4         = 4
        //
        // n = 5
        // 1 ^ 2 ^ 3 ^ 4 ^ 5     = 1
        //
        // n = 6
        // 1 ^ 2 ^ 3 ^ 4 ^ 5 ^ 6 = 7
        //
        // n = 7
        // 1 ^ 2 ^ 3 ^ 4 ^ 5 ^ 6 ^ 7 = 0
        //
        // n = 8
        // 1 ^ 2 ^ 3 ^ 4 ^ 5 ^ 6 ^ 7 ^ 8 = 8
        //
        // Observed pattern:
        //
        // n % 4 == 0 → XOR = n
        // n % 4 == 1 → XOR = 1
        // n % 4 == 2 → XOR = n + 1
        // n % 4 == 3 → XOR = 0
        //
        // Reason for repetition:
        //
        // XOR(1..4)   = 4
        // XOR(5..8)   = 4
        // XOR(9..12)  = 4
        //
        // Reason:
        // XOR of consecutive numbers cancels out bits in a predictable cycle.
        //
        // Example:
        // (1) n = 10
        //     n % 4 = 2
        //     XOR(1..10) = 11
        //
        int xor;

        if (n % 4 == 0) xor = n;
        else if (n % 4 == 1) xor = 1;
        else if (n % 4 == 2) xor = n + 1;
        else xor = 0;

        System.out.println("XOR from 1 to " + n + " = " + xor);
    }
}
