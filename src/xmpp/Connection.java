package xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

public class Connection {
 
    public static void ConfigurarConexion()
    {
        XMPP.gConfiguration = new ConnectionConfiguration("alumchat.xyz",5222);
        XMPP.gConfiguration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        XMPP.gConfiguration.setDebuggerEnabled(false);
        //XMPP.gConfiguration.setSASLAuthenticationEnabled(false);
        XMPP.gConfiguration.setSendPresence(true);
    }
    
    public static boolean EstaConectado()
    {
        return (XMPP.gConnection != null && XMPP.gConnection.isConnected());   
    }
}
