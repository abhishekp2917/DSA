import java.util.List;
import java.util.ArrayList;

class Solution {

    // Pattern: Bit Manipulation → Gray Code Generation + XOR Shift
    //
    // Goal:
    // Generate a circular permutation of Gray codes of n bits,
    // starting from the given value `start`.

    public List<Integer> circularPermutation(int n, int start) {

        List<Integer> perm = new ArrayList<>();

        // Total number of Gray codes for n bits
        int grayCodeCount = (1 << n);

        // Array to store standard Gray code sequence
        int[] grayCode = new int[grayCodeCount];

        // Generate the Gray code sequence
        for (int i = 0; i < grayCodeCount; i++) {
            grayCode[i] = i ^ (i >> 1);
        }

        // Shift the Gray code sequence using XOR with start
        for (int i = 0; i < grayCodeCount; i++) {
            perm.add(start ^ grayCode[i]);
        }

        return perm;
    }
}
