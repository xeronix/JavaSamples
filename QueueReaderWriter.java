import java.util.Random;

/*
 * Single Reader-Writer Blocking Queue
 * Writer adds random integer to the queue
 * Reader reads from the queue
 */
public class QueueReaderWriter {
    private Queue queue;
    
    public static void startQueueReaderWriter() {
    	Queue queue = new Queue(10);
    	QueueReaderWriter ob = new QueueReaderWriter(queue);
    	ob.getReader().start();
    	ob.getWriter().start();
    }
    
	public QueueReaderWriter(Queue queue) {
		this.queue = queue;
	}
	
	public QueueReader getReader() {
		return new QueueReader(queue);
	}
	
	public QueueWriter getWriter() {
		return new QueueWriter(queue);
	}
	
	class QueueReader extends Thread {
		Queue queue;

		public QueueReader(Queue queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException e) {
							// handle exception }
						}
					}
					
					QueueNode node = queue.removeHead();
					System.out.println("Node Removed : " + node.getData());
					// process node

					queue.notify();
				}
			}
		}
	}

	class QueueWriter extends Thread {
		Queue queue;

		public QueueWriter(Queue queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (queue) {
					while (queue.isFull()) {
						try {
							queue.wait();
						} catch (InterruptedException e) {
							// handle exception }
						}
					}

					// we can have a method that generates queue node
					QueueNode node = new QueueNode(new Random().nextInt());
					queue.add(node);
					System.out.println("Node Added : " + node.getData());
					
					queue.notify();
				}
			}
		}
	}
}

class Queue {
	int size;
	int capacity;
	QueueNode head;
	QueueNode tail;
	
	public Queue(int capacity) {
		size = 0;
		this.capacity = capacity;
	}
	
	public QueueNode getHead() {
		return head;
	}
	
	public QueueNode getTail() {
		return tail;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public boolean isFull() {
		return size == capacity;
	}
	
	public QueueNode removeHead() {
		if (isEmpty()) {
			throw new RuntimeException("Queue is Empty.");
		}
		
		QueueNode oldHead = head;
		head = head.next;
		size--;
		return oldHead;
	}
	
	public void add(QueueNode object) {
		if (capacity == 0) {
			throw new RuntimeException("Capacity of queue is 0.");
		}
		
		if (size == capacity) {
			removeHead();
		}
		
		if (size == 0) {
			head = object;
			tail = object;
		} else {
			tail.next = object;
			tail = object;
		}
		
		size++;
	}
}

class QueueNode {
	int data;
	QueueNode next;
	
	public QueueNode(int data) {
		this.data = data;
	}
	
	public int getData() {
		return data;
	}
	
	public QueueNode getNext() {
		return next;
	}
}