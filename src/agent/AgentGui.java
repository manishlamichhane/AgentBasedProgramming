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
	JComboBox messageTypeJC , receiverJC;
	String messageType="";
	JTextArea messages;
	
	/*Update*/
	
	JLabel remoteAddressLbl;
	JTextField remoteAddressTF;

	/*Update Close*/
	
	
	public AgentGui(MesAgent a) {
		super(a.getLocalName());
		myAgent = a;
		
		frame = new JFrame();
		frame.setTitle(myAgent.getAID().getLocalName());
		frame.setBounds(100, 100, 450, 300);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		/*Update*/
		
		/*remoteAddressLbl	=	new JLabel("agent@ip");
		remoteAddressTF		=	new JTextField();*/

		/*Update Close*/

		
		
		/*JLabel messageTypelabel = new JLabel("Message Type");
		messageTypelabel.setBounds(27, 11, 101, 14);
		frame.getContentPane().add(messageTypelabel);*/
		
		/*Update*/
		
		//JLabel receiverLabel = new JLabel("Receivers");
		//receiverLabel.setBounds(234, 11, 72, 14);
		//frame.getContentPane().add(receiverLabel);
		
		/*remoteAddressLbl.setBounds(234, 11, 72, 14);
		frame.getContentPane().add(remoteAddressLbl);*/
		/*Update ends*/

		JButton sendMessageBtn = new JButton("send");
		sendMessageBtn.setBounds(27, 112, 89, 23);
		frame.getContentPane().add(sendMessageBtn);

		JTextArea messageToBeSent = new JTextArea("serverIPAddress port timeForTickerInMillisec noOfAgents");
		messageToBeSent.setBounds(27, 61, 359, 40);
		frame.getContentPane().add(messageToBeSent);

		/*JLabel messageToBeSentLabel = new JLabel("Text to be sent :");
		messageToBeSentLabel.setBounds(27, 36, 113, 14);
		frame.getContentPane().add(messageToBeSentLabel);

		messages = new JTextArea();
		messages.setBounds(27, 177, 359, 48);
		frame.getContentPane().add(messages);

		JLabel messagesLabel = new JLabel("Conversation :");
		messagesLabel.setBounds(27, 152, 113, 14);
		frame.getContentPane().add(messagesLabel);

		messageTypesList.add("Inform");
		messageTypesList.add("Propose");

		receiversList = new ArrayList<String>();
		// Prepare a local copy of the agentList (belonging to MesAgent) to remove this agents name from the list
		// It is needed to exclude this agents name from the dropdown / combo list message recepients
		// Makes no sense sending message to ownself

		for(String agentName : MesAgent.agentList){
			if(myAgent.getName().equals(agentName) || receiversList.contains(agentName))
				continue;
			receiversList.add(agentName);
		}*/

		/*messageTypeJC = new JComboBox(messageTypesList.toArray());
		//add actionListener to comboBox when any message type is selected
		messageTypeJC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Object selected = messageTypeJC.getSelectedItem();
				if(selected.toString().equals("Request"))
					messageType = "Request";
				else if(selected.toString().equals("Propose"))
					messageType = "Propose";
			}
		});*/

		/*messageTypeJC.setBounds(125, 8, 89, 20);
		frame.getContentPane().add(messageTypeJC);*/

		/*Update*/
		
		//receiverJC = new JComboBox(receiversList.toArray());
		//receiverJC.setBounds(310, 8, 95, 20);
		/*remoteAddressTF.setBounds(310, 8, 95, 20);
		frame.getContentPane().add(receiverJC);
		frame.getContentPane().add(remoteAddressTF);*/
		/*Update ends*/
		
		
		frame.setVisible(true);
		//button to send 
		sendMessageBtn.addActionListener( new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ae) {
				try {
					String noOfNewAgents = messageToBeSent.getText().trim();
					/*Update*/
					//myAgent.getFromGui(messageType, receiverJC.getSelectedItem().toString(), content);
					myAgent.getFromGui(noOfNewAgents);
					/*Update ends*/
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
