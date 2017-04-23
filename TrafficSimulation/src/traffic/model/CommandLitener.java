package traffic.model;

import simulaltion.SimulationMessage;

public class CommandLitener implements Runnable{
	private CommandQueue msgQueue;
	
	private TrafficCar car;

	
	Thread thread;
	public void start()
	{
		if(thread == null)
			thread = new Thread(this);
		thread.start();
	}
	public CommandLitener(TrafficCar car) {
		this.car = car;
		msgQueue = new CommandQueue();
		System.out.println("command litener init");
	}

	
	public synchronized void append(SimulationMessage message)
	{
		msgQueue.append(message);
	}
	public synchronized SimulationMessage poll()
	{
		return (SimulationMessage) msgQueue.poll();
	}
	@Override
	public void run() {
		System.out.println("command queue start");

		while(true)
		{
			SimulationMessage command = (SimulationMessage) msgQueue.poll();
			
		}
	}
}
