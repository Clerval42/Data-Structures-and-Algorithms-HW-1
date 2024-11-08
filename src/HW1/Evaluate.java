package HW1;

import java.util.Stack;

public class Evaluate {

    static int precedence(String operator) {
        return switch (operator) {
            case "+", "-" ->
                1;
            case "*", "/" ->
                2;
            default ->
                -1;
        };
    }

    // Fill the method and return the value
    static String infixToPostfix(String expression) {
        /// Find the precedence
        // Do not forget to delete return "" statement
        String postfix = "";
        String currentNumber = "";
        Stack<String> stack = new Stack<>();
        for (char i : expression.toCharArray()) {

            if (Character.isDigit(i)) {
                currentNumber += Character.getNumericValue(i);
            }else if(i == ' '&& !"".equals(currentNumber)){ 
                stack.push(currentNumber);
                currentNumber = "";
            } else if (i == '(') { // If the character is (
                stack.push(String.valueOf(i));
            } else if (i == ')') { // If the character is )
                while (!stack.isEmpty() && stack.contains("("))  { // Pop until ( and )
                    postfix += stack.pop();
                }
                stack.pop();

            } else if (i == '+' || i == '-' || i == '*' || i == '/') { // If the character is +,-,*,/
                postfix += " ";

                while (!stack.isEmpty() && precedence(String.valueOf(i)) <= precedence(stack.peek())) {
                    postfix += stack.pop();
                    postfix += " ";

                }
                stack.push(String.valueOf(i));
            }
        }
        while (!stack.isEmpty()) {
            postfix += " ";
            postfix += stack.pop();
        }
        return postfix;
    }

    // Fill the method and return the value
    static int evaluatePostfixExpression(String expression) {
        //Do not forget to delete return 0 statement
        Stack<Integer> stack = new Stack<>();
        String currentNumber = "";
        for (char i : expression.toCharArray()) {
            
            if (Character.isDigit(i)) {
                currentNumber += Character.getNumericValue(i);

            }else if(i == ' '&& !"".equals(currentNumber)){ 
                stack.push(Integer.valueOf(currentNumber));
                currentNumber = "";


            }else if (precedence(String.valueOf(i)) > 0) {
                if(stack.size()>=0){
                    int a = Character.getNumericValue(stack.pop());
                    int b = Character.getNumericValue(stack.pop());
    
                    switch (i) {
                        case '+' ->
                            stack.push(a + b);
                        case '-' ->
                            stack.push(a - b);
                        case '*' ->
                            stack.push(a * b);
                        case '/' ->
                            stack.push(a / b);
                    }
                }
                
            }
            
        }
        return stack.pop();
                
    }
}
