/*
 * 
Implement int sqrt(int x).

Compute and return the square root of x.

 */

public class SquareRoot2 {

    public static int sqrt2(int x) {
    	if (x <= 0)
    		return 0;
    	
    	// use binary search
    	int left = 1;
    	int right = x;
    	
        for (;;) {
        	int guess = left + (right - left)/2;
        	int square = guess * guess;
        	if (square > x)
        		right = guess;
        	else if (square < x )
        		left = guess;
        	else if (square == x)
        		return guess;
        	
        	if (right - left <= 1)
        		return left;
        }
    }
    
	public static void main(String[] args) {
		System.out.println(sqrt2(2546435));
	}

}
