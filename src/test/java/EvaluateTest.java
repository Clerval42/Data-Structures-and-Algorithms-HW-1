
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.Evaluate;

public class EvaluateTest {

    @Test
    public void testInfixToPostfix() {
        Assertions.assertEquals("2 3 4 * +", Evaluate.infixToPostfix("2+3*4"));
        Assertions.assertEquals("2 3 + 4 *", Evaluate.infixToPostfix("(2+3)*4"));
        Assertions.assertEquals("2 3 + 4 5 * +", Evaluate.infixToPostfix("2+3+4*5"));
        Assertions.assertEquals("2 3 4 * + 5 +", Evaluate.infixToPostfix("2+3*4+5"));
    }

    @Test
    public void testEvaluatePostfixExpression() {
        Assertions.assertEquals(14, Evaluate.evaluatePostfixExpression("2 3 4 * +"));
        Assertions.assertEquals(20, Evaluate.evaluatePostfixExpression("2 3 + 4 *"));
        Assertions.assertEquals(25, Evaluate.evaluatePostfixExpression("2 3 4 5 * + +"));
        Assertions.assertEquals(19, Evaluate.evaluatePostfixExpression("2 3 4 * + 5 +"));
    }
}