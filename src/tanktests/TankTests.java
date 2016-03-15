/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanktests;

import SabotageTanks.GameClient;
import SabotageTanks.GameServer;
import SabotageTanks.Net.ConnectionClient;
import SabotageTanks.Net.ConnectionServer;
import SabotageTanks.Player;
import java.awt.Color;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 *
 * @author 1
 */
public class TankTests {

    static int PORT = 5005;
    
    public static void main(String... args) throws IOException
    {
        System.out.println("Inet4Address.getLocalHost().getHostAddress()");
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        
        System.out.println("Inet6Address.getLocalHost().getHostAddress()");
        System.out.println(Inet6Address.getLocalHost().getHostAddress());
        
        System.out.println("InetAddress.getLocalHost().getHostAddress()");
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        
        String localIp = null;
        
        while (interfaces.hasMoreElements())
        {
            NetworkInterface inter = interfaces.nextElement();
            
            Enumeration<InetAddress> addresses = inter.getInetAddresses();
            
            while (addresses.hasMoreElements())
            {
                InetAddress address = addresses.nextElement();
                if (address instanceof Inet4Address && address.isSiteLocalAddress())
                {
                    localIp = address.getHostAddress();
                    break;
                }
            }
            if (localIp != null)
            {
                break;
            }
        }
        
        System.out.println("Interfaces address");
        System.out.println(localIp);
        
        Player playerServer = new Player("playerServer", Color.yellow);
        Player playerClient = new Player("playerClient", Color.red);

        ConnectionServer connectServer = new ConnectionServer(PORT);
        ConnectionClient connectClient = new ConnectionClient(Inet4Address.getLocalHost().getHostAddress(), PORT, playerClient);

        GameServer server = new GameServer(playerServer, connectServer);
        GameClient client = new GameClient(playerClient, connectClient);
        
        server.start();
        client.start();
        
    }
    
}
