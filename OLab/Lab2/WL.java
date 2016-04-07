import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.io.File;

public class WL {

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream(new File("words-5757.txt"));
			HashMap<String, LinkedList<Word>> graph = new HashMap<String, LinkedList<Word>>();
			LinkedList<Integer> dist = new LinkedList<Integer>();
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = br.readLine();
			while (line !=null) {
				buildGraph(graph, line);
				line = br.readLine();
			}
			br.close();
			fis = new FileInputStream(new File("words-5757-in.txt"));
			br = new BufferedReader(new InputStreamReader(fis));
			line = br.readLine();
			while (line != null) {
				String[] words = line.split(" ");
				dist.offer(getDistance(words[0], words[1], graph));
				line = br.readLine();
			}
			br.close();
			for (Integer distance : dist) {
				System.out.println(distance);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void buildGraph(HashMap<String, LinkedList<Word>> map, String line) throws Exception {
		Word w = new Word(line);
		// w points to old words
		char[] charFor1 = w.srt.toCharArray();
		String strFor1;
		for (char i = 'a'; i <= 'z'; i++) {
			charFor1[0] = i;
			strFor1 = new String(charFor1);
			if (map.containsKey(strFor1)) {
				for (Word k : map.get(strFor1)) {
					w.conTo.add(k);
				}
			}
			int index = strFor1.substring(1).indexOf(i);
			if (index != -1) {
				char[] charFor2 = new char[5];
				for (char j = 'a'; j <= 'z'; j++) {
					System.arraycopy(charFor1, 0, charFor2, 0, 5);
					charFor2[index + 1] = j;
					Arrays.sort(charFor2, 1, 5);
					String strFor2 = new String(charFor2);
					if (map.containsKey(strFor2)) {
						for (Word k : map.get(strFor2)) {
							w.conTo.add(k);
						}
					}
				}
			}
		}
		// Old words Pointing to w
		charFor1 = w.srt.toCharArray();
		for (char i = 'a'; i <= 'z'; i++) { // Old words pointing to
											// the new one
			charFor1[0] = i;
			strFor1 = new String(charFor1);
			if (map.containsKey(strFor1)) { // words having the same
											// last 4 letters
				for (Word k : map.get(strFor1)) {
					k.conTo.add(w);
				}
			}
			for (int j = 1; j < 5; j++) { // words where the 4 last
											// chars contain the
											// first char + 3 others
				char[] charFor2 = new char[5];
				System.arraycopy(charFor1, 0, charFor2, 0, 5);
				if (!((j > 1) && (charFor1[j] == charFor1[j - 1]))) {
					charFor2[j] = w.srt.charAt(0);
					Arrays.sort(charFor2, 1, 5);
					String strFor2 = new String(charFor2);
					if (map.containsKey(strFor2)) {
						for (Word k : map.get(strFor2)) {
							k.conTo.add(w);
						}
					}
				}
			}
		}
		if (!map.containsKey(w.srt)) {
			LinkedList<Word> list = new LinkedList<Word>();
			list.add(w);
			map.put(w.srt, list);
		} else {
			map.get(w.srt).add(w);
		}
	}

	private static int getDistance(String start, String fin, HashMap<String, LinkedList<Word>> graph) {
		
		if(start.equals(fin)) {
			return 0;
		}
		for (LinkedList<Word> lists : graph.values()) {
			for (Word word : lists) {
				for (Word n : word.conTo) {
					n.distance = -1;
					n.parent = null;
				}
			}
		}
		Word w1 = new Word(start);
		LinkedList<Word> queue = new LinkedList<Word>();
		LinkedList<Word> list = graph.get(w1.srt);

		int ind = list.indexOf(w1);
		Word root = list.get(ind);
		root.distance = 0;
		queue.offer(root);
		while (!queue.isEmpty()) {
			Word current = queue.poll();
			for (Word n : current.conTo) {
				if (n.distance == -1) {
					n.distance = current.distance + 1;
					n.parent = current;
					queue.offer(n);
					if (n.w.equals(fin)) {
						return n.distance;
					}
				}
			}
		}

		return -1;

	}

	private static class Word {
		String w;
		String srt;
		LinkedList<Word> conTo;
		Word parent;
		int distance;

		private Word(String w) {
			this.w = w;
			char[] ch = w.toCharArray();
			Arrays.sort(ch, 1, 5);
			this.srt = new String(ch);
			conTo = new LinkedList<Word>();
		}

		public boolean equals(Object o) {
			if (!(o instanceof Word)) {
				return false;
			} else {
				Word w1 = ((Word) o);
				return w.equals(w1.w);
			}
		}
	}
}
