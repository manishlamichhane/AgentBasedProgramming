# AgentBasedProgramming
Agent based programming in Java using Jade.
MesAgent.java creates multiple number (OneShot Behaviour) of Smith Agents which will make a TCP connection (TickerBehaviour) to the MultiThreaded Server in runtime. The number of Agents to be created can be changed from the GUI.  

Dependency: Jade

To compile:

javac -cp <classpath to jade.jar>

To run:

java -cp <classpath to jade.jar> jade.Boot -gui <AgentName>:agent/MesAgent

