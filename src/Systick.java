//////////////////////////////////////////////////////////////////////////////////////////////

import java.awt.AWTEventMulticaster;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Systick - Simulated SysTick Timer for Cortex Processor Family.
 *
 * This class represents a simulated SysTick timer, providing 
 * methods to manipulate the timer's registers and simulate 
 * clock ticks. It is designed to mimic the behavior
 * of the SysTick timer in the Cortex processor family.
 *
 * Author: 263671
 * Date: November 11, 2023
 *
 * Usage:
 * - Create an instance of Systick using the default constructor.
 * - Use the provided methods to configure and control the timer.
 * - Simulate clock ticks using tickInternal() or tickExternal().
 */

//////////////////////////////////////////////////////////////////////////////////////////////

public class Systick implements Cortex_M0_SysTick_Interface{

//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Example program utilizing Systick interrupt feature to measure 
	 * time taken to execute a full countdown a hundred times
	 * 
	 * Features:
	 * - Systick configuration,
	 * - Usage of interrupt to perform an action
	 * - Live execution time readout
	 */
	
	public static void main(String[] args) throws InterruptedException {			
		
		// Create a new instance of Systick
		Systick cortexM01 = new Systick();
        
		// Configure Systick
		cortexM01.setRVR(64);
		cortexM01.setCVR(2137);
		cortexM01.setEnable();
		cortexM01.setSourceInternal();
		cortexM01.setInterruptEnable();
		
		// Display initial values
		System.out.println("Initial values: ");
		System.out.println("RVR is: " + cortexM01.getRVR());
		System.out.println("CVR is: " + cortexM01.getCVR());
		System.out.println("CSR is: " + cortexM01.getCSR());
		System.out.println("CSR is: 0000 0000 " 
		        + (cortexM01.getCountFlag() ? 1 : 0)
		        + "000 0000 0000 0"
		        + (cortexM01.getSource() ? 1 : 0)
		        + (cortexM01.getInterrupt() ? 1 : 0)
		        + (cortexM01.getEnabled() ? 1 : 0));
		
		try{Thread.sleep(5000);}
		catch (InterruptedException e) {e.printStackTrace();}
		
		// Initialize loop variables
		boolean isRunning = true;
		int passCount = 0;
		
		// Configure time measurement
		long startTime = System.nanoTime();
		long elapsedTime;
		
		// Implement Systick
		while(isRunning) {
			cortexM01.tickInternal();
			System.out.println("CVR is: " + cortexM01.getCVR());
			
			try{Thread.sleep(10);}
			catch (InterruptedException e) {e.printStackTrace();}

			if(cortexM01.isInterrupt()) {
				// Add empty spaces to clear console
				for (int i = 0; i < 10; ++i) System.out.println();
				// Print interrupt number
				System.out.println("Number of passes: " + passCount);
				// Update number of passes
				passCount++;
				
				// Print amount of time passed
				elapsedTime = System.nanoTime() - startTime; 
		        System.out.println("Execution time in millis: "
		                + elapsedTime/1000000);
		        startTime = System.nanoTime();
			}
			
			// End loop after 10 passes
			if (passCount > 10) {
                isRunning = false;
            }
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Declarations of registers:
	 */
	/**
	 * CSR - Control and Status Register
	 * 
	 * A register holding regControl and interrupt status bits.
	 * Register composition:
	 * 	0000 000C 0000 0000 0000 0SIE;
	 * Where: C - countflag, S - source, I - interrupt, E - enable
	 */
	/**
	 * RVR - Reload Value Register
	 * 
	 * A register holding the value written into CVR on reload;
	 */
	/**
	 * CVR - Current Value Register
	 * 
	 * A register holding the current amount of ticks left.
	 */
	private Register RVR, CVR, CSR;		
	
	/**
	 * Constants to represent bit positions of CSR flags
	 */
	private static final int ENABLE = 0;
	private static final int TICKINT = 1;
	private static final int CLKSRC = 2;
	private static final int COUNTFLAG = 16;
	
	/**
	 * Declaration of interrupt variable
	 *  used to notify that an interrupt would normally be sent 
	 */
	private boolean interrupt;

	/**
	 * Declaration of action listener
	 */
	ActionListener cicero;
	
//////////////////////////////////////////////////////////////////////////////////////////////

	/**
	* Constructor for the SysTick timer. Initializes registers
	*  with zeros.
	*/
	public Systick(){		
		RVR = new Register();
		CVR = new Register();
		CSR = new Register();
	}

//////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @deprecated Use {@link #newDoTick()} instead.
     */
    @SuppressWarnings("unused")
	private void doTick() {
		if(CSR.readBit(ENABLE) != false) {		
			// Check if clock bit [0] is enabled
			if(CVR.readValue() != 0) {					
				// If the current value is not zero:
				CVR.writeValue((CVR.readValue() - 1)); 	
				// Execute a tick
				if(CVR.readValue() == 0) {				
					// If the current value just ticked to zero:
					CSR.setBit(COUNTFLAG);			
					// Set count flag bit [16]
					setInterrupt(true);
				}
			}
			else {									// If the current value is zero:
				CVR.writeValue(RVR.readValue());		// Reload current value
			}
		}
	}
    
	/**
	 * Universal internal function to execute a single tick
	 */
	private void newDoTick() {
	    if (isClockEnabled() != true) return;

	    if (CVR.readValue() != 0) {
	        executeTick();
	        if (CVR.readValue() == 0) {
	            setCountFlag();
	            setInterrupt(true);
	        }
	    } else {
	        reloadCurrentValue();
	        interrupt = false;
	    }
	}

	/**
	 * Methods to use internally in newDoTick() method.
	 */
	
	private boolean isClockEnabled() {
	    return CSR.readBit(ENABLE);
	}
	private void executeTick() {
	    CVR.writeValue(CVR.readValue() - 1);
	}
	private void setCountFlag() {
	    CSR.setBit(COUNTFLAG);
	}
	private void reloadCurrentValue() {
	    CVR.writeValue(RVR.readValue());
	}
	
	/**
	 * Methods to serve action listener
	 * @param mother
	 */
	public void addActionListener(ActionListener mother) {
		cicero=AWTEventMulticaster.add(cicero, mother);
	}
	public void removeActionListener(ActionListener mother) {
		cicero=AWTEventMulticaster.remove(cicero, mother);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
	/**
     * Performs a tick from the internal source clock.
     */
	@Override
	public void tickInternal() {
			
		if(CSR.readBit(CLKSRC) == false) {
			newDoTick();
			isInterrupt(); 
			
			if(cicero != null)cicero.actionPerformed(new ActionEvent(this,
					ActionEvent.ACTION_PERFORMED,
					""));
		}
	}
	/**
     * Performs a tick from the internal source clock.
     */
	@Override
	public void tickExternal() {
		
		if(CSR.readBit(CLKSRC) == true) {
			newDoTick();
			isInterrupt();
			
			if(cicero != null)cicero.actionPerformed(new ActionEvent(this,
					ActionEvent.ACTION_PERFORMED,
					""));
		}
	}
	

//////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void setCVR(int value) {
		CVR.writeValue(0x000000);
		CSR.resetBit(COUNTFLAG);
		//CVR will get reloaded with RVR in next clock cycle
		if(cicero != null)cicero.actionPerformed(new ActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				"setCVR"));
	}
	@Override
	public void setRVR(int value) {
		if(value == 0) {
			CSR.resetBit(ENABLE); 
			RVR.writeValue(0x000000);
			CVR.writeValue(0x000000);
		}
		else {
			RVR.writeValue(value);
		}
		
		if(cicero != null) cicero.actionPerformed(new ActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				"setRVR"));
	}
	@Override
	public void setCSR(int value) {
		CSR.writeValue(value);
		
		if(cicero != null) cicero.actionPerformed(new ActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				"setCSR"));
	}
	

//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Setters for different bit in Control and Status Register (CVR).
	 */
	@Override
	public void reset() {
		CSR.writeValue(0x000000);	
		
		if(cicero != null)
			newActionPerformed(cicero, "");
	}
	@Override
	public void setEnable() {
		CSR.setBit(ENABLE);
		
		if(cicero != null)
			newActionPerformed(cicero, "");
	}
	@Override
	public void setDisable(){
		CSR.resetBit(ENABLE);
		
		if(cicero != null)
			newActionPerformed(cicero, "");
	}
	@Override
	public void setInterruptEnable() {
		CSR.setBit(TICKINT);
		
		if(cicero != null)
			newActionPerformed(cicero, "");
	}
	@Override
	public void setInterruptDisable(){
		CSR.resetBit(TICKINT);
		
		if(cicero != null)
			newActionPerformed(cicero, "");
	}
	@Override 
	public void setSourceExternal() {
		CSR.setBit(CLKSRC);
		
		if(cicero != null)
			newActionPerformed(cicero, "");
	}
	@Override
	public void setSourceInternal() {
		CSR.resetBit(CLKSRC);
		
		if(cicero != null)
			newActionPerformed(cicero, "");
	}

	
	/**
	 * Setter for interrupt variable
	 *  sends an interrupt if countflag and tickint are set
	 *  
	 * @param status State to set interrupt to
	 */
    private void setInterrupt(boolean status) {
        if (CSR.readBit(TICKINT) && CSR.readBit(COUNTFLAG)) {
            System.out.println("Interrupt!\n");
            interrupt = status;
        } else {
            interrupt = false;
        }
        
//		if(cicero != null)
//			newActionPerformed(cicero, "interrupt");
    }
    
    
//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Getters for values stored in Registers.
	 */
	@Override
	public int getCVR() {
		return CVR.readValue();
	}
	@Override
	public int getRVR() {
		return RVR.readValue();
	}
	@Override
	public int getCSR() {
		int temp = CSR.readValue();
		CSR.resetBit(COUNTFLAG);
		return temp;
	}
	

//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Getters for specific bits stored in Control and Status Register (CVR).
	 */
	@Override
	public boolean getEnabled(){
		CSR.resetBit(COUNTFLAG);
		return CSR.readBit(ENABLE);
	}
	@Override
	public boolean getInterrupt() {
		CSR.resetBit(COUNTFLAG);
		return CSR.readBit(TICKINT);
	}
	@Override
	public boolean getSource() {
		CSR.resetBit(COUNTFLAG);
		return CSR.readBit(CLKSRC);
	}
	@Override
	public boolean getCountFlag() {
		boolean tmp = (CSR.readBit(COUNTFLAG));
		CSR.resetBit(COUNTFLAG);
		return tmp;
	}
	

//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Getters for bits in CVR, for debugging purpose only.
	 * 
	 * Note: They don't conform to the working of Systick described
	 * 		 in documentation
	 */
	@Override
	public boolean isEnableFlag() {
		return CSR.readBit(ENABLE);
	}
	@Override
	public boolean isInterruptFlag() {
		return CSR.readBit(TICKINT);
	}
	@Override
	public boolean isCountFlag() {
	    return CSR.readBit(COUNTFLAG);
	}
	
	/**
	 * Getter for interrupt variable
	 */
	@Override
	public boolean isInterrupt() {
		return interrupt;
	}
	
	
	private void newActionPerformed(ActionListener al, String txt) {
		al.actionPerformed(new ActionEvent(this,
				ActionEvent.ACTION_PERFORMED,
				txt));
	}
}
