package HW1;

import java.util.Stack;

public class Evaluate {

    static int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    static String infixToPostfix(String expression) {
        String postfix = "";
        Stack<Character> stack = new Stack<>();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                currentNumber.append(c);
                // If it's last character or next is not digit, add the number
                if (i == expression.length() - 1 || !Character.isDigit(expression.charAt(i + 1))) {
                    postfix += currentNumber.toString();
                    postfix += " ";
                    currentNumber.setLength(0);
                }
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix += stack.pop();
                    postfix += " ";
                }
                stack.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!stack.isEmpty() && stack.peek() != '(' && 
                       precedence(c) <= precedence(stack.peek())) {
                    postfix += stack.pop();
                    postfix += " ";
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() != '(') {
                postfix += stack.pop();
                postfix += " ";
            } else {
                stack.pop();
            }
        }

        return postfix.trim();
    }

    static int evaluatePostfixExpression(String expression) {
        Stack<Integer> stack = new Stack<>();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                currentNumber.append(c);
            } else if (c == ' ' && currentNumber.length() > 0) {
                stack.push(Integer.valueOf(currentNumber.toString()));
                currentNumber.setLength(0);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                if (stack.size() >= 2) {
                    int b = stack.pop();
                    int a = stack.pop();

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

        return stack.isEmpty() ? 0 : stack.pop();
    }

    public static void main(String[] args) {
        // Test the implementation
        String expression = "20+2*3+(2*8+5)*4";
        String postfix = infixToPostfix(expression);
        System.out.println("Infix: " + expression);
        System.out.println("Postfix: " + postfix);
        System.out.println("Result: " + evaluatePostfixExpression(postfix));
    }
}