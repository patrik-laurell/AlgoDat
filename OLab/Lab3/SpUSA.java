import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.HashMap;

public class SpUSA {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = br.readLine();
			HashMap<String, Node> map = new HashMap<String, Node>();
			Node n;
			PriorityQueue<Arc<Node>> arcs = new PriorityQueue<Arc<Node>>();
			while(!line.contains("--")) {
				if (line.endsWith(" ")) {
					line = line.substring(0, line.length() - 1);
				}
				n = new Node(line);
				map.put(line, n);
				line = br.readLine();
			}
			while (line != null) {
			//while(!line.startsWith("!")) {
				//String[] types = line.split(" ");
				//String[] names = types[0].split("--");
				int start = line.indexOf('[') + 1;
				int fin = line.lastIndexOf("]");
				int dist = Integer.parseInt(line.substring(start, fin));
				String names = line.substring(0, line.indexOf('[') - 1);
				String[] nameArray = names.split("--");
				Node n1 = map.get(nameArray[0]);
				Node n2 = map.get(nameArray[1]);
				Arc<Node> arc = new Arc<Node>(n1, n2, dist);
				arcs.add(arc);
				line = br.readLine();
			}
			MST mst = new MST(map, arcs);
			System.out.println(mst.cumWeight);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	private static class MST {
		private int cumWeight;
		private PriorityQueue<Arc<Node>> arcs;
		private HashMap<String, Node> map;

		private MST(HashMap<String, Node> map, PriorityQueue<Arc<Node>> arcs) {
			this.arcs = arcs;
			this.map = map;
			cumWeight = 0;
			Kruskal();
		}

		private void Kruskal() {
			for (Node n : map.values()) {
				n.parent = n;
			}
			while (!arcs.isEmpty()) {
				Arc<Node> a = arcs.poll();
				if (union(a.n1, a.n2)) {
					cumWeight = cumWeight + a.dist;
				}
			}
		}

		private boolean union(Node n1, Node n2) {
			Node n1Root = find(n1);
			Node n2Root = find(n2);
			if (n1Root == n2Root) {
				return false;
			}

			if (n1Root.rank < n2Root.rank) {
				n1Root.parent = n2Root;
			} else if (n1Root.rank > n2Root.rank) {
				n2Root.parent = n1Root;
			} else {
				n2Root.parent = n1Root;
				n1Root.rank = n1Root.rank + 1;
			}
			return true;
		}

		private Node find(Node n) {
			if (n != n.parent) {
				n.parent = find(n.parent);
			}
			return n.parent;
		}

	}

	private static class Node {
		private String name;
		private Node parent;
		private int rank;

		private Node(String name) {
			this.name = name;
			rank = 0;
		}
	}

	private static class Arc<T> implements Comparable<T> {
		private int dist;
		private T n1;
		private T n2;

		private Arc(T n1, T n2, int dist) {
			this.dist = dist;
			this.n1 = n1;
			this.n2 = n2;
		}

		@Override
		public int compareTo(T o) {
			if (o instanceof Arc) {
				Arc<T> arc2 = ((Arc<T>) o);
				return dist - arc2.dist;
			} else if (o == null) {
				throw new NullPointerException();
			} else {
				throw new ClassCastException();
			}
		}
	}
}
