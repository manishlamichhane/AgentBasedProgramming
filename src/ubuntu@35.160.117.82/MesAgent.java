package agent;

import java.util.ArrayList;

import jade.core.ProfileImpl;

import jade.core.behaviours.OneShotBehaviour;
/*These imports are needed to Query AMS for all active agents*/
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import jade.core.Runtime;



public class MesAgent extends GuiAgent {
	//	private MesAgentGui myGui;
	private AgentGui myGui;
	private String serverIP = "";
	private String serverPort = "";
	private String timeForTickerBehaviour = "";
	private int noOfAgents = 0; //no of agents to be added
	private String messagePerformative="";
	private String allConversation = ""; // all the conversations will be appended here
	public  ArrayList<AgentController> agentList;
	public final static String AgentclassPath = "agent.Smith";
	public int agentCount = 0;
	

	protected void setup() {
		// Printout a welcome message
		
		System.out.println("Messenger agent "+getAID().getName()+" is ready.");

		AMSAgentDescription [] agents = null;
		agentList	=	new ArrayList();

		try {
			SearchConstraints c = new SearchConstraints();
			c.setMaxResults ( new Long(-1) );
			agents = AMSService.search( this, new AMSAgentDescription (), c );
		}
		catch (Exception e) {  }

	
		myGui = new AgentGui(this);
		myGui.setTitle(this.getAID().getLocalName());
	
		/** This piece of code, to register services with the DF, is explained
		 * in the book in section 4.4.2.1, page 73 
		 **/
		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("messenger-agent");
		sd.setName(getLocalName()+"-Messenger agent");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		//addBehaviour(new ReceiveMessage());
	}

	/**
	 * Agent clean-up
	 **/
	protected void takeDown() {
		// Dispose the GUI if it is there
		if (myGui != null) {
			myGui.dispose();
		}

		// Printout a dismissal message
		System.out.println("Seller-agent "+getAID().getName()+"terminating.");

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
	
	public class CreateAgent extends OneShotBehaviour {
		public void action() {
			
			Runtime rt = Runtime.instance();
			ProfileImpl p = new ProfileImpl(false);
			p.setParameter("serverIP", serverIP);
			p.setParameter("serverPort", serverPort);
			p.setParameter("timeForTicker", timeForTickerBehaviour);
			jade.wrapper.AgentContainer container =rt.createMainContainer(p);	        
		       	
			for(int counter = 0;counter < noOfAgents; counter ++){
				
				AgentController Agent = null;
				try {
					agentList.add(((ContainerController) container).createNewAgent("Agent"+agentCount, "agent.Smith", null));
					agentList.get(agentList.size()-1).start(); // gives the recently added object
					agentCount++;
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	//get all entered input from gui agent
	public void getFromGui(final String newAgents) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				/*messagePerformative = messageType;
				receiver = dest; //the correct input from the gui is <ipaddress> <agent@platform-id> */
				
				String[] parameters = newAgents.split(" ");
				if(parameters.length != 4){
					System.out.println("Insufficient parameters. Expected: ServerIP ServerPort TimeforTickerBehaviourInMilliSeconds NoOfAgents ");
					System.exit(0);
				}
				
				serverIP = parameters[0];
				serverPort = parameters[1];
				timeForTickerBehaviour = parameters[2]; 
				noOfAgents = Integer.parseInt(parameters[3]);
			}
		} );
	}
	//predefined function of GuiAgent - see postGuiEvent() in MesAgentGui.java
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		addBehaviour(new CreateAgent());

	}

}