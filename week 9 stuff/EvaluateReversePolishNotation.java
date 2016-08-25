package medium;
import java.util.*;
/**
 * https://leetcode.com/problems/evaluate-reverse-polish-notation/
 * 
 * <pre>
 * Polish: 1 + 2 = 3
 * 
 * Reverse polish: "1 2 +" => "1 + 2" = 3 -- 很自然用stack，碰到一个1就往回pop。
 * 
 * ["2", "1", "+", "3", "*"]  -> ((2 + 1) * 3)  -> 9 
 * ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 * </pre>
 */
public class EvaluateReversePolishNotation {
	/**
	 * valid input assumed: first one is number, not operand.
	 */
	public int evalRPN(String[] tokens) {
		Stack<Integer> stack = new Stack<>();
		for (String tok : tokens)
			if (isOper(tok)) {
				int right = stack.pop();
				int left = stack.pop();
				stack.push(oper(tok, left, right));
			} else
				stack.push(Integer.parseInt(tok));
		return stack.pop();
	}

	private int oper(String tok, int left, int right) {
		switch (tok) {
			case "+" : // if (tok.equals("+"))
				return left + right;
			case "-" : // if (tok.equals("-"))
				return left - right;
			case "*" : // if (tok.equals("*"))
				return left * right;
			case "/" : // if (tok.equals("/"))
				return left / right;
		}
		return Integer.MAX_VALUE; // error
	}
	private static final List<String> VALID_OPER = Arrays.asList(new String[]{"+", "-", "*", "/"}); // or:set
	private boolean isOper(String tok) {
		return VALID_OPER.contains(tok);
	}
}
