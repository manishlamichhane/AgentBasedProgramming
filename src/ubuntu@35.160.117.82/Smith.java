package agent;

import java.io.IOException;
import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;



public class Smith extends Agent {

	private String serverIP = "";
	private int serverPort = 0;
	private long timeForTickerBehaviour = 0;
	
	protected void setup() {
		// Printout a welcome message
		
		serverIP = getProperty("serverIP","localhost");
		serverPort = Integer.parseInt(getProperty("serverPort","5678"));
		timeForTickerBehaviour = Long.parseLong(getProperty("timeForTicker","3000"));
		
		System.out.println("Messenger agent "+getAID().getName()+" is ready.");


		/*This part will query the AMS to get list of all active agents in all containers*/

		/** This piece of code, to register services with the DF, is explained
		 * in the book in section 4.4.2.1, page 73 
		 **/
		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Attacker agent");
		sd.setName(getLocalName()+"-Attacker agent");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// a new tcp connection is to be made in every 30 secs (the parameters are passed in millisecs)
		addBehaviour(new MakeTCPConnection(this,timeForTickerBehaviour));
	}
	
	protected void takeDown() {

		// Printout a dismissal message
		System.out.println("Agent: "+getAID().getName()+"terminating.");

		/** This piece of code, to deregister with the DF, is explained
		 * in the book in section 4.4.2.1, page 73 
		 **/
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
	// The implementation of ticker behavior of agent class
	// It will make a periodic tcp connection to the specified server
	
	public class MakeTCPConnection extends TickerBehaviour{
		
		public MakeTCPConnection(Agent a, long period) {
	
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onTick() {
			// TODO Auto-generated method stub
			// create a tcp connection here
			
			ClientSocket soc = new ClientSocket(serverIP,serverPort);
			try {
				soc.connect();
				System.out.println("Connection Established with ip: "+serverIP+" at port: "+serverPort+" at Ticker value: "+timeForTickerBehaviour);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	} 
	
}
