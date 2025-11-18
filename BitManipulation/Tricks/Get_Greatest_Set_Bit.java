package BitManipulation.Tricks;

class Get_Greatest_Set_Bit {

    public static void main(String[] args) {
        int n = 12; // 1100
        int pos = -1;
        // Explanation:
        // if performing (n >> 1) N times makes n = 0, then N is the most significant set bit of n. 
        // 
        // Example:
        // (1) n = 12
        //     12 = 1100 
        //     n >> 1 = 0110
        //     n >> 1 = 0011
        //     n >> 1 = 0001
        //     n >> 1 = 0000
        //     N = 4 i.e 4th bit from right is the most significant set bit of n.
        while(n>0) {
            pos++;
            n >>= 1;
        }

        System.out.println("Greatest set bit position: " + pos);
    }
}