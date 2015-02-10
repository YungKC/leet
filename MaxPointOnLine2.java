package leet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 
Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.

 */

public class MaxPointOnLine2 {

	public static int maxPoints(Point[] points) {		
		
		if (points.length <= 2) {
			return points.length;
		}

		int max = 0;
		for (int i=0; i<points.length; i++) {
			Map<Integer, Integer> score = new HashMap<Integer, Integer>();
			int dup = 0;
			for (int j=0; j<points.length; j++) {
				if (i==j)
					continue;
				if ((points[i].x == points[j].x) && (points[i].y == points[j].y)) {
					dup++;
					continue;
				}
				int slope = getSlope(points[i], points[j]);
				Integer value = score.get(slope);
				if (value == null)
					score.put(slope, 2);
				else
					score.put(slope, value+1);
			}
			
			// for special case where all points are the same location, where n > 2
			if ((dup+1) > max)
				max = dup+1; 
			
			Iterator<Integer> tmpResult = score.values().iterator();
			while (tmpResult.hasNext()) {
				int aCount = tmpResult.next() + dup;
				if (aCount > max)
					max = aCount;
			}
		}
		
		return max;
        
    }
    
	private static int getSlope(Point point1, Point point2) {
		if (point2.x - point1.x == 0)
			return Integer.MAX_VALUE;		// for max
		float slope = ((float)(point2.y - point1.y)) / (point2.x - point1.x);
		
		return Math.round(slope * 10000);
	}


	public static void main(String[] args) {
		
		Point[] points = new Point[9];
		points[0] = new Point(84,250);
		points[1] = new Point(0,0);
		points[2] = new Point(1,0);
		points[3] = new Point(0,-70);
		points[4] = new Point(0,-70);
		points[5] = new Point(1,-1);
		points[6] = new Point(21,10);
		points[7] = new Point(42,90);
		points[8] = new Point(-42,-230);
		
		
/*
		Point[] points = new Point[9];
		points[0] = new Point(84,250);
		points[1] = new Point(0,0);
		points[2] = new Point(1,0);
		points[3] = new Point(0,-70);
		points[4] = new Point(0,-70);
		points[5] = new Point(1,-1);
		points[6] = new Point(21,10);
		points[7] = new Point(42,90);
		points[8] = new Point(-42,-230);
*/

/*
		Point[] points = new Point[3];
		points[0] = new Point(1,1);
		points[1] = new Point(1,1);
		points[2] = new Point(1,1);
*/
		
/*
		Point[] points = new Point[1];
		points[0] = new Point(0,0);
*/
		
/*
		Point[] points = new Point[4];
		points[0] = new Point(3,10);
		points[1] = new Point(0,2);
		points[2] = new Point(0,2);
		points[3] = new Point(3,10);
*/
		
/*		
		Point[] points = new Point[6];
		points[0] = new Point(1,1);
		points[1] = new Point(1,2);
		points[2] = new Point(1,3);
		points[3] = new Point(2,1);
		points[4] = new Point(4,1);
		points[5] = new Point(8,1);
*/
		
		
		System.out.println(maxPoints(points));
	}

}
