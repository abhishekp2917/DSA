package BitManipulation.Tricks;

class Generate_Gray_Code {

    public static void main(String[] args) {
        int n = 4;          // to generate 4-bit gray code
        int count = (1<<4); // number of gray codes
        int[] grayCode = new int[count];
        // Explanation:
        // 
        // Gray codes are sequence of numbers in which every next num differs from it's previous num by 1 bit
        // 
        // Example: 
        // (1) n=4
        //     
        //     i (binary)	i>>1	i ^ (i>>1)    	Result
        //     000 (0)	    000	    000 ^ 000	    000
        //     001 (1)	    000	    001 ^ 000	    001
        //     010 (2)	    001	    010 ^ 001	    011
        //     011 (3)	    001	    011 ^ 001	    010
        //     100 (4)	    010	    100 ^ 010	    110
        //     101 (5)	    010	    101 ^ 010	    111
        //     110 (6)	    011	    110 ^ 011	    101
        //     111 (7)	    011	    111 ^ 011	    100
        for(int i=0; i<count; i++) {
            grayCode[i] = i^(i>>1);
            System.out.println(grayCode[i]);
        }
    }
}


