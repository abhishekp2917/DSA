import java.util.*;

class Solution {

    public int evalRPN(String[] tokens) {

        // Stack to store operands
        Stack<Integer> stack = new Stack<>();

        // --------------------------------------------------
        // STEP 1: Traverse tokens
        // --------------------------------------------------
        for (int i = 0; i < tokens.length; i++) {

            String token = tokens[i];

            switch (token) {

                // --------------------------------------------------
                // ADDITION
                // --------------------------------------------------
                case "+" : {

                    // Pop last two operands
                    int num1 = stack.pop(); // top (right operand)
                    int num2 = stack.pop(); // second top (left operand)

                    // Order doesn't matter for +
                    stack.push(num1 + num2);
                    break;
                }

                // --------------------------------------------------
                // SUBTRACTION
                // --------------------------------------------------
                case "-" : {

                    int num1 = stack.pop(); // right operand
                    int num2 = stack.pop(); // left operand

                    // IMPORTANT: order matters
                    // num2 - num1 (NOT num1 - num2)
                    stack.push(num2 - num1);
                    break;
                }

                // --------------------------------------------------
                // DIVISION
                // --------------------------------------------------
                case "/" : {

                    int num1 = stack.pop(); // right operand
                    int num2 = stack.pop(); // left operand

                    // IMPORTANT: order matters
                    stack.push(num2 / num1);
                    break;
                }

                // --------------------------------------------------
                // MULTIPLICATION
                // --------------------------------------------------
                case "*" : {

                    int num1 = stack.pop();
                    int num2 = stack.pop();

                    stack.push(num1 * num2);
                    break;
                }

                // --------------------------------------------------
                // NUMBER
                // --------------------------------------------------
                default : {

                    // Push operand to stack
                    stack.push(Integer.parseInt(token));
                }
            }
        }

        // Final result remains in stack
        return stack.pop();
    }
}