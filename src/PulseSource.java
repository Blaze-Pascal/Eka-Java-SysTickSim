import java.awt.event.*;

// wywoluje funkcjie tick external/internal
// trzeba powiazac systick i generator
// burst mode - dziala na x impulsow
// continuous - dziala ciagle
// ma sam sprawdzic 

public interface PulseSource {
    final static byte BURST_MODE = 0;
    final static byte CONTINOUS_MODE = 1;

    void addActionListener(ActionListener pl);// upraszczamy (powinna być nowa klasa PulseListener)
    void removeActionListener(ActionListener pl);// upraszczamy (powinna być nowa klasa  PulseListener)

    void trigger(); //rozpocznij generację
    void setMode(byte mode);
    byte getMode() ;
    void halt() ;	//zatrzymaj generację 
    void setPulseDelay(int ms) ;	// ile impulsów ma trwać
    int getPulseDelay() ;			// ile czasu ma czekac na impulsie
    void setPulseCount(int burst) ;	// ile impulsow ma zrobic w burscie
    //get pulse count
    //zmienna do ubijania wątku killThread czy jakoś tak?
}
