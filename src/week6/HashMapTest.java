package week6;

import java.util.HashMap;

public class HashMapTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
//		int a = HashMapTest.tableSizeFor(100);
//		System.out.println(a);
//		String s = "test";
//		System.out.println(Integer.toBinaryString(s.hashCode()));
//		System.out.println(Integer.toBinaryString(s.hashCode() >>> 16));
//		System.out.println(hash("test"));
		HashMap map = new HashMap();
		for(int i = 'a';i<='z';i++) {
			map.put((char)i, i);
		}
		System.out.println(map.get('h'));
	}
	
	public static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n + 1;
    }
	
	public static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

}
