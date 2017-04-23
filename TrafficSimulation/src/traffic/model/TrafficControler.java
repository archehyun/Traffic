package traffic.model;

import java.util.Iterator;

import simulaltion.IFSimulationAgent;
import simulaltion.SimulationControl;
import simulaltion.SimulationMessage;

public class TrafficControler extends SimulationControl implements Runnable{


	private static final int delay = 1000;

	private static TrafficControler instance;	

	public static TrafficControler getInstance()
	{
		if(instance == null)
			instance = new TrafficControler();
		return instance;
	}

	Thread thread;
	
	private TrafficControler() {
		thread = new Thread(this);
		thread.start();
	}


	public boolean isMove(TrafficCar car)
	{
		for(int i=0;i<list.size();i++)
		{
			return false;
		}
		return true;
	}

	@Override
	public void run() {
		while(true)
		{
			try {
				System.out.println("contorl notify");
				notifyMessage();
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	private void notifyMessage()
	{
		Iterator<IFSimulationAgent> iter = list.iterator();
		while(iter.hasNext())
		{
			IFSimulationAgent item = iter.next();
			SimulationMessage message = new SimulationMessage();
			message.setDeparture("B");
			message.setArrival("A");
			item.handleMessage(message);			
		}
	}

}
