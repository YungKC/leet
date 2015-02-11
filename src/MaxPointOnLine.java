import java.util.ArrayList;
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

public class MaxPointOnLine {

	static List<Point> pointArray = new ArrayList<Point>();
	static Map<String, Integer> pointCountMap = new HashMap<String, Integer>();

	public static int maxPoints(Point[] points) {
		
		// TODO: optimize - can group points with the same coordinates first to reduce comparison size
		for (Point point : points) {
			String pointIndex = point.x + "_" + point.y;
			Integer count = pointCountMap.get(pointIndex);
			if (count == null) {
				pointCountMap.put(pointIndex, 1);
				pointArray.add(point);
			}
			else
				pointCountMap.put(pointIndex, count+1);
		}
		
		if (pointArray.size() <= 2) {
			return points.length;
		}
		
		Map<String, Set<Integer>> score = new HashMap<String, Set<Integer>>();
		for (int i=0; i<pointArray.size(); i++) {
			for (int j=i+1; j<pointArray.size(); j++) {
				String ab = calAB(pointArray.get(i), pointArray.get(j));
//				System.out.println(i + ":" + j + " = " + ab);
				Set<Integer> curSet = score.get(ab);
				if (curSet == null) {
					curSet = new HashSet<Integer>();
					score.put(ab,  curSet);					
				}
				curSet.add(i);
				curSet.add(j);
			}
		}
		
		Iterator<Set<Integer>> it = score.values().iterator();
		if (!it.hasNext())
		    return 0;
		
		int max = getSize(it.next());
		while (it.hasNext()) {
			int curValue = getSize(it.next());
			if (curValue > max)
				max = curValue;
		}
		return max;
        
    }
    
	private static int getSize(Set<Integer> set) {
		int count = 0;
		for (Integer index : set) {
			Point point = pointArray.get(index);
			String key = point.x + "_" + point.y;
			count += pointCountMap.get(key);
		}
		return count;
	}

	private static String calAB(Point point1, Point point2) {
		if (point2.x - point1.x == 0)
			return point2.x+"m";		// for max
		float slope = ((float)(point2.y - point1.y)) / (point2.x - point1.x);
		
		float b = point1.y - slope * point1.x;
		return Math.round(slope * 10000) + "_" + Math.round(b * 10000);
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
	points[0] = new Point(0,0);
*/
		
/*
		points[0] = new Point(3,10);
		points[1] = new Point(0,2);
		points[2] = new Point(0,2);
		points[3] = new Point(3,10);
*/
		
/*		
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
