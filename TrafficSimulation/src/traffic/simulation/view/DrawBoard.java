package traffic.simulation.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComponent;

import traffic.model.TrafficCar;
import traffic.model.TrafficControler;
import traffic.model.TrafficLight;

public class DrawBoard extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TrafficControler controler;
	
	Thread thead;
	
	TrafficLight light1 = new TrafficLight(300,100, TrafficLight.HORIZON);
	TrafficLight light2 = new TrafficLight(200,400,TrafficLight.HORIZON);
	TrafficLight light3 = new TrafficLight(200,200,TrafficLight.VERTICAL);
	TrafficLight light4 = new TrafficLight(400,300,TrafficLight.VERTICAL);
	
	TrafficCar car1 = new TrafficCar(0,305);
	
	TrafficCar car2 = new TrafficCar(0,360);
	
	TrafficCar car3 = new TrafficCar(-45,305);
	
	TrafficCar car4 = new TrafficCar(-45,360);
	
	LinkedList<IFDrawObject> drawList;
	
	public DrawBoard() {
		controler = TrafficControler.getInstance();
		
		
		drawList = new LinkedList<>();
		
		controler.addSimulationAgent(light1);
		controler.addSimulationAgent(light2);
		controler.addSimulationAgent(light3);
		controler.addSimulationAgent(light4);
		
		controler.addSimulationAgent(car1);
		controler.addSimulationAgent(car2);
		controler.addSimulationAgent(car3);
		controler.addSimulationAgent(car4);
		
		
		drawList.add(light1.getTrafficDraw());
		drawList.add(light2.getTrafficDraw());
		drawList.add(light3.getTrafficDraw());
		drawList.add(light4.getTrafficDraw());
		
		drawList.add(car1.getTrafficDraw());		
		drawList.add(car2.getTrafficDraw());
		drawList.add(car3.getTrafficDraw());
		drawList.add(car4.getTrafficDraw());
		thead = new Thread(this);
		thead.start();
	}
	
	@Override
	public void run() {
		while(true)
		{	
			update();
			repaint();

			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void update()
	{
		Iterator<IFDrawObject> iter = drawList.iterator();
		while(iter.hasNext())
		{
			iter.next().update(this);
		}
	}
	
	private int roadWidth =250;
	private int line =10;
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		// µµ·Î
		g.setColor(Color.gray.brighter());
		g.fillRect(0, this.getHeight()/2-roadWidth/2, this.getWidth(), roadWidth);		
		g.fillRect(this.getWidth()/2-roadWidth/2, 0, roadWidth, this.getHeight());
		
		// Áß¾Ó¼±
		g.setColor(Color.orange);
		g.fillRect(0, this.getHeight()/2-line/2, this.getWidth(), line);		
		g.fillRect(this.getWidth()/2-line/2, 0, line, this.getHeight());
		
		g.setColor(Color.white);
		int whiteLineY = 0;
		int whiteLineX1 = this.getWidth()/2-roadWidth/4-2;
		int whiteLineX2 = this.getWidth()/2+roadWidth/4+2;
		while(whiteLineY<this.getHeight())
		{
			g.fillRect(whiteLineX1, whiteLineY, 4, 15);
			g.fillRect(whiteLineX2, whiteLineY, 4, 15);
			whiteLineY+=25;
		}
		
		int whiteLineX = 0;
		int whiteLineY1 = this.getHeight()/2-roadWidth/4-2;
		int whiteLineY2 = this.getHeight()/2+roadWidth/4+2;
		while(whiteLineX<this.getWidth())
		{
			g.fillRect(whiteLineX, whiteLineY1, 15, 4);
			g.fillRect(whiteLineX, whiteLineY2, 15, 4);
			whiteLineX+=25;
		}
		
		g.setColor(Color.gray.brighter());
		g.fillRect(this.getWidth()/2-roadWidth/2, this.getHeight()/2-roadWidth/2, roadWidth, roadWidth);
				
		// È¾´Üº¸µµ		
		g.setColor(Color.white);
		g.fillRect(this.getWidth()/2-roadWidth/2, this.getHeight()/2-roadWidth/2 -5, roadWidth, 5);		
		g.fillRect(this.getWidth()/2-roadWidth/2, this.getHeight()/2-roadWidth/2 -40, roadWidth, 5);
		
		g.fillRect(this.getWidth()/2-roadWidth/2-40, this.getHeight()/2-roadWidth/2 , 5, roadWidth);		
		g.fillRect(this.getWidth()/2-roadWidth/2-5, this.getHeight()/2-roadWidth/2 , 5, roadWidth);
		
		g.fillRect(this.getWidth()/2-roadWidth/2, this.getHeight()/2+roadWidth/2 , roadWidth, 5);		
		g.fillRect(this.getWidth()/2-roadWidth/2, this.getHeight()/2+roadWidth/2+40 , roadWidth, 5);
		
		g.fillRect(this.getWidth()/2+roadWidth/2+40, this.getHeight()/2-roadWidth/2 , 5, roadWidth);		
		g.fillRect(this.getWidth()/2+roadWidth/2, this.getHeight()/2-roadWidth/2 , 5, roadWidth);
				
		Iterator<IFDrawObject> iter = drawList.iterator();
		
		while(iter.hasNext())
		{
			iter.next().draw(g);
		}
	}

}
