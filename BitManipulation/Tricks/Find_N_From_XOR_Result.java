package BitManipulation.Tricks;

public class Find_N_From_XOR_Result {
    
    
    public static void main(String[] args) {
        int a = 5; // binary: 101
        int b = 7; // binary: 111

        // Explanation:
        //
        // If a ^ b = c, then a ^ c = b and c ^ b = a 
        //
        // Example:
        // (1) a=5, b=7, c=2
        //     a ^ b = 101 ^ 111 = 010 (2)
        //     a ^ c = 101 ^ 010 = 111 (7)
        //     b ^ c = 111 ^ 010 = 101 (5)
        int c = (a ^ b);
        System.out.println("A : " + a + "B :  " + b + "C : " + c);
    }
}
