import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SystickTest {

	/**
     * Default constructor for test class Cortex_M0_SysTicTest
     */
    public SystickTest()
    {
        
    }

    
    @Test
    public void disable_test()
    {
        Systick cortexM01 = new Systick();
        cortexM01.setSourceInternal(); 
        cortexM01.setRVR(1);
        cortexM01.setCVR(4);
        cortexM01.setEnable();
        cortexM01.tickInternal();
        assertEquals(1, cortexM01.getCVR());
        cortexM01.setRVR(0);
        cortexM01.tickInternal();
        assertEquals(0, cortexM01.getCVR());
        assertEquals(0, cortexM01.getRVR());
        cortexM01.tickInternal();
        assertEquals(0, cortexM01.getCVR());
        assertEquals(0, cortexM01.getRVR());
        cortexM01.setRVR(2);
        cortexM01.tickInternal();
        assertEquals(0, cortexM01.getCVR());
        assertEquals(2, cortexM01.getRVR());
        cortexM01.setEnable();
        cortexM01.tickInternal();
        assertEquals(2, cortexM01.getCVR());
        cortexM01.tickInternal();
        assertEquals(1, cortexM01.getCVR());
        cortexM01.tickInternal();
        assertEquals(0, cortexM01.getCVR());
    }
    
    @Test
    public void testReload()
    {
    	Systick cortexM01 = new Systick();
        cortexM01.setSourceInternal(); 
        cortexM01.setCVR(4);
        cortexM01.setRVR(4);
        cortexM01.setEnable();
        cortexM01.tickInternal();
        assertEquals(4, cortexM01.getRVR());
        for (int i=0; i<5; i++) 
            cortexM01.tickInternal();
        assertEquals(4, cortexM01.getRVR());
        assertEquals(true, cortexM01.isCountFlag());
        cortexM01.getCSR();
        assertEquals(false, cortexM01.isCountFlag());
    }
    
    @Test
    public void testFlags()
    {
    	Systick cortexM01 = new Systick();
        cortexM01.setSourceInternal(); 
        assertEquals(false, cortexM01.isInterruptFlag());
        assertEquals(false, cortexM01.isCountFlag());
        assertEquals(false, cortexM01.isEnableFlag());
        cortexM01.setEnable(); 
        cortexM01.setInterruptEnable();
        assertEquals(true, cortexM01.isInterruptFlag());
        assertEquals(false, cortexM01.isCountFlag());
        assertEquals(true, cortexM01.isEnableFlag());
        cortexM01.setCVR(4);
        cortexM01.setRVR(1);
        cortexM01.tickInternal();
        cortexM01.tickInternal();
        assertEquals(0, cortexM01.getCVR());
        assertEquals(true, cortexM01.isCountFlag());
        cortexM01.getCSR();
        assertEquals(false, cortexM01.isCountFlag());
    }
    
    
    @Test
    public void testRVR()
    {
    	Systick cortexM01 = new Systick();
        cortexM01.setSourceInternal(); 
        cortexM01.setCVR(0);
        cortexM01.setRVR(0);
        assertEquals(0, cortexM01.getRVR());
        cortexM01.tickInternal();
        assertEquals(0, cortexM01.getCVR());
        cortexM01.setEnable();
        cortexM01.tickInternal();
        assertEquals(0, cortexM01.getCVR());
        cortexM01.setRVR(10);
        assertEquals(10, cortexM01.getRVR());
        cortexM01.setRVR(-3);
        assertEquals((1<<24)-3, cortexM01.getRVR());
        cortexM01.setRVR((1<<24)+2);
        assertEquals(2, cortexM01.getRVR());
    }
    
    
    @Test
    public void testInputs()
    {
    	Systick cortexM01 = new Systick();
        cortexM01.setSourceInternal(); 
        cortexM01.setCVR(0);
        cortexM01.setRVR(5);
        assertEquals(0, cortexM01.getCVR());
        cortexM01.setEnable();
        cortexM01.tickInternal();
        assertEquals(5, cortexM01.getCVR());
        assertEquals(false, cortexM01.isCountFlag());
        cortexM01.setSourceExternal(); 
        for(int i=0;i<5;++i)
            cortexM01.tickInternal();
        assertEquals(5, cortexM01.getCVR());
        assertEquals(false, cortexM01.isCountFlag());
        cortexM01.setSourceInternal();
        for(int i=0;i<5;++i)
            cortexM01.tickInternal();
        assertEquals(0, cortexM01.getCVR());
        assertEquals(true, cortexM01.isCountFlag());
        cortexM01.setCVR(10);
        assertEquals(0, cortexM01.getCVR());
        assertEquals(false, cortexM01.isCountFlag());
    }
    
 
    @Test
    public void testInt()
    {
    	Systick cortexM01 = new Systick();
        cortexM01.setSourceInternal(); 
        cortexM01.setCVR(0);
        cortexM01.setRVR(1);
        cortexM01.setEnable();
        cortexM01.setInterruptEnable();
        assertEquals(false, cortexM01.isInterrupt());
        cortexM01.tickInternal();
        cortexM01.tickInternal();
        assertEquals(true, cortexM01.isCountFlag());
        assertEquals(0, cortexM01.getCVR());
        assertEquals(true, cortexM01.isInterrupt());
        cortexM01.tickInternal();
        assertEquals(1, cortexM01.getCVR());
    }

}
