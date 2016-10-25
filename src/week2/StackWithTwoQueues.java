package week2;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Queue;

public class StackWithTwoQueues<T> {
	
	private Queue<T> queueA = new Queue<T>();
	private Queue<T> queueB = new Queue<T>();
	
	public void push(T t) {
		if(!queueB.isEmpty()) {
			queueB.enqueue(t);
		} else {
			queueA.enqueue(t);
		}
	}
	
	public T pop() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		if(!queueA.isEmpty()) {
			while(queueA.size() > 1) {
				queueB.enqueue(queueA.dequeue());
			}
			return queueA.dequeue();
		} else {
			while(queueB.size() > 1) {
				queueA.enqueue(queueB.dequeue());
			}
			return queueB.dequeue();
		}
	}
	
	public int size() {
		return queueA.size() + queueB.size();
	}
	
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	public static void main(String[] args) {
		StackWithTwoQueues<Integer> stack = new StackWithTwoQueues<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		System.out.println(stack.pop());
		stack.push(4);
		stack.push(5);
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
	}

}
