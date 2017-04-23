package traffic.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import simulaltion.SimulationMessage;
import traffic.simulation.view.DrawBoard;
import traffic.simulation.view.TrafficDraw;

public class TrafficLight extends TrafficObject{
	

	public static final int ON =1;
	public static final int OFF =0;

	public static final int VERTICAL =0;
	public static final int HORIZON =1;

	private int lightState[];

	Color[] lightColors ={Color.red, Color.yellow, Color.green};

	int direction;

	int width;
	int height;
	int x,y;
	
	int step;
	public TrafficLight(int x, int y, int direction) {
		initTrafficDraw(x, y);
		this.x = x;
		this.y = y;
		lightState = new int[3];
		lightState[0]=ON;
		lightState[1]=OFF;
		lightState[2]=OFF;
		this.direction = direction;
		if(direction==VERTICAL)
		{
			width=25;
			height=75;
		}
		else
		{
			width=75;
			height=25;
		}
		rn = new Random();
		step=1;


	}
	Random rn;
	@Override
	public void handleMessage(SimulationMessage message) {
		
		switch (step) {
		case 1:
			lightState[0]=ON;
			lightState[1]=OFF;
			lightState[2]=OFF;
			break;
		case 2:
			lightState[0]=OFF;
			lightState[1]=ON;
			lightState[2]=OFF;
			break;
		case 3:
			lightState[0]=OFF;
			lightState[1]=OFF;
			lightState[2]=ON;
			break;	

		default:
			break;
		}
		step+=1;
		if(step==4)
		{
			step=1;
		}

	}




	class TrafficLightDraw extends TrafficDraw
	{
		public TrafficLightDraw(int x, int y) {
			super(x, y);
			// TODO Auto-generated constructor stub
		}
		private void drawLight(Graphics g,Color color, int x, int y, int onOff)
		{
			g.setColor(color);

			switch (onOff) {
			case ON:
				g.fillOval(x, y, 25, 25);
				break;
			case OFF:
				g.drawOval(x, y, 25, 25);
				break;

			default:
				break;
			}

		}
		@Override
		public void draw(Graphics g) {


			g.setColor(Color.gray.brighter());
			g.fillRect(this.getX(), this.getY(), width, height);


			if(direction==HORIZON)
			{
				for(int i=0;i<lightState.length;i++)
				{
					this.drawLight(g, lightColors[i], this.getX()+i*25, this.getY(),lightState[i]);
				}
			}else
			{
				for(int i=0;i<lightState.length;i++)
				{
					this.drawLight(g, lightColors[i], this.getX(), this.getY()+i*25,lightState[i]);
				}
			}
			
			g.setColor(Color.BLACK);
			g.drawRect(this.getX(), this.getY(), width, height);

		}

		@Override
		public void update(DrawBoard board) {

		}
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initTrafficDraw(int x, int y) {
		this.trafficDraw = new TrafficLightDraw(x, y);
		
	}

}
