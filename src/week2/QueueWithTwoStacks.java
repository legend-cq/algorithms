package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Stack;

public class QueueWithTwoStacks<T> implements Iterable<T>{
	private Stack<T> enQueue = new Stack<T>();
	private Stack<T> deQueue = new Stack<T>();
	
	public void enqueue(T t) {
		enQueue.push(t);
	}
	
	public T dequeue() {
		if(this.isEmpty()) {
			throw new NoSuchElementException();
		}
		if(!deQueue.isEmpty()) {
			return deQueue.pop();
		} else {
			while(!enQueue.isEmpty()) {
				deQueue.push(enQueue.pop());
			}
			return deQueue.pop();
		}
	}
	
	public T peek() {
		if(this.isEmpty()) {
			throw new NoSuchElementException();
		}
		if(!deQueue.isEmpty()) {
			return deQueue.peek();
		} else {
			while(!enQueue.isEmpty()) {
				deQueue.push(enQueue.pop());
			}
			return deQueue.peek();
		}
	}
	
	public int size() {
		return enQueue.size() + deQueue.size();
	}
	
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	public static void main(String[] args) {
		QueueWithTwoStacks<Integer> stack = new QueueWithTwoStacks<>();
		stack.enqueue(1);
		stack.enqueue(2);
		stack.enqueue(3);
		stack.enqueue(4);
		stack.enqueue(5);
		System.out.println(stack.dequeue());
		System.out.println(stack.dequeue());
		System.out.println(stack.dequeue());
		stack.enqueue(6);
		System.out.println(stack.dequeue());
		System.out.println(stack.dequeue());
		System.out.println(stack.dequeue());
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}

}
