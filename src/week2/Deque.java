package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{
	private class Node<Item> {
		private Item item;
		private Node<Item> previous;
		private Node<Item> next;
	}
	private Node<Item> first = null;
	private Node<Item> last = null;
	private int size = 0;
	public Deque() {
		
	}                          
	public boolean isEmpty() {
		return size == 0;
	}                
	public int size() {
		return size;
	}                       
	public void addFirst(Item item) {
		if(item == null) {
			throw new NullPointerException();
		}
		size++;
		Node<Item> oldFirst = first;
		Node<Item> newFirst = new Node<Item>();
		newFirst.item = item;
		newFirst.next = oldFirst;
		first = newFirst;
		if(oldFirst != null) {
			oldFirst.previous = newFirst;
		} else {
			last = newFirst;
		}
	}         
	public void addLast(Item item) {
		if(item == null) {
			throw new NullPointerException();
		}
		size++;
		Node<Item> oldLast = last;
		Node<Item> newLast = new Node<Item>();
		newLast.item = item;
		newLast.previous = oldLast;
		last = newLast;
		if(oldLast != null) {
			oldLast.next = newLast;
		} else {
			first = newLast;
		}
	}          
	public Item removeFirst() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		size--;
		Item item = first.item;
		first = first.next;
		if(first == null) {
			last = null;
		} else {
			first.previous = null;
		}
		return item;
	}               
	public Item removeLast() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		size--;
		Item item = last.item;
		last = last.previous;
		if(last == null) {
			first = null;
		} else {
			last.next = null;
		}
		return item;
	}                 
	
	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			private Node<Item> current = first;
			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public Item next() {
				if(current == null) {
					throw new NoSuchElementException();
				}
				Item item = current.item;
				current = current.next;
				return item;
			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<>();
		deque.addFirst(1);
		deque.addFirst(2);
		deque.addFirst(3);
		deque.removeFirst();
		deque.removeLast();
		deque.addLast(4);
		deque.addLast(5);
		deque.addLast(6);
		deque.addFirst(7);
		deque.removeLast();
		for (Integer integer : deque) {
			System.out.println(integer);
		}
	}
}
