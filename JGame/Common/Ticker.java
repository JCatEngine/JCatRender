package JGame.Common;

import java.util.ArrayList;

public class Ticker {
	
	public interface OnResponceListener {

		public void	onResponce();
	}



	/**
	 * loop thread
	 */
	private Thread loopThread;
	/**
	 * listeners
	 */
	private ArrayList<OnResponceListener> listeners=new ArrayList<>();
	/**
	 * callback delay
	 */
	private int delay;
	/**
	 * is this ticker running or stop?This two state can switch
	 */
	private boolean isRunning;
	/**
	 * is this ticker close? if so,this ticker can't be reuse.
	 * it always need to close ticker before set ticker to null
	 */
	private boolean close;

	
	
	
	
	/**
	 * 
	 * @param delay
	 * @param once
	 */
	public Ticker(int delay) {
		
		this.delay=delay;
	
		initLoopThread();
		
	}
	
	
	
	/**
	 * 进行循环
	 * @param frameRate
	 */
	public void start() {
	
		if(close)
		{
			throw new RuntimeException("close ticker can't be restart,please use a new ticker!");
		}
		isRunning=true;

	}

	/**
	 * 初始化世界的循环线程
	 */
	private void initLoopThread() {
		
		isRunning=true;
		close=false;
		
		this.loopThread=new Thread()
		{
			public void run() 
			{
				
				while(!close)
				{
				
					try {
						Thread.sleep(delay);
						if(isRunning)
						{
							
							listeners.forEach(l->l.onResponce());
						}
						
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};
		
		loopThread.start();
		
	
	}
	
	
	
	
	public void close()
	{
		close=true;
	}

	public void stop()
	{
		isRunning=false;
	}

	
	public void addListener(OnResponceListener listener) {
		listeners.add(listener);
	}

	public void removeListener(OnResponceListener listener) {
		listeners.remove(listener);
	}
	
}