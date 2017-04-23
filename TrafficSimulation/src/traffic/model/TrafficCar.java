package traffic.model;

import java.awt.Point;
import java.util.Random;

import simulaltion.SimulationMessage;
import traffic.simulation.view.DrawBoard;
import traffic.simulation.view.TrafficDraw;

public class TrafficCar extends TrafficObject implements Runnable{
	
	
	private Point destination;
	
	public Point getDestination() {
		return destination;
	}

	public void setDestination(Point destination) {
		this.destination = destination;
	}

	private int speed=100;
	
	private int x,y;

	public final int MOVE=1;

	public final int MOVE_RIGHT=1;

	public final int MOVE_LEFT=2;

	public final int MOVE_DOWN=3;

	public final int MOVE_UP=4;

	public final int STOP=5;

	private int state=MOVE_RIGHT;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	private int initX;
	private int initY;
	
	CommandLitener commandListenr;

	Thread commnadThread;
	public TrafficCar(int x, int y) {
		
		initTrafficDraw(x, y);
		
		this.x = x;
		this.y = y;

		this.initX=x;
		this.initY=y;
		
		commandListenr = new CommandLitener(this);
		commnadThread = new Thread(this);
		commnadThread.start();
		
		
		//commandListenr.start();
		
	}
	public void speedUP()
	{
		speed++;
	}
	public void speedDown()
	{
		if(speed>0)
			speed--;
		
	}


	@Override
	public void handleMessage(SimulationMessage message) {

		System.out.println("notify "+message);

		if(message!=null)
		{
			commandListenr.append(message);
		}
		switch (state) {
		case STOP:

			break;
		case MOVE:

			break;	

		default:
			break;
		}
	}



	@Override
	public void execute() {


	}
	Random rn = new Random();


	public boolean isCollision(TrafficCar car)
	{

		return true;
	}

	private void moveUP()
	{
		y-=1;
		trafficDraw.setY(y);
	}
	private void moveDown()
	{
		y+=1;
		trafficDraw.setY(y);
	}
	private void moveRight()
	{
		x+=1;
		trafficDraw.setX(x);
	}
	private void moveLeft()
	{
		x-=1;
		trafficDraw.setX(x);
	}


	@Override
	public void run() {
		
		while(true)
		{
			System.out.println(commandListenr);
			
			SimulationMessage message = commandListenr.poll();
		}
		

	/*	while(true){
			switch (state) {
			case STOP:
				break;
			case MOVE_RIGHT:
				moveRight();
				break;	
			case MOVE_LEFT:
				moveLeft();
				break;
			case MOVE_UP:
				moveUP();
				break;
			case MOVE_DOWN:
				moveDown();
				break;				
			default:
				break;
			}
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
	class TrafficCarDraw extends TrafficDraw
	{

		public TrafficCarDraw(int x, int y) {
			super(x, y);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void update(DrawBoard board) {

			// Á÷Áø
			if(rn.nextInt()>0.5)
			{
				state = MOVE_RIGHT;
			}
			else

			{
				state = MOVE_LEFT;
			}
			if(rn.nextInt()>0.5)
			{
				state = MOVE_DOWN;
			}
			else
			{
				state = MOVE_UP;
			}
			if(x>board.getWidth())
			{
				x = initX;
			}
		}
		
	}

	@Override
	public void initTrafficDraw(int x, int y) {
		trafficDraw = new TrafficCarDraw(x, y);
		
	}

}




