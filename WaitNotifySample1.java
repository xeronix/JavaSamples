import java.util.ArrayList;
import java.util.List;

class LockObject {
	int index;
	
	public LockObject() {
		index = 0;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void incrementIndex() {
		index++;
	}
}

/**
 * Synchronizing two threads to print first 20 elements of Fibonacci series
 * Thread 1 prints elements in odd places
 * Thread 2 prints elements in even places
 * @author vmehta
 *
 */
class Print {
	List<Integer> array;
	LockObject lock;

	Print(List<Integer> array, LockObject lock) {
		this.array = new ArrayList<>();
		this.array = array;
		this.lock = lock;
	}

	public void printOdd() {
		while (lock.getIndex() < 20) {
			synchronized (lock) {
				if (lock.getIndex() % 2 == 1) {
					System.out.println(array.get(lock.getIndex()));
					lock.incrementIndex();
					lock.notify();
				} else {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void printEven() {
		while (lock.getIndex() < 20) {
			synchronized (lock) {
				if (lock.getIndex() % 2 == 0) {
					System.out.println(array.get(lock.getIndex()));
					lock.incrementIndex();
					lock.notify();
				} else {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}

public class WaitNotifySample1 {
	public static void main(String args[]) throws InterruptedException {
		List<Integer> fibArray = new ArrayList<Integer>();
		fibArray.add(0);
		fibArray.add(1);

		for (int i = 2; i < 20; ++i) {
			int fib = fibArray.get(i - 2) + fibArray.get(i - 1);
			fibArray.add(fib);
		}

		LockObject lock = new LockObject();

		final Print print = new Print(fibArray, lock);
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				print.printEven();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				print.printOdd();
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
	}
}
