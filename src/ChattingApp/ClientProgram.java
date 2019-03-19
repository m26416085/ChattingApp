/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChattingApp;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 *
 * @author Jeffrey
 */
public class ClientProgram extends Listener{
       
    //our client object
    static Client client;
    //ip to connect to
    static String ip = "76.73.114.85";
    //ports to connect on
    static int tcpPort = 27960, udpPort = 27960;
    
    //a boolean value
    static boolean messageReceived = false;
    
    public static void main(String[] args) throws Exception
    {
        System.out.println("Connecting to the server...");
        //create the client
        client = new Client();
        
        //Register the packet object
        client.getKryo().register(PacketMessage.class);
        
        //start the client
        client.start();
        //the client MUST be started before connecting can take place
        
        //connect to the server - wait 5000ms before failing
        client.connect(5000, ip, tcpPort, udpPort);
       
        
        //add a listener
        client.addListener(new ClientProgram());
        
        System.out.println("Connected! The client program is now waiting for a packet...\n");
        
        //This is here to stop the program from closing before we receive a message
        while (!messageReceived)
        {
            Thread.sleep(1000);
        }
        System.out.println("Client will now exit");
        System.exit(0);
    }
    
    // im only going to implement this method from listener.class because i only need to use this one
    public void received(Connection c, Object p)
    {
        //is the received packet the same class as PacketMessage.class?
        if (p instanceof PacketMessage)
        {
            //cast it, so we can access the message within
            PacketMessage packet = (PacketMessage) p;
            System.out.println("Received a message from the host: "+packet.message);
            
            //we now received the message!
            messageReceived = true;
        }
    }
}
