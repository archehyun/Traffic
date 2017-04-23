package traffic.model;

import simulaltion.SimulationAgent;
import traffic.simulation.view.TrafficDraw;

public abstract class TrafficObject extends SimulationAgent{
	protected TrafficDraw trafficDraw;

	public TrafficDraw getTrafficDraw() {
		return trafficDraw;
	}
	
	public abstract void initTrafficDraw(int x, int y);

}
