package match;

import java.util.LinkedList;

import java.util.Scanner;
import java.util.Arrays;


public class MatchingApplication {

	public static void main(String[] args) {
		String line;
		Scanner sc = new Scanner(System.in);
		do {
			line = sc.nextLine();
		} while (line.startsWith("#"));
		int n = Integer.parseInt(line.substring(2));

		Person[] women = new Person[n];
		Person[] men = new Person[n];

		line = sc.nextLine();
		while (!line.isEmpty()) {
			Scanner scan = new Scanner(line);
			Person p = new Person(scan.nextInt() - 1, scan.next());
			scan.close();
			if (p.getId() % 2 == 1) {
				women[p.getId() / 2] = p;
			} else {
				men[p.getId() / 2] = p;
			}

			line = sc.nextLine();
		}
		for (int i = 0; i < 2 * n; i++) {
			line = sc.nextLine();
			int j = line.indexOf(':') + 1;
			String ints = line.substring(j);
			Scanner scan = new Scanner(ints);
			while (scan.hasNextInt()) {
				if (i % 2 == 0) {
					men[i / 2].offerSpouse(scan.nextInt() - 1);
				} else {
					women[i / 2].offerSpouse(scan.nextInt() - 1);
				}
			}
		}
		LinkedList<Person> menList = new LinkedList<Person>(Arrays.asList(men));
		Person[][] matches = match(menList, women, n);

		for (int i = 0; i < n; i++) {
			System.out.println(matches[i][0] + " -- " + matches[i][1]);
		}
		sc.close();
	}
	
	public static Person[][] match(LinkedList<Person> men, Person[] women, int n) {
		Person[][] matches = new Person[n][2];
		while (!men.isEmpty()) {
			Person man = men.poll();
			int id = man.getId();
			int prey = man.pollSpouse();
			int husband = women[prey/2].getSpouse();
			
			if (husband == -1) {
				man.setSpouse(prey/2);
				women[prey/2].setSpouse(id);
				matches[id/2][0] = man;
				matches[id/2][1] = women[prey/2];
			}
			
			else if (women[prey/2].getPriority(id) < women[prey/2].getPriority(husband)){
				man.setSpouse(prey);
				women[prey/2].setSpouse(id);
				matches[husband/2][0].setSpouse(-1);
				matches[id/2][0] = man;
				matches[id/2][1] = women[prey/2];
				men.offer(matches[husband/2][0]);
				
			}
			else {
				men.offer(man);
			}
		}
		
		
		return matches;
	}
}
