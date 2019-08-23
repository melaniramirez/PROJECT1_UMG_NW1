package xmpp;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.packet.VCard;

public class Administration extends Thread {
    
    public static void CreateAccount()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.print("\nEnter the username: ");
            String lNombre = scan.nextLine();
            System.out.print("Enter the lastname: ");
            String lApellido = scan.nextLine();
            System.out.print("Enter the user: ");
            String lUsuario = scan.nextLine();
            Console console = System.console();
            //String lClave = new String(console.readPassword("Enter password: "));
            System.out.print("Enter the pass: ");
            String lClave = scan.nextLine();
            
            XMPP.gConnection = new XMPPConnection(XMPP.gConfiguration);
            XMPP.gConnection.connect(); 
            AccountManager lManager = XMPP.gConnection.getAccountManager();
            lManager.createAccount(lUsuario,lClave);
            XMPP.gConnection.login(lUsuario, lClave);
            
            VCard vcard = new VCard();
            vcard.load(XMPP.gConnection, lUsuario + "@alumchat.xyz");
            vcard.setFirstName(lNombre);
            vcard.setLastName(lApellido);
            vcard.setEmailHome(lUsuario + "@alumchat.xyz");
            vcard.save(XMPP.gConnection);
            
            Generals.Pause("Account registered successfully.");
        }
        catch(Exception ex)
        {
            Generals.Pause("Error: " + ex.getMessage());
        }
    }
    
    public static void LogIn()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.print("\nEnter the user: ");
            String lUsuario = scan.nextLine();
            Console console = System.console();
            //String lClave = new String(console.readPassword("Enter password: "));
            System.out.print("Enter the pass: ");
            String lClave = scan.nextLine();
            
            XMPP.gConnection = new XMPPConnection(XMPP.gConfiguration);
            XMPP.gConnection.connect(); 
            XMPP.gConnection.login(lUsuario, lClave);
            
            Generals.Pause("Session successfully started.");
        }
        catch(Exception ex)
        {
            Generals.Pause("Error: " + ex.getMessage());
        }
    }
    
    public static void LogOut()
    {
        try
        {
            XMPP.gConnection.disconnect();
            XMPP.gConnection = null;
            
            Generals.Pause("Session closed successfully.");
        }
        catch(Exception ex)
        {
            Generals.Pause("Error: " + ex.getMessage());
        }
    }
    
    public static void DeleteAccount()
    {
        try
        {
            AccountManager lManager = XMPP.gConnection.getAccountManager();
            lManager.deleteAccount();
            XMPP.gConnection.disconnect();
            XMPP.gConnection = null;
            
            Generals.Pause("Account deleted successfully.");
        }
        catch(Exception ex)
        {
            Generals.Pause("Error: " + ex.getMessage());
        }
    }
}
