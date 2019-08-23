package xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPP {

    /**
     * @param args the command line arguments
     */
    
    public static ConnectionConfiguration gConfiguration = null;
    public static XMPPConnection gConnection = null;
            
    public static void main(String[] args) throws XMPPException {
        /*
        Connection.ConfigurarConexion();
        int lOpcion = 0;
       
        while (lOpcion <= 16)
        {
            lOpcion = Menu.MostrarMenu();
            Menu.SelectOption(lOpcion);
        }
        */
        Generals.Pause("<******************** Thanks for using the application ********************>");
    }
}
