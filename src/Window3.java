import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import java.awt.Color;

//////////////////////////////////////////////////////////////////////////////////////////////

public class Window3 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
//////////////////////////////////////////////////////////////////////////////////////////////

	public Window3() {
		
		/**
		 * Create instances of a Systick and Generator
		 */
		Systick systick = new Systick();
		Generator generator = new Generator();

		/**
		 * Configure window
		 */
		setSize(new Dimension(1400, 550));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel Systick = new JPanel();
		getContentPane().add(Systick);
		//splitPane.setLeftComponent(Systick);
		Systick.setLayout(new BorderLayout(5, 5));
		
		JPanel panel_systickCtrl = new JPanel();
		Systick.add(panel_systickCtrl, BorderLayout.WEST);
		panel_systickCtrl.setLayout(new GridLayout(3, 1, 0, 0));
		
		JToggleButton btnSetEnable = new JToggleButton("Enable");
		btnSetEnable.setFont(new Font("OCR A Extended", Font.PLAIN, 18));
		panel_systickCtrl.add(btnSetEnable);
		
		JToggleButton btnSetInterrupt = new JToggleButton("Interrupt");
		btnSetInterrupt.setFont(new Font("OCR A Extended", Font.PLAIN, 18));
		panel_systickCtrl.add(btnSetInterrupt);
		
		JToggleButton btnSetSource = new JToggleButton("Source: I");
		btnSetSource.setFont(new Font("OCR A Extended", Font.PLAIN, 18));
		panel_systickCtrl.add(btnSetSource);
		
		JPanel panel_systickReg = new JPanel();
		Systick.add(panel_systickReg, BorderLayout.SOUTH);
		panel_systickReg.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_stSetReg = new JPanel();
		panel_systickReg.add(panel_stSetReg);
		panel_stSetReg.setLayout(new GridLayout(3, 0, 0, 0));
		
		JRadioButton btnSetCVR = new JRadioButton("Set CVR");
		btnSetCVR.setHorizontalAlignment(SwingConstants.CENTER);
		btnSetCVR.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_stSetReg.add(btnSetCVR);
		
		JRadioButton btnSetRVR = new JRadioButton("Set RVR");
		btnSetRVR.setSelected(true);
		btnSetRVR.setHorizontalAlignment(SwingConstants.CENTER);
		btnSetRVR.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_stSetReg.add(btnSetRVR);
		
		JRadioButton btnSetCSR = new JRadioButton("Set CSR");
		btnSetCSR.setHorizontalAlignment(SwingConstants.CENTER);
		btnSetCSR.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_stSetReg.add(btnSetCSR);
		
		JSpinner systickValToSend = new JSpinner();
		systickValToSend.setFont(new Font("OCR A Extended", Font.PLAIN, 24));
		panel_systickReg.add(systickValToSend);
		
		JButton btnSendBtn = new JButton("Send");
		btnSendBtn.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_systickReg.add(btnSendBtn);
		
		JPanel panel_systickOp = new JPanel();
		Systick.add(panel_systickOp, BorderLayout.EAST);
		panel_systickOp.setLayout(new GridLayout(3, 2, 0, 0));
		
		JButton btnDoTick = new JButton("Tick (E)");
		btnDoTick.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_systickOp.add(btnDoTick);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_systickOp.add(btnReset);
		
		JToggleButton interruptIndicator = new JToggleButton("INTERRUPT!");
		interruptIndicator.setSelected(true);
		interruptIndicator.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		interruptIndicator.setBackground(Color.YELLOW);
		panel_systickOp.add(interruptIndicator);
		
		JPanel panel_systickShow = new JPanel();
		Systick.add(panel_systickShow, BorderLayout.CENTER);
		panel_systickShow.setLayout(new GridLayout(6, 0, 0, 0));
		
		JLabel lblCVRVal = new JLabel("   CVR");
		lblCVRVal.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 24));
		panel_systickShow.add(lblCVRVal);
		
		JTextField textCVRVal = new JTextField();
		textCVRVal.setText("0");
		textCVRVal.setFont(new Font("OCR A Extended", Font.ITALIC, 20));
		textCVRVal.setEditable(false);
		textCVRVal.setColumns(10);
		panel_systickShow.add(textCVRVal);
		
		JLabel lblRVRVal = new JLabel("   RVR");
		lblRVRVal.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 24));
		panel_systickShow.add(lblRVRVal);
		
		JTextField textRVRVal = new JTextField();
		textRVRVal.setText("0");
		textRVRVal.setFont(new Font("OCR A Extended", Font.ITALIC, 20));
		textRVRVal.setEditable(false);
		textRVRVal.setColumns(10);
		panel_systickShow.add(textRVRVal);
		
		JLabel lblCSRVal = new JLabel("   CSR");
		lblCSRVal.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 24));
		panel_systickShow.add(lblCSRVal);
		
		JTextField textCSRVal = new JTextField();
		textCSRVal.setText("0000 0000 0000 0000 0000 0000");
		textCSRVal.setFont(new Font("OCR A Extended", Font.ITALIC, 20));
		textCSRVal.setEditable(false);
		textCSRVal.setColumns(10);
		panel_systickShow.add(textCSRVal);
		
		JPanel panel_systickTitle = new JPanel();
		Systick.add(panel_systickTitle, BorderLayout.NORTH);
		
		JLabel lblSystickTitle = new JLabel("SYSTICK PANEL");
		lblSystickTitle.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 30));
		panel_systickTitle.add(lblSystickTitle);
		
		
////////////////////////////////////////////////////////////////////
		
		/**
		 * Create Generator side of control panel
		 */
		JPanel Generator = new JPanel();
		getContentPane().add(Generator);
		//splitPane.setRightComponent(Generator);
		Generator.setLayout(new BorderLayout(5, 5));
		
		JPanel panel_genTitle = new JPanel();
		Generator.add(panel_genTitle, BorderLayout.NORTH);
		
		JLabel lblGeneratorTitle = new JLabel("GENERATOR PANEL");
		lblGeneratorTitle.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 30));
		panel_genTitle.add(lblGeneratorTitle);
		
		JPanel panel_genOp = new JPanel();
		Generator.add(panel_genOp, BorderLayout.WEST);
		panel_genOp.setLayout(new GridLayout(3, 0, 0, 0));
		 
		JButton btnGenaratorOn = new JButton("Trigger");
		btnGenaratorOn.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_genOp.add(btnGenaratorOn);
		
		JToggleButton tickIndicator = new JToggleButton("TICK!");
		tickIndicator.setBackground(Color.ORANGE);
		tickIndicator.setSelected(true);
		tickIndicator.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		panel_genOp.add(tickIndicator);
		
		Knob delayKnob = new Knob();
		panel_genOp.add(delayKnob);
		
		JPanel panel_genReg = new JPanel();
		Generator.add(panel_genReg, BorderLayout.SOUTH);
		panel_genReg.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_genReg.add(panel_2);
		panel_2.setLayout(new GridLayout(3, 0, 0, 0));
		
		JRadioButton btnSetDelay = new JRadioButton("Set Delay");
		btnSetDelay.setSelected(true);
		btnSetDelay.setHorizontalAlignment(SwingConstants.CENTER);
		btnSetDelay.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_2.add(btnSetDelay);
		
		JRadioButton btnSetFreq = new JRadioButton("Set Freqency");
		btnSetFreq.setHorizontalAlignment(SwingConstants.CENTER);
		btnSetFreq.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_2.add(btnSetFreq);
		
		JRadioButton btnSetBurst = new JRadioButton("Set Burst");
		btnSetBurst.setHorizontalAlignment(SwingConstants.CENTER);
		btnSetBurst.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_2.add(btnSetBurst);
		
		JSpinner genValToSend = new JSpinner();
		genValToSend.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_genReg.add(genValToSend);
		
		JButton btnGenSend = new JButton("Send");
		btnGenSend.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_genReg.add(btnGenSend);
		
		JPanel panel_genCtrl = new JPanel();
		Generator.add(panel_genCtrl, BorderLayout.EAST);
		panel_genCtrl.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnGenAlive = new JButton("STOP");
		btnGenAlive.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_genCtrl.add(btnGenAlive);
		
		JToggleButton btnGenMode = new JToggleButton("Mode: B");
		btnGenMode.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_genCtrl.add(btnGenMode);
		
		JPanel panel_genCtrl_burstCount = new JPanel();
		panel_genCtrl.add(panel_genCtrl_burstCount);
		panel_genCtrl_burstCount.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Multiplier");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_genCtrl_burstCount.add(lblNewLabel, BorderLayout.NORTH);
		
		JSpinner genBurstMultiplierValue = new JSpinner();
		genBurstMultiplierValue.setFont(new Font("OCR A Extended", Font.PLAIN, 20));
		panel_genCtrl_burstCount.add(genBurstMultiplierValue, BorderLayout.CENTER);
		
		JPanel panel_genShow = new JPanel();
		Generator.add(panel_genShow, BorderLayout.CENTER);
		panel_genShow.setLayout(new GridLayout(6, 0, 0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_genShow.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblGenDelay = new JLabel("   Delay");
		lblGenDelay.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 24));
		panel_5.add(lblGenDelay);
		
		JLabel lblGenFreq = new JLabel("   Frequency");
		lblGenFreq.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 24));
		panel_5.add(lblGenFreq);
		
		JPanel panel_6 = new JPanel();
		panel_genShow.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 2, 0, 0));
		
		JTextField textGenDelay = new JTextField();
		textGenDelay.setText("0");
		textGenDelay.setFont(new Font("OCR A Extended", Font.ITALIC, 20));
		textGenDelay.setEditable(false);
		textGenDelay.setColumns(10);
		panel_6.add(textGenDelay);
		
		JTextField textGenFreq = new JTextField();
		textGenFreq.setText("0");
		textGenFreq.setFont(new Font("OCR A Extended", Font.ITALIC, 20));
		textGenFreq.setEditable(false);
		textGenFreq.setColumns(10);
		panel_6.add(textGenFreq);
		
		JLabel lblNewLabel_1 = new JLabel("   Burst");
		lblNewLabel_1.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 24));
		panel_genShow.add(lblNewLabel_1);
		
		JTextField textGenBurst = new JTextField();
		textGenBurst.setText("0");
		textGenBurst.setEditable(false);
		textGenBurst.setFont(new Font("OCR A Extended", Font.ITALIC, 20));
		panel_genShow.add(textGenBurst);
		textGenBurst.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("   CDR");
		lblNewLabel_3.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 24));
		panel_genShow.add(lblNewLabel_3);
		
		JTextField textGenCSR = new JTextField();
		textGenCSR.setFont(new Font("OCR A Extended", Font.ITALIC, 20));
		textGenCSR.setText("0000 0000 0000 0000 0000 0000");
		textGenCSR.setEditable(false);
		panel_genShow.add(textGenCSR);
		textGenCSR.setColumns(10);
		setVisible(true);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////

		/*
		 * Add action listeners to systick panel side
		 */
		btnSetEnable.addActionListener(e->{
			if(btnSetEnable.isSelected())
				systick.setEnable();
			else
				systick.setDisable();
		});
		btnSetInterrupt.addActionListener(e->{
			if(btnSetInterrupt.isSelected())
				systick.setInterruptEnable();
			else 
				systick.setInterruptDisable();
		});
		btnSetSource.addActionListener(e->{
			if(btnSetSource.isSelected()) {
				btnSetSource.setText("Source: E");
				systick.setSourceExternal();
			}
			else {
				btnSetSource.setText("Source: I");
				systick.setSourceInternal();
			}
		});
		btnSendBtn.addActionListener(e->{
			if (btnSetCVR.isSelected()) {
				systick.setCVR((Integer)systickValToSend.getValue());
			}
			if (btnSetRVR.isSelected()) {
				systick.setRVR((Integer)systickValToSend.getValue());
			}
			if (btnSetCSR.isSelected()) {
				systick.setCSR((Integer)systickValToSend.getValue());
			}
		});
		btnDoTick.addActionListener(e->{
			systick.tickExternal();
		});
		btnReset.addActionListener(e->{
			systick.reset();
		});
		interruptIndicator.addActionListener(e->{
			systick.isInterrupt();
		});
		
		systick.addActionListener(e->{
			textCVRVal.setText(String.valueOf(systick.getCVR()));
			textRVRVal.setText(String.valueOf(systick.getRVR()));
			//textCSRVal.setText(String.valueOf(systick.getCSR()));
			textCSRVal.setText(
					"0000 0000 " 
					+ (systick.getCountFlag() ? 1 : 0)
					+ "000 0000 0000 0"
					+ (systick.getSource() ? 1 : 0)
			        + (systick.getInterrupt() ? 1 : 0)
			        + (systick.getEnabled() ? 1 : 0));
			
			btnSetEnable.setSelected(systick.getEnabled());
			btnSetInterrupt.setSelected(systick.getInterrupt());
			btnSetSource.setSelected(systick.getSource());
			
			interruptIndicator.setSelected(!systick.isInterrupt());
			
		});
		
/////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/*
		 * Add action listeners to generator panel side
		 */
		btnGenAlive.addActionListener(e->{
				generator.killGen();
		});
		
		delayKnob.addActionListener(e->{
			double x = delayKnob.getAngleAdj();
			
			if (btnSetDelay.isSelected()) {
				int tmp = (int) (Math.pow(32768, (x/(2*Math.PI-Math.PI/4))));
				if (tmp > 65535)
					tmp = 65535;
				generator.setPulseDelay(tmp);
				//generator.setPulseDelay((int) ((x*65535)/(2*(Math.PI-Math.PI/16))));
			}
			
			else if (btnSetBurst.isSelected()) {
				int tmp = (int) (Math.pow(8388607, (x/(2*Math.PI-Math.PI/4))));
				if (tmp > 16777215)
					tmp = 16777215;
				generator.setPulseCount(tmp);
				//generator.setPulseCount((int) ((x*65535)/(2*(Math.PI-Math.PI/16))));
			}

			else if (btnSetFreq.isSelected()) {
				int tmp = (int) (Math.pow(32768, (x/(2*Math.PI-Math.PI/4))));
				if (tmp > 65535)
					tmp = 65535;
				generator.setPulseFreq(tmp);
				//generator.setPulseFreq((int) ((x*65535)/(2*(Math.PI-Math.PI/16))));
			}
		});
		
		btnGenMode.addActionListener(e->{
			if(btnGenMode.isSelected()) {
				btnGenMode.setText("Mode: C");
				generator.setMode((byte)1);
			}

			else {
				btnGenMode.setText("Mode: B");
				generator.setMode((byte)0);
			}

		});
		
		btnGenaratorOn.addActionListener(e->{
			if (generator.getEnable() != 1) {
				btnGenaratorOn.setText("  Halt  ");
				generator.trigger();
			}
			else if (generator.getEnable() != 0) {
				btnGenaratorOn.setText("Trigger");
				generator.halt();
			}
		});
		
		btnGenSend.addActionListener(e->{
			if (btnSetDelay.isSelected()) {
				generator.setPulseDelay((Integer)genValToSend.getValue());
			}
			if (btnSetBurst.isSelected()) {
				generator.setPulseCount((Integer)genValToSend.getValue());
			}
			if (btnSetFreq.isSelected()) {
				generator.setPulseFreq((float) ((Integer)(genValToSend.getValue())));
			}
		});
		
		genBurstMultiplierValue.addChangeListener(e->{
			int tmp = (Integer)genBurstMultiplierValue.getValue();
			
			if(tmp < 0) {
				genBurstMultiplierValue.setValue(0);
				generator.setBurstScale(0);
			}
			if(tmp > 0 && tmp < 16) {
				generator.setBurstScale(tmp);
			}
			if(tmp>15){
				genBurstMultiplierValue.setValue(31);
				generator.setBurstScale(31);
			}
		});
		
		generator.addActionListener(e->{
			textGenDelay.setText(String.valueOf(generator.getPulseDelay()));
			textGenFreq.setText(String.valueOf(generator.getPulseFreq()));
			
			// Print CDR individual bits
			String txt = "";
			int cDR = generator.getCDR();
			for (int i = 0; i < 24; i++) {
			    txt = (cDR & 1) + txt; 
			    cDR = cDR >>> 1; 
			    if ((i + 1) % 4 == 0 && i != 23) {
			        txt = " " + txt;
			    }
			}
			
			textGenCSR.setText(txt);
			textGenBurst.setText(String.valueOf(generator.getPulseCount()));
			
			if(e.getActionCommand()=="tic") 
			{
				systick.tickInternal();
				tickIndicator.setSelected(false);
				textCVRVal.setText(String.valueOf(systick.getCVR()));
				
			}	
			else if(e.getActionCommand()=="tac")
				tickIndicator.setSelected(true);

			textCVRVal.setText(String.valueOf(systick.getCVR()));
		});
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window3 window = new Window3();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//new Window3();
	}
}
