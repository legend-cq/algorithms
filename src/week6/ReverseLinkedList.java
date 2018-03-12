package week6;

import java.util.Iterator;
import java.util.LinkedList;

public class ReverseLinkedList {
	public static <E> LinkedList<E> reverse(LinkedList<E> list) {
		LinkedList<E> newList = new LinkedList<E>();
		Iterator<E> descendingIterator = list.descendingIterator();
		while(descendingIterator.hasNext()) {
			newList.add(descendingIterator.next());
		}
		return newList;
	}
	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		list.add("c");
		list.add("a");
		list.add("b");
		list.add("d");
		LinkedList<String> reverse = ReverseLinkedList.reverse(list);
		for (String string : reverse) {
			System.out.println(string);
		}
	}
	
	 // Return reference of new head of the reverse linked list 
	 class Node {
	     int value;
	    Node next;
	    Node(int value) {
	        this.value = value;
	    }
	} 
   // This function should reverse linked list and return
   // head of the modified linked list.
   Node reverse(Node head)
   {
        // add code here
        Node pre = null;
        Node cur = head;
        while(cur != null) {
            Node next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
   }
}
