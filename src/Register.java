//////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Register - Simulated register for Stytick simulation.
 *
 * This class represents a simulated Register system. It 
 * contains basic methods to manipulate register values and
 * individual bits. It's designed to be integrated into Systick
 * simulation but can be easily expanded to fit more cases.
 *
 * Author: 263671
 * Date: November 11, 2023
 *
 * Usage:
 * - Create an instance of Register using one of the constructors,
 * - Use the provided methods to read from and write to Registers,
 * - Perform bitwise operations using readBit(), setBit() and resetBit().
 * 
 * Rev 1.1
 * Author: 263671
 * Date: December 21, 2023
 * 
 * Changes:
 * - Added ability to write and read blocks of data via 
 * readBlockValue(...) and writeBlockValue(...) for use
 * in Generator class.
 * 
 * - Added write protection to set/resetBit() methods
 */

//////////////////////////////////////////////////////////////////////////////////////////////

public class Register {
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Value stored inside called register.
	 */
	private int storedValue;
	
	/**
	 * Constant bit masks used by Register,
	 * Caps registers at 24 bits for use in systick simulation.
	 * 
	 * REGISTER_LENGTH is later referred to as regLen,
	 * REGISTER_INITIAL is later referred to as regInit.
	 */
	private static final int REGISTER_LENGTH = 0xFFFFFF;
	private static final int REGISTER_INITIAL = 0x000000;
	
//////////////////////////////////////////////////////////////////////////////////////////////

	/**
    * Constructors for the Register. Initializes registers
    *  with zeros or a value capped at 24-bit.
    */
	public Register() {
		storedValue = REGISTER_INITIAL;
	}
	public Register(int newValue) {
		storedValue = newValue & REGISTER_LENGTH;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public int readValue() {
		return storedValue;
	}
	/**
	 * Attention!: method rewrites whole register!
	 * 
	 * Use {@link #writeBlockValue(int, int, int)} for 
	 * writing to only a part of register 
	 */
	public void writeValue(int newValue) {
		storedValue = newValue & REGISTER_LENGTH;
	}
	
	/**
	 * Method to give boolean value based on value stored inside register bit.
	 * 
	 * Attention!: method does not include regLen protection
	 * if bitNum > regLen, returns 0
	 * if bitNum < 0, returns 1
	 * 
	 * @param bitNum - position of bit to read, from 0 to regLen-1
	 * @return boolean, true if bitVal = 1, false if bitVal = 0
	 */
	public boolean readBit(int bitNum) {
		return (storedValue & (1<<bitNum)) != 0;
	}
	// Write a 1 into bit number bitNum
	public void setBit(int bitNum) {
		storedValue = (storedValue | (1 << bitNum))& REGISTER_LENGTH;
	}
	// Write a 0 into bit number bitNum
	public void resetBit(int bitNum) {
		storedValue = (storedValue &~ (1 << bitNum))& REGISTER_LENGTH;
	}
	
	/**
	 * A function to read a block of values from a register
	 * 
	 * Used if register is partitioned and a block of bits 
	 * stores a continuous value
	 * 
	 * @param bitStart - first bit of read number
	 * @param maskLen - length of read number
	 * @return number bit shifted to be directly readable
	 */
	public int readBlockValue(int bitStart, int maskLen) {
		//Create mask of length maskLen, move mask to bitStart position
		int mask = ((1 << maskLen) - 1) << bitStart;
		//return the value of register under mask, move it back to 0 position
		return ((storedValue & mask) >> bitStart);
	}
	/**
	 * A function to override a block of values
	 * 
	 * Complimentary to {@link #readBlockValue(int, int)}
	 * 
	 * Replaces a previously stored block of values
	 * with new ones, keeping the rest of the register
	 * 
	 * @param bitStart - first bit of write position
	 * @param maskLen - amount of bits to be written 
	 * @param newValue - value to be written into register
	 */
	public void writeBlockValue(int bitStart, int maskLen, int newValue) {
		// create mask for bits you want to write to
		int mask = (((1 << maskLen) - 1) << bitStart);
		// clear those bits to prepare for writing
		storedValue = (storedValue &~ mask);
		// write new value
		storedValue = (storedValue | (newValue << bitStart))& REGISTER_LENGTH;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Main function used for testing new methods
	 */
	public static void main(String[] args) {
		//110110110110110110110110
		//000000000011111111110000
		Register CSR = new Register(0b110110110110110110110110);
//									//000000000011111111110000
		
		System.out.println(CSR.readBit(-10));
		System.out.println(" wartość rejestru: " + CSR.readValue());
		
		//Print every bit of register
		String txt = "";
		int cDR = CSR.readValue();
		for (int i = 0; i < 32; i++) {
		    txt = (cDR & 1) + txt; 
		    cDR = cDR >>> 1; 
		    if ((i + 1) % 4 == 0 && i != 31) {
		        txt = " " + txt;
		    }
		}
		System.out.println(txt);
		
        int wynik = CSR.readBlockValue(24, 10);
        System.out.println("Odczytana wartość: " + wynik);
        System.out.println(" wartość rejestru: " + CSR.readValue());
        
        CSR.writeBlockValue(24, 10, 1022);
        wynik = CSR.readBlockValue(4, 10);
        System.out.println("Odczytana wartość: " + wynik);
        System.out.println(" wartość rejestru: " + CSR.readValue());
    }
}
