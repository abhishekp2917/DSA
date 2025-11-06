package BitManipulation.Tricks;

class Remove_Lowest_Set_Bit {

     public static void main(String[] args) {
        int n = 12; // 1100

        // Explanation:
        // 
        // n - 1 flips all bits after the lowest set bit,
        // then AND removes the lowest set bit.
        //
        // 1100 (12)
        // 1011 (11)
        // 1100 & 1011 = 1000 (8)
        int result = n & (n - 1);

        System.out.println("After removing lowest set bit: " + result);
    }
}