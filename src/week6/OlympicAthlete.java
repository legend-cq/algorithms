package week6;

import java.util.HashMap;

public class OlympicAthlete {
	private String name;
	private int age;
	public OlympicAthlete(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public int hashCode() {
		return name.hashCode();
	}
	
	public boolean equals(OlympicAthlete that) {
		if(that == null) {
			return false;
		}
		if(!that.getClass().equals(this.getClass())) {
			return false;
		}
		OlympicAthlete athelete = (OlympicAthlete) that;
		return this.name.equalsIgnoreCase(athelete.name);
	}
	
	public static void main(String[] args) {
		HashMap<OlympicAthlete,Integer> map = new HashMap<>();
		map.put(new OlympicAthlete("Steven",30), 30);
		System.out.println(map.get(new OlympicAthlete("Steven",30)));
	}
}
