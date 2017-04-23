package traffic.simulation.view;

import java.awt.Color;
import java.awt.Graphics;

import simulaltion.SimulationAgent;

/**
 * @author archehyun
 *
 */
public abstract class TrafficDraw  implements IFDrawObject{
	
	protected int x;
	protected int y;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public TrafficDraw(int x, int y)
	{
		this.x =x;
		this.y =y;
	}
	@Override
	public void draw(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(x, y, 50, 25);
		g.setColor(Color.black);
		g.drawRect(x, y, 50, 25);

	}
	@Override
	public void update(DrawBoard board) {
		// TODO Auto-generated method stub
		
	}

}
