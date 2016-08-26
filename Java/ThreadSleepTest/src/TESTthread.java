import java.io.IOException;


public class TESTthread implements Runnable{
	private WaitTimer w = new WaitTimer();
	private boolean run;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TESTthread b = new TESTthread();
		Thread t = new Thread(b);
		t.start();
		try {
			System.in.read();
			b.stops();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stops(){
		run=false;
		w.awake();
	}

	@Override
	public void run() {
		System.out.println("Startar");
		run=true;
		
			while(run){
				w.waitTimer(3000);
			}
		
		System.out.println("avslutad");
	}
}

class WaitTimer{
	private final Object lock = new Object();
	public synchronized void waitTimer(long milisec){
		try {
			synchronized(lock) {
				lock.wait(milisec);
			}
//			Thread.sleep(milisec);
		} catch (InterruptedException e) {
			System.out.println("Thread interupt");
		}
	}
	public void awake() {
		synchronized(lock) {
			lock.notifyAll();
		}
	}
}