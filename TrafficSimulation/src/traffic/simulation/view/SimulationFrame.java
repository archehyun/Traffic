package traffic.simulation.view;

import javax.swing.JFrame;

public class SimulationFrame extends JFrame{
	
	public SimulationFrame() {
		
		this.getContentPane().add(new DrawBoard());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,600);
		//this.setResizable(false);
		this.setVisible(true);
	}

}
