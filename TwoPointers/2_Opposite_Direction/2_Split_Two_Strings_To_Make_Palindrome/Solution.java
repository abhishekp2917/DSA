class Solution {

    public boolean checkPalindromeFormation(String a, String b) {

        // We try two possibilities:
        // 1) Take prefix from 'a' and suffix from 'b'
        // 2) Take prefix from 'b' and suffix from 'a'
        // If either works, answer is true.
        return checkCross(a, b) || checkCross(b, a);
    }

    private boolean checkCross(String s1, String s2) {

        int left = 0;
        int right = s1.length() - 1;

        // Move inward while characters match in cross manner:
        // s1[left] should match s2[right]
        // This simulates taking prefix from s1 and suffix from s2.
        while (left < right && s1.charAt(left) == s2.charAt(right)) {
            left++;
            right--;
        }

        // If mismatch happens at some point,
        // remaining middle part must itself be palindrome.
        // We check:
        // 1) Remaining substring in s1
        // 2) Remaining substring in s2
        //
        // If either is palindrome, full string can form palindrome.
        return isPalindrome(s1, left, right) ||
               isPalindrome(s2, left, right);
    }

    private boolean isPalindrome(String s, int left, int right) {

        // Standard palindrome check between indices left and right.
        while (left < right) {

            if (s.charAt(left) != s.charAt(right))
                return false;

            left++;
            right--;
        }

        return true;
    }
}
