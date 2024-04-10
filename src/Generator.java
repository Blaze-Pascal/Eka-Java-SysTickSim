//////////////////////////////////////////////////////////////////////////////////////////////

import java.awt.AWTEventMulticaster;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Generator - Simulated generator for Systick simulation
 * 
 * This class simulates an oscillating signal given to systick
 * timer. It's used for stable timekeeping. Uses multithreading 
 * to enable signal generation while a window is active allowing
 * responisivity. 
 * 
 * Author: 263671
 * Date:  November 30, 2023
 * 
 * Usage:
 * - Create an instance of Generator using default constructor,
 * - Configure Generator writing to CDR Register,
 * - Use continuous or burst mode to generate ticks .
 */

//////////////////////////////////////////////////////////////////////////////////////////////

public class Generator extends Thread implements PulseSource{

//////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * CDR - Control and Delay Register
	 * 
	 * A shared register composed of control bits and delay value.
	 * Register composition:
	 * 	DDDD DDDD DDDD DDDD SSSS BMAE
	 * Where: D-delay, S-burst scale, B - burst end flag, M-mode, A-alive, E-enable
	 * 
	 */
	private Register CDR;	
	
	/**
	 * BVR - Burst Value Register
	 * 
	 * A register holding the amount of ticks the generator 
	 * performs in a single burst, times scale amount
	 */
	private Register BVR;
	
	//private boolean burstEnd;
	
	/**
	 * Constants to represent bit positions of CDR flags and used bit masks
	 */
	
	private static final int ENABLE = 0;
	private static final int ALIVE = 1;
	private static final int MODE = 2;
	private static final int BURST_END = 3;
	private static final int SCALE = 4;
	private static final int SCALE_MASK = 4;
	private static final int DELAY = 8;
	private static final int DELAY_MASK = 16;
	
	ActionListener al;
	
//////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructor of Generator object. Initializes registers with zeros,
	 * turns on the generator main loop and starts generator in new thread.
	 */
	public Generator() {
		CDR = new Register();
		BVR = new Register();
		CDR.resetBit(ENABLE);
		CDR.setBit(ALIVE);
		start();
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void addActionListener(ActionListener pl) {
		al=AWTEventMulticaster.add(al, pl);
	}
	@Override
	public void removeActionListener(ActionListener pl) {
		al=AWTEventMulticaster.add(al, pl);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Main tick generation function. 
	 * 
	 * Working principle:
	 * 
	 *	while alive, is running:
	 * 		if enabled, do tick:
	 * 			if continuous mode -> tick continuous,
	 * 			if burst -> tick for tick amount, than stop.
	 * 		if disabled, be idle.
	 * 
	 * 	if killed, stop thread.
	 */
	public void run() {
		
		int burstCount = 0;
		
		while(CDR.readBit(ALIVE)) {
			if(CDR.readBit(ENABLE)) {
				if(CDR.readBit(MODE)) {
					//System.out.println("CONTINOUS_MODE");
					doTicTac(DELAY, DELAY_MASK);
				}
				else if (!CDR.readBit(MODE)){
					if(burstCount < (BVR.readValue()*getBurstScale())) {
						//System.out.println("BURST_MODE");
						burstCount++;
						doTicTac(DELAY, DELAY_MASK);
						CDR.resetBit(BURST_END);
					}
					else {
						CDR.setBit(BURST_END);
						burstCount = 0;
						halt();
					}
				}
			}
			else {
				//System.out.println("IDLE");
				burstCount = 0;
				sleep(1);
			}
		}
	}
	
	private void doTicTac(int delay, int delayMask) {
		if(al != null) newActionPerformed(al, "tic");
		sleep(CDR.readBlockValue(delay, delayMask)/2);
		if(al != null) newActionPerformed(al, "tac");
		sleep(CDR.readBlockValue(delay, delayMask)/2);
	}

//////////////////////////////////////////////////////////////////////////////////////////////

	public void startGen() {
		CDR.setBit(ALIVE);
	}
	public void killGen() {
		CDR.resetBit(ALIVE);
	}
	@Override
	public void trigger() {
		CDR.setBit(ENABLE);
		
		if(al != null)
			newActionPerformed(al, "");
	}
	@Override
	public void halt() {
		CDR.resetBit(ENABLE);
		
		if(al != null)
			newActionPerformed(al, "");
	}

	@Override
 	public void setMode(byte mode) {
		if(mode == CONTINOUS_MODE) {
			CDR.setBit(MODE);
		}
		else if(mode == BURST_MODE){
			CDR.resetBit(MODE);
		}
		
		if(al != null)
			newActionPerformed(al, "");
	}
	@Override
	public void setPulseDelay(int ms) {
		CDR.writeBlockValue(DELAY, DELAY_MASK, ms);
		
		if(al != null)
			newActionPerformed(al, "");
	}
	public void setPulseFreq(float hz) {
		int ms = (int) (1000.0/hz);
		CDR.writeBlockValue(DELAY, DELAY_MASK, ms);
		
		if(al != null)
			newActionPerformed(al, "");
	}
	@Override
	public void setPulseCount(int burst) {
		BVR.writeValue(burst);
		
		if(al != null)
			newActionPerformed(al, "");
	}
	public void setBurstScale(int scale) {
		CDR.writeBlockValue(SCALE, SCALE_MASK, scale);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getCDR() {
		return CDR.readValue();
	}
	public int getBVR() {
		return BVR.readValue();
	}
	
	public byte getEnable() {
		return (CDR.readBit(ENABLE) ? (byte)1:(byte)0);
	}
	@Override
	public byte getMode() {
		return (CDR.readBit(MODE)? (byte)1:(byte)0);
	}

	@Override
	public int getPulseDelay() {
		return CDR.readBlockValue(DELAY, DELAY_MASK);
	}
	public float getPulseFreq() {
		return (float) (1000.0/CDR.readBlockValue(DELAY, DELAY_MASK));
	}
	public int getPulseCount() {
		return BVR.readValue();
	}
	public int getBurstScale() {
		return (CDR.readBlockValue(SCALE, SCALE_MASK));
	}
	
	public boolean getBurstEnd() {
		return CDR.readBit(BURST_END);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////

	private void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void newActionPerformed(ActionListener al, String txt) {
		al.actionPerformed(new ActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				txt));
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////

}
