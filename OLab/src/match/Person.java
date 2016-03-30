package match;
import java.util.LinkedList;

public class Person {
	private int id,spouse;
	private String name;
	private LinkedList<Integer> priority;
	
	public Person (int id, String name) {
		this.id = id;
		this.name = name;
		spouse = -1;
		priority = new LinkedList<Integer>();
	}
	
	public int getId() {
		return id;
	}
	public String toString() {
		return name;
	}
	public boolean equals(Person p) {
		return p.id == id;
	}
	public int pollSpouse() {
		return priority.poll();
	}
	public void offerSpouse(int i) {
		priority.offer(i);
	}
	public void setSpouse(int i) {
		spouse = i;
	}
	public int getSpouse() {
		return spouse;
	}
	
	public int getPriority(int id) {
		return priority.indexOf(id);
	}
}
