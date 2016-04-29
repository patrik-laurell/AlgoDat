import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.io.InputStreamReader;
import java.io.BufferedReader;

class FindCP {

	public static void main(String[] args) {
		ArrayList<Point> points = new ArrayList<Point>();
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String line = br.readLine();
			ArrayList<String> list = new ArrayList<String>();
			while (!line.equals("EOF") && !line.isEmpty()) {
				String[] str = line.split(" ");
				if (str.length > 2) {
					list = new ArrayList<String>();
					int k = 0;
					for (int i = 0; i < str.length; i++) {
						if (!str[i].isEmpty()) {
							list.add(k, str[i]);
							k++;
						}
					}
					try {
						double y = Double.parseDouble(list.get(2));
						double x = Double.parseDouble(list.get(1));
						points.add(new Point(list.get(0), x, y));
						line = br.readLine();
					} catch (NumberFormatException e) {
						line = br.readLine();
					}
				} else {
					line = br.readLine();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Point[] xSorted = points.toArray(new Point[points.size()]);
		Point[] ySorted = points.toArray(new Point[points.size()]);

		Arrays.sort(xSorted, new xComparator<Point>()); // O(nlog(g))
		Arrays.sort(ySorted, new yComparator<Point>());

		for (int i = 0; i < xSorted.length; i++) { // O(n)
			xSorted[i].xListIndex = i;
			ySorted[i].yListIndex = i;
		}
		Point[] cp = cpRec(xSorted, ySorted);
		System.out.println(points.size() + " " + distance(cp[0], cp[1]));
	}

	private static Point[] cpRec(Point[] xx, Point[] yy) {
		Point[] pair = new Point[2];
		if (xx.length < 4) {
			int p1 = 0, p2 = 0;
			double min = Double.MAX_VALUE;
			for (int i = 0; i < xx.length - 1; i++) {
				for (int j = i + 1; j < xx.length; j++) {
					double d = distance(xx[i], xx[j]);
					if (d < min) {
						min = d;
						p1 = i;
						p2 = j;
					}
				}
			}

			pair[0] = xx[p1];
			pair[1] = xx[p2];
			return pair;
		} else {
			Point[] qx = Arrays.copyOfRange(xx, 0, xx.length / 2);
			Point[] rx = Arrays.copyOfRange(xx, xx.length / 2, xx.length);

			int xIndexMax = 0;
			for (int i = 0; i < qx.length; i++) {
				if(qx[i].xListIndex > xIndexMax) {
					xIndexMax = qx[i].xListIndex;
				}
			}
			Point[] qy = new Point[qx.length];
			Point[] ry = new Point[rx.length];
			int qi = 0, ri = 0;
			for (int i = 0; i < yy.length; i++ ) {
				if (yy[i].xListIndex > xIndexMax) {
					ry[ri] = yy[i];
					ri++;
				}
				else {
					qy[qi] = yy[i];
					qi++;
				}
			}
			
			Point[] qq = cpRec(qx, qy);
			Point[] rr = cpRec(rx, ry);
			double dq = distance(qq[0], qq[1]);
			double dr = distance(rr[0], rr[1]);
			double d;
			Point p1, p2;
			if (dq < dr) {
				d = dq;
				p1 = qq[0];
				p2 = qq[1];
			} else {
				d = dr;
				p1 = rr[0];
				p2 = rr[1];
			}

			double xMax = qx[qx.length - 1].x;
			ArrayList<Point> s = new ArrayList<Point>();

			for (int i = 0; i < yy.length; i++) {
				Point p = yy[yy.length - i - 1];
				if (Math.abs(xMax - p.x) <= d) {
					s.add(p);
				}
			}

			for (int i = 0; i < s.size() - 1; i++) {
				int iteRemain = s.size() - 1 - i;
				int k = 15;
				if (iteRemain < 15) {
					k = iteRemain;
				}

				Point pi = s.get(i);

				for (int j = i + 1; j <= k + i; j++) {
					Point pj = s.get(j);

					if (distance(pi, pj) < d) {
						d = distance(pi, pj);
						p1 = pi;
						p2 = pj;
					}

				}
			}
			pair[0] = p1;
			pair[1] = p2;
		}
		return pair;
	}

	private static double distance(Point a, Point b) {
		return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}

	private static class Point {
		private double x;
		private String name;
		private double y;
		private int xListIndex;
		private int yListIndex;

		private Point(String name, double x, double y) {
			this.x = x;
			this.y = y;
			this.name = name;
		}
	}

	private static class xComparator<T> implements Comparator<T> {
		public int compare(T o1, T o2) {
			if (!((o1 instanceof Point) && (o2 instanceof Point))) {
				throw new ClassCastException();
			}
			Point p1 = ((Point) o1);
			Point p2 = ((Point) o2);
			double d = p1.x - p2.x;
			if (d < 0) {
				return -1;
			}
			if (d > 0) {
				return 1;
			}
			return 0;
		}
	}

	private static class yComparator<T> implements Comparator<T> {
		public int compare(T o1, T o2) {
			if (!((o1 instanceof Point) && (o2 instanceof Point))) {
				throw new ClassCastException();
			}
			Point p1 = ((Point) o1);
			Point p2 = ((Point) o2);
			double d = p1.y - p2.y;
			if (d < 0) {
				return -1;
			}
			if (d > 0) {
				return 1;
			}
			return 0;
		}
	}
}
