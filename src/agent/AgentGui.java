package agent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;



import jade.gui.GuiEvent;

import javax.swing.JComboBox;


public class AgentGui extends JFrame {

	MesAgent myAgent;
	JFrame frame;
	ArrayList<String> messageTypesList = new ArrayList<String>();
	ArrayList<String> receiversList;
	JLabel headerLabel;
	JLabel statusLabel;
	JPanel controlPanel;
	JLabel msglabel;
	JButton sendMessageBtn;
	JLabel messageToBeSentLabel , messagesLabel , messageTypeLable, receiverLabel;
	//sJComboBox messageTypeJC , receiverJC;
	String messageType="";
	JTextArea messages;
		
	
	public AgentGui(MesAgent a) {
		super(a.getLocalName());
		myAgent = a;
		
		frame = new JFrame();
		frame.setTitle(myAgent.getAID().getLocalName());
		frame.setBounds(100, 100, 450, 300);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	

		JButton sendMessageBtn = new JButton("send");
		sendMessageBtn.setBounds(27, 112, 89, 23);
		frame.getContentPane().add(sendMessageBtn);

		JTextArea messageToBeSent = new JTextArea("serverIPAddress port timeForTickerInMillisec noOfAgents");
		messageToBeSent.setBounds(27, 61, 359, 40);
		frame.getContentPane().add(messageToBeSent);

				
		
		frame.setVisible(true);
		//button to send 
		sendMessageBtn.addActionListener( new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ae) {
				try {
					String noOfNewAgents = messageToBeSent.getText().trim();
					myAgent.getFromGui(noOfNewAgents);
					messageToBeSent.setText("");
					
					GuiEvent ge = new GuiEvent(this, 1);
					myAgent.postGuiEvent(ge);
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(AgentGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );
	}
	public void setMessageTextArea(String text){
		messages.setText(text);
	}



}
