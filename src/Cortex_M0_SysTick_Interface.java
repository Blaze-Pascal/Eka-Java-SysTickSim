
public interface Cortex_M0_SysTick_Interface {
	
//////////////////////////////////////////////////////////////////////////////////////////////

    public void tickInternal();	
    public void tickExternal();	
    
//////////////////////////////////////////////////////////////////////////////////////////////

    public void setCVR(int value);
    public void setRVR(int value);
    public void setCSR(int value);
    
//////////////////////////////////////////////////////////////////////////////////////////////

    public void reset();
    public void setEnable();
    public void setDisable();
    public void setInterruptEnable();
    public void setInterruptDisable();
    public void setSourceExternal();
    public void setSourceInternal();
    
//////////////////////////////////////////////////////////////////////////////////////////////

    public int getCVR();
    public int getRVR();
    public int getCSR();
    
//////////////////////////////////////////////////////////////////////////////////////////////

    public boolean getEnabled();
    public boolean getInterrupt();
    public boolean getSource();	
    public boolean getCountFlag();
    
//////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isEnableFlag();
    public boolean isInterruptFlag();
    public boolean isCountFlag();
    
    public boolean isInterrupt();
    
}
