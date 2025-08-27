class Solution {
	
    public int fib(int n) {
        // ---- BASE CASE ----
        // If n is 0, return 0 as fib(0) = 0
        // If n is 1, return 1 as fib(1) = 1
        // These are the terminating conditions for recursion.
        if (n <= 1) return n;

        // ---- RECURSIVE CASE ----
        // To calculate fib(n), we call fib(n-1) and fib(n-2).
        // This follows the definition:
        //   fib(n) = fib(n-1) + fib(n-2)
        // Example: fib(4) = fib(3) + fib(2)
        //   -> fib(3) = fib(2) + fib(1)
        //   -> fib(2) = fib(1) + fib(0)
        // Eventually breaks down to base cases.
        return fib(n - 1) + fib(n - 2);
    }
}
