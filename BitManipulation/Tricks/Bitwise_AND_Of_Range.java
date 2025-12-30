package BitManipulation.Tricks;

class Bitwise_AND_Of_Range {

    public static void main(String[] args) {
        int left = 5;
        int right = 7;

        // Explanation:
        //
        // Bitwise AND of a range [left, right] keeps only the common
        // leftmost (prefix) bits of both numbers.
        //
        // Any bit position that changes within the range becomes 0.
        //
        // Strategy:
        // Repeatedly remove the lowest set bit from 'right' until
        // right <= left.
        //
        // Operation used:
        // right & (right - 1) removes the lowest set bit.
        //
        // Example:
        // (1) left = 5  (101)
        //     right = 7 (111)
        //
        //     Step 1: right = 111 & 110 = 110 (6)
        //     Step 2: right = 110 & 101 = 100 (4)
        //
        // Now right < left → stop
        // Result = 100 (4)
        //
        while (left < right) {
            right = right & (right - 1);
        }

        System.out.println("Bitwise AND of range [" + left + ", " + right + "] = " + right);
    }
}
