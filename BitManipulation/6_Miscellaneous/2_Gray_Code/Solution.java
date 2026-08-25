import java.util.List;
import java.util.ArrayList;

class Solution {

    // Pattern: Bit Manipulation → Binary to Gray Code Transformation
    //
    // Goal:
    // Generate a sequence of Gray codes of length 2^n where
    // consecutive numbers differ by exactly one bit.

    public List<Integer> grayCode(int n) {

        List<Integer> grayCode = new ArrayList<>();

        // Total number of Gray codes for n bits is 2^n
        int grayCodeCount = 1 << n;

        // Generate Gray code for each number from 0 to 2^n - 1
        for (int i = 0; i < grayCodeCount; i++) {

            // Convert binary number i to its Gray code representation
            grayCode.add(i ^ (i >> 1));
        }

        return grayCode;
    }
}
