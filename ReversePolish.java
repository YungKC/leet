package leet;

import java.util.LinkedList;

/*
 * 
Evaluate the value of an arithmetic expression in Reverse Polish Notation.

Valid operators are +, -, *, /. Each operand may be an integer or another expression.

Some examples:
  ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
  ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
  
  
 */
public class ReversePolish {

    private static LinkedList<Integer> myQueue = new LinkedList<Integer>();

	public static int evalRPN(String[] tokens) {
        for (String token : tokens) {
        	if (token.equals("+"))
        		plus();
        	else if (token.equals("-"))
        		minus();
        	else if (token.equals("*"))
        		times();
        	else if (token.equals("/"))
        		divide();
        	else {
        		myQueue.push(Integer.parseInt(token));
        	
        	}
        }
        return myQueue.pop();
    }
    
	private static void plus() {
		myQueue.push(myQueue.pop() + myQueue.pop());		
	}

	private static void minus() {
		myQueue.push(- myQueue.pop() + myQueue.pop());		
	}
	
	private static void times() {
		myQueue.push( myQueue.pop() * myQueue.pop());		
	}

	private static void divide() {
		int a = myQueue.pop();
		myQueue.push(myQueue.pop()/a);		
	}
	public static void main(String[] args) {
		String[] input = {"2", "1", "-", "3", "*"};
		System.out.println(evalRPN(input));
		

	}

}
