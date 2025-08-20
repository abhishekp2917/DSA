class Solution {
    
    public void subsequence(String str, int ptr, StringBuilder subSeq) {
        
        // ---- BASE CASE ----
        // If pointer reaches the end of the string:
        // Print the currently formed subsequence and return.
        if(ptr >= str.length()) {
            System.out.println(subSeq.toString());
            return; 
        }

        // ---- CHOICE 1: Include the current character ----
        // Add current character to subsequence
        subSeq.append(str.charAt(ptr));

        // Recurse to the next character with current char included
        subsequence(str, ptr + 1, subSeq);

        // ---- BACKTRACK ----
        // Remove the last character (undo choice)
        // to explore subsequences without the current character
        subSeq.deleteCharAt(subSeq.length() - 1);

        // ---- CHOICE 2: Exclude the current character ----
        // Move to the next character without adding current one
        subsequence(str, ptr + 1, subSeq);
    }
}
