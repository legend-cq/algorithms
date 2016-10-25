package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Object[] items = new Object[10];
	private int size = 0;
	
	public RandomizedQueue() {
		
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public int size() {
		return size;
	}
	public void enqueue(Item item) {
		if(item == null) {
			throw new NullPointerException();
		}
		if(items.length == size) {
			adjustArray(size*2);
		}
		int random = StdRandom.uniform(size+1);
		items[size] = items[random];
		items[random] = item;
		size++;
	}
	
	public Item dequeue() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		if(items.length == size * 4) {
			adjustArray(items.length/2);
		}
		Item item = (Item) items[size-1];
		items[size-1] = null;
		size--;
		return item;
	}
	public Item sample() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		int random = StdRandom.uniform(size);
		return (Item) items[random];
	}
	
	private void adjustArray(int newSize) {
		Object[] newArr = new Object[newSize];
		int length = newSize > size?size:newSize;
		for(int i = 0;i < length;i++) {
			newArr[i] = items[i];
		}
		items = newArr;
	}

	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			private Object[] objArr = shuffle(items);
			private int current = 0;
			public boolean hasNext() {
				return size != 0 && current != size;
			}
			
			private Object[] shuffle(Object[] items) {
				Object[] newArr = new Object[size];
				for(int i = 0; i < size;i++) {
					int random = StdRandom.uniform(i+1);
					newArr[i] = newArr[random];
					newArr[random] = items[i];
				}
				return newArr;
			}

			public Item next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				current++;
				return (Item) objArr[current-1];
			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> queue = new RandomizedQueue<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		System.out.println("dequeue:" + queue.dequeue());
		queue.enqueue(5);
		queue.enqueue(6);
		queue.enqueue(7);
		queue.enqueue(8);
		System.out.println("dequeue:" + queue.dequeue());
		queue.enqueue(9);
		queue.enqueue(10);
		for (Integer integer : queue) {
			System.out.println(integer);
		}
		for (Integer integer : queue) {
			System.out.println(integer);
		}
	}

}
