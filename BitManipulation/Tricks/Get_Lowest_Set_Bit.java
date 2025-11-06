package BitManipulation.Tricks;

class Get_Lowest_Set_Bit {

    public static void main(String[] args) {
        int n = 12; // 1100

        // Explanation:
        // -n = 2's complement of n
        // 1100 & 0100 = 0100 -> lowest set bit isolated
        //
        // Example:
        // (1) n = 12
        //     12 = 1100 
        //     This gives the value of the lowest set bit:
        //     For 1100 → 0100 (which is 4)
        int lowest = n & -n;

        System.out.println("Lowest set bit value: " + lowest);
    }
}