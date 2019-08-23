package xmpp;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Menu {
    
    public static int MostrarMenu()
    {
        int lOpcion = 0;
        
        Generals.ClearConsole();
        
        System.out.println("<******************** Menu Principal ********************>");
        System.out.println("Administración --------------------");
        System.out.println("1. Create new account");
        System.out.println("2. Log in");
        System.out.println("3. Log out");
        System.out.println("4. Delete account");
        System.out.println("Comunicación --------------------");
        System.out.println("5. Add user to contacts");
        System.out.println("6. Create group");
        System.out.println("7. Add contact to group");
        System.out.println("8. Show contacts");
        System.out.println("9. Show contact detail");
        System.out.println("10. Define presence message");
        System.out.println("11. Start conversation with contact");
        System.out.println("12. Create groupchat");
        System.out.println("13. Start group conversation");
        System.out.println("14. Enviar / recibir notificaciones");
        System.out.println("15. Send file");
        System.out.println("16. Receive file");
        System.out.println("17. End program");
        
        Scanner input = new Scanner(System.in);
        System.out.print("\nSelect an option: ");
        lOpcion = input.nextInt();
        
        return lOpcion;
    }
    
    public static void SelectOption(int pOption) throws XMPPException
    {
     
    }
}
