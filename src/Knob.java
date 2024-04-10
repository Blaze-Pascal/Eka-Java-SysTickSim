//////////////////////////////////////////////////////////////////////////////////////////////

import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Knob - Graphic control component for Window applications
 * 
 *  This class is a simulated knob based on a potentiometer based 
 *  control element. Provides a way to change variables based 
 *  on knob angle. Has a ring indicator suitable for showing 
 *  accurate knob angle.
 *  
 *  Author: 263671
 *  Date: December 21, 2023
 *  
 *  Usage:
 *  - Create an object using default constructor,
 *  - Add an action listener to control panel,
 *  - Convert knob angle to regulated value.
 */

//////////////////////////////////////////////////////////////////////////////////////////////

public class Knob extends JComponent implements MouseMotionListener{

//////////////////////////////////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1L;
	
	//Mouse position
	private int xKlik;
	private int yKlik;
	
	//Initial knob angle
	private double knobAngle = -Math.PI+Math.PI/16;
	//Max/Min knob angle
	private final double MIN_KAT = -Math.PI+Math.PI/16;
	private final double MAX_KAT = Math.PI-Math.PI/16;
	
	//Default Window button colors
	private final Color LIGHT_BLUE = new Color(0xB8CFE5);
	private final Color BLUE_GRAY = new Color (0x7A8A99);
	
	private ActionListener al;
	
//////////////////////////////////////////////////////////////////////////////////////////////

	public Knob() {
		addMouseMotionListener(this);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////

	public void addActionListener(ActionListener pl) {
		al=AWTEventMulticaster.add(al, pl);
	}
	public void removeActionListener(ActionListener pl) {
		al=AWTEventMulticaster.add(al, pl);
	}
	
//////////////////////////////////////////////////////////////

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Enable for debugging
//		System.out.println(this.getAngle());
		
		// Add antialiasing 
		Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
        // Configure diameter of indicator ring
        int ringDiameter = Math.min(getWidth(), getHeight())/13;
		
        // Calculate dimensions of knob circle
		int r=Math.min(getWidth(), getHeight())/2 - ringDiameter;
		int x=getWidth()/2;	
		int y=getHeight()/2;
		
		// Calculate dimensions of indicator circle
		int rm=r/5;
		int xm=x+(int)(Math.cos(knobAngle)*0.75*r);
		int ym=y+(int)(Math.sin(knobAngle)*0.75*r);
		
		// Paint white indicator ring with trim around knob circle
        g.setColor(Color.WHITE);
        g.fillOval(x - r - ringDiameter, y - r - ringDiameter, (r + ringDiameter) * 2,
                (r + ringDiameter) * 2);
        
        g.setColor(BLUE_GRAY);
        g.drawOval(x - r - ringDiameter, y - r - ringDiameter, (r + ringDiameter) * 2,
                (r + ringDiameter) * 2);
		
		//Paint arc fill indicator
		g.setColor(Color.ORANGE);
        int indicatorAngle = (int) Math.toDegrees(knobAngle + Math.PI-Math.PI/16);
        g.fillArc(x - r - ringDiameter, y - r - ringDiameter, (r + ringDiameter) * 2,
                (r + ringDiameter) * 2, (int) Math.toDegrees(MAX_KAT), -indicatorAngle);
        
        //Paint indicator division markings
        g.setColor(Color.BLACK);
        int lineCount = 31;						//Configure to suit requirements
        for (int i = 0; i < lineCount; i++) {
        	double angle = i  * (2 * Math.PI / lineCount) ;
            int lineX = x + (int) (Math.cos(angle) * (r + ringDiameter));
            int lineY = y + (int) (Math.sin(angle) * (r + ringDiameter));
        	g.drawLine(lineX, lineY, x, y);
        }
                
		g.setColor(BLUE_GRAY);
        g.fillArc(x - r - ringDiameter, y - r - ringDiameter, (r + ringDiameter) * 2,
                (r + ringDiameter) * 2, (int) Math.toDegrees(MAX_KAT), (int) Math.toDegrees(Math.PI/8));
        
        //Paint blue knob circle with trim
        g.setColor(LIGHT_BLUE);
		g.fillOval(x-r, y-r, r*2, r*2);
		
		g.setColor(BLUE_GRAY);
		g.drawOval(x-r, y-r, r*2, r*2);
		
		//Paint small indicator circle with trim	    
		g.setColor(Color.WHITE);
		g.fillOval(xm-rm, ym-rm, rm*2, rm*2);
		
		g.setColor(BLUE_GRAY);
		g.drawOval(xm-rm, ym-rm, rm*2, rm*2);

	}
	
//////////////////////////////////////////////////////////////
	
	/**
	 * Getter for raw knob angle,
	 * 
	 * @return knobAngle in range form -Pi to Pi
	 */
	public double getAngle() {
		return (knobAngle);
	}
	/**
	 * Getter for adjusted knob angle
	 * 
	 * @return knobAngle in range from 0 to 2*Pi
	 */
	public double getAngleAdj() {
		return (knobAngle + Math.PI-Math.PI/16);
	}
	
	
	@Override
	/**
	 * Event handler for mouseDragged event
	 * 
	 * Calculates new knob angle based on mouse position,
	 * Restricts angle from MIN to MAX (adjustable).
	 */
	public void mouseDragged(MouseEvent e) {
		xKlik=e.getX();
		yKlik=e.getY();
		
        // Calculate angle based on mouse position
        knobAngle = Math.atan2((yKlik - getHeight() / 2), (xKlik - getWidth() / 2));

        // Restrict angle
        if (knobAngle < MIN_KAT) {
            knobAngle = MIN_KAT;
        } else if (knobAngle > MAX_KAT) {
            knobAngle = MAX_KAT;
        }
        
		if(al != null)al.actionPerformed(new ActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				""));
		
		repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Paints knob component on its own for easy debugging 
	 * outside of main window application.
	 * 
	 * Enable angle readout in {@link #paintComponent(Graphics g)}
	 * for easier debugging.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			JFrame okno=new JFrame();
			
			Knob k=new Knob();
			
			okno.add(k);
			
			okno.setSize(600, 400);
			okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			okno.setVisible(true);
		});
	}
}
