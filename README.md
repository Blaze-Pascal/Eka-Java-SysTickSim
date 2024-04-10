# Eka-Java-SysTickSim
 Introduction:
This is a simulation of SysTick timer from Coretex processor family systick written in Java with a GUI. It was written for one of my courses: programming in Java.

 Code structure:
- Register: backbone of the project, self written library to interpret and use integer numbers as registers,
- Generator: simulated pulse source, triggering systick,
- Systick: count down counter controlled by appropriate registers,
- Knob: self written GUI component made for easier values control,
- Window3: GUI with action listeners to other classes.

The rest are interfaces given to us by our professor. Comments in them are in Polish, if there is a need I will translate them to English.

 Summing up:
Learning something can come in a most unexpected of ways. I, for one, never imagine that a basics programming course would bring me so much understanding in the inner working of microcontrollers. And later I learned, that a library I have written for fun and not really understanding how registers work would actually turn out to be pretty accurate and helpful in using them on a real thing. The project works surprisingly well and made me learn a lot. Hope you find it somewhat helpful or at least inspiring. Glhf :)
