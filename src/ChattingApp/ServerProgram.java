/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChattingApp;

import java.util.Date;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;


public class ServerProgram extends Listener {

        RegisterData usernow = new RegisterData();
        
        public void setUsernow(RegisterData usernow) {
        this.usernow = usernow;
        }
	//Server object
	static Server server;
	//Ports to listen on
	static int udpPort = 27960, tcpPort = 27960;
	
	public static void main(String[] args) throws Exception {
		System.out.println("Creating the server...");
		//Create the server
		server = new Server();
		
		//Register a packet class.
		server.getKryo().register(PacketMessage.class);
		//We can only send objects as packets if they are registered.
		
		//Bind to a port
		server.bind(tcpPort, udpPort);
		
		//Start the server
		server.start();
		
		//Add the listener
		server.addListener(new ServerProgram());
		
		System.out.println("Server is operational!");
	}
	
	//This is run when a connection is received!
	public void connected(Connection c){
                MongoDB mongo = new MongoDB();
                String user = usernow.getName();
                System.out.println(usernow.getName());
		System.out.println("Received a connection from "+c.getRemoteAddressTCP().getHostString());
                System.out.println(user+" connected!");
		//Create a message packet.
		PacketMessage packetMessage = new PacketMessage();
		//Assign the message text.
		packetMessage.message = "Hello friend! The time is: "+new Date().toString();
		
		//Send the message
		c.sendTCP(packetMessage);
		//Alternatively, we could do:
		//c.sendUDP(packetMessage);
		//To send over UDP.
	}
	
	//This is run when we receive a packet.
	public void received(Connection c, Object p){
		//We will do nothing here.
		//We do not expect to receive any packets.
		//(But if we did, nothing would happen)
	}
	
	//This is run when a client has disconnected.
	public void disconnected(Connection c){
		System.out.println("A client disconnected!");
	}
}

