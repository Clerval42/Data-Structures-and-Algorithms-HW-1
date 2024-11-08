package HW1;

import java.util.Stack;

public class Evaluate {
    
    /**
     * Determines the precedence of operators
     * Higher number means higher precedence
     */
    static int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    /**
     * Converts an infix expression to postfix notation
     * Example: "2+3*4" becomes "2 3 4 * +"
     */
    static String infixToPostfix(String expression) {
        String postfix = "";  // Will store the final postfix expression
        Stack<Character> stack = new Stack<>();  // Stack for operators
        StringBuilder currentNumber = new StringBuilder();  // For building multi-digit numbers

        // Process each character in the expression
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If the character is a digit
            if (Character.isDigit(c)) {
                currentNumber.append(c);
                // If it's last character or next is not digit, add the complete number
                if (i == expression.length() - 1 || !Character.isDigit(expression.charAt(i + 1))) {
                    postfix += currentNumber.toString();
                    postfix += " ";  // Add space after number
                    currentNumber.setLength(0);  // Reset for next number
                }
            }
            // If opening parenthesis, push to stack
            else if (c == '(') {
                stack.push(c);
            }
            // If closing parenthesis, pop all operators until matching opening parenthesis
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix += stack.pop();
                    postfix += " ";
                }
                stack.pop();  // Remove the opening parenthesis
            }
            // If operator (+, -, *, /)
            else if (precedence(c) >= 0) {
                // Pop operators with higher or equal precedence
                while (!stack.isEmpty() && stack.peek() != '(' && 
                       precedence(c) <= precedence(stack.peek())) {
                    postfix += stack.pop();
                    postfix += " ";
                }
                stack.push(c);  // Push current operator
            }
        }

        // Pop remaining operators from stack
        while (!stack.isEmpty()) {
            if (stack.peek() != '(') {
                postfix += stack.pop();
                postfix += " ";
            } else {
                stack.pop();
            }
        }

        return postfix.trim();  // Remove trailing spaces
    }

    /**
     * Evaluates a postfix expression and returns the result
     * Example: "2 3 4 * +" evaluates to 14
     */
    static int evaluatePostfixExpression(String expression) {
        Stack<Integer> stack = new Stack<>();  // Stack for operands
        StringBuilder currentNumber = new StringBuilder();  // For building multi-digit numbers

        // Process each character in the expression
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If digit, collect it as part of number
            if (Character.isDigit(c)) {
                currentNumber.append(c);
            }
            // If space and we have collected digits, convert to number and push to stack
            else if (c == ' ' && currentNumber.length() > 0) {
                stack.push(Integer.valueOf(currentNumber.toString()));
                currentNumber.setLength(0);  // Reset for next number
            }
            // If operator, perform operation on top two numbers in stack
            else if (precedence(c) >= 0) {
                if (stack.size() >= 2) {
                    int b = stack.pop();
                    int a = stack.pop();

                    // Perform the operation and push result back to stack
                    switch (c) {
                        case '+' -> stack.push(a + b);
                        case '-' -> stack.push(a - b);
                        case '*' -> stack.push(a * b);
                        case '/' -> stack.push(a / b);
                    }
                }
            }
        }

        // Handle any remaining number at the end
        if (currentNumber.length() > 0) {
            stack.push(Integer.valueOf(currentNumber.toString()));
        }

        return stack.pop();  // Return final result
    }
}