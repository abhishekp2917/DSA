package BitManipulation.Tricks;

class Complement_Of_N {

    public static void main(String[] args) {
        int n = 12; // 00001100 
        int mask = createMaskTillGreatestSetBit(n); // 00001111
        // Explanation:
        // ~n will set all the trailing zeros on left side which we don't want
        // to clear those set bit, we need a mask  
        // (~n) & mask will give correct complement
        //
        // Example:
        // (1) n = 12
        //     12           = 0000000000001100 
        //     ~n           = 1111111111110011
        //     mask         = 0000000000001111
        //     (~n) & mask  = 0000000000000011
        int complement = (~n) & mask;

        System.out.println("Complement: " + complement);
    }

    private static int createMaskTillGreatestSetBit(int n) {
        int mask = 0;
        while(n>0) {
            mask = (mask<<1) | 1;
            n >>= 1;
        }
        return mask;
    }
}