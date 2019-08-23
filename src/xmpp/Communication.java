package xmpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.VCard;

public class Communication {
    
    public static void ShowContacts()
    {
        Roster roster = XMPP.gConnection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();
        System.out.println("\nUser\t\t\tState\t\t\tConnected");
        for (RosterEntry entry : entries) {
            System.out.println(entry.getName() + "\t\t\t" + entry.getStatus() + "\t\t\t" + roster.getPresence(entry.getUser()));
        }
        
        Generals.Pause("Detail of contacts successfully displayed.");
    }
    
    public static void AddContact()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nEnter the contact: ");
        String lContacto = scan.nextLine();
        
        try
        {
            Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
            Roster roster = XMPP.gConnection.getRoster();

            if (!roster.contains(lContacto)){
                roster.createEntry(lContacto + "@alumchat.xyz", lContacto, new String[] { "Friends" });
                Generals.Pause("Contact added successfully.");
            }else
                Generals.Pause("Contact already added.");
        }
        catch(XMPPException ex)
        {
            Generals.Pause("Error: " + ex.getMessage());
        }
    }
     
    public static void ShowContactInfo()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.print("\nEnter the contact: ");
            String lContact = scan.nextLine() + "@alumchat.xyz";
            
            VCard card = new VCard();
            card.load(XMPP.gConnection, lContact);
            System.out.println("\nUser Information");
            System.out.println("First Name: " + card.getFirstName());
            System.out.println("Last Name: " + card.getLastName());
            System.out.println("Email: " + card.getEmailHome());
            
            Roster roster = XMPP.gConnection.getRoster();
            Collection<RosterEntry> entries = roster.getEntries();
            RosterEntry entry = roster.getEntry(lContact);
            if (entry!=null)
            {
                System.out.println("User: " + entry.getUser());
                System.out.println("State: " + entry.getStatus());
                System.out.println("Presence: " + roster.getPresence(lContact));
                System.out.println("Grupos");
                for (RosterGroup group : entry.getGroups())
                    System.out.println(group.getName());
            }
            
            Generals.Pause("User information successfully displayed.");
        
         }catch (Exception e) {
            Generals.Pause("Error: " + e.getMessage());
        }
    }
    
    public static void DefinePresenceMessage()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter the message of presence: ");
        String lMensajePresencia = scan.nextLine();
        
        Presence lPresence = new Presence(Presence.Type.available);
        lPresence.setStatus(lMensajePresencia);
        XMPP.gConnection.sendPacket(lPresence);
        
        Generals.Pause("Message defined successfully.");
    }
    
    public static void SendMessageToContact()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nEnter the contact: ");
        String lContacto = scan.nextLine() + "@alumchat.xyz";
        
        try{
            createEntry(XMPP.gConnection,lContacto,lContacto,null);
            String lMensaje = "";
            ChatManager chatManager = XMPP.gConnection.getChatManager();
            Chat chat = chatManager.createChat(lContacto, new MessageListener() {
            @Override
                public void processMessage(Chat chat, Message msg) {
                    String from = msg.getFrom();
                    String body = msg.getBody();
                    if (body != null)
                    {
                        System.out.println(from + "typing...");
                        System.out.println(from + "'s message: " + body);
                        if (body.equalsIgnoreCase("bye")){
                            Generals.Pause("Conversation completed successfully.");
                            return;
                        }
                    }
                }
            });

            while(lMensaje.equalsIgnoreCase("bye") == false){
                System.out.print("Enter the message: ");
                lMensaje = scan.nextLine();
                System.out.println("Your message: " + lMensaje);
                chat.sendMessage(lMensaje);
            }
                    
            Generals.Pause("Conversation completed successfully.");

            }catch(XMPPException e){
                Generals.Pause("Error: " + e.getMessage());
            }catch(Exception e){
                Generals.Pause("Error: " + e.getMessage());
        }
    }
    
    public static void createEntry(XMPPConnection pConnection, String pUser, String pName, String pGrupo) throws Exception {
        System.out.println(String.format("Creating entry for buddy '%1$s' with name %2$s", pUser, pName));
        Roster roster = pConnection.getRoster();
        roster.createEntry(pUser, pName, (pGrupo == null) ? new String[] { pGrupo } : null);
    }
    
    public static void CreateGroup()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nEnter the groupname: ");
        String lGroupName = scan.nextLine();
        
        try {
            // Generate the roster with the current connection
            Roster roster = XMPP.gConnection.getRoster();
            roster.createGroup(lGroupName);
            Generals.Pause("Group created successfully.");
        } catch (Exception e) {
            Generals.Pause("Error: " + e.getMessage());
        }
    }
    
    public static void AddContactToGroup()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nEnter the groupname: ");
        String lGroupName = scan.nextLine() + "@alumchat.xyz";
        System.out.print("Enter the contact: ");
        String lContact = scan.nextLine() + "@alumchat.xyz";
        
        try {
            // Generate the roster with the current connection
            Roster roster = XMPP.gConnection.getRoster();
            // Searchs for a group if it does not exists creates it
            RosterGroup group = roster.getGroup(lGroupName);
            // Search entries and add them if they are not in the group
            RosterEntry entry = roster.getEntry(lContact);
            if (entry != null)
            {
                group.addEntry(entry);
                Generals.Pause("Contact added successfully.");
            }
        } catch (XMPPException e) {
            Generals.Pause("Error: " + e.getMessage());
        }
    }
    
    public static void CreateGroupChat()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.print("\nEnter the groupname: ");
            String lGroupName = scan.nextLine();

            MultiUserChat muc = new MultiUserChat(XMPP.gConnection, lGroupName + "@conference.alumchat.xyz");
            muc.create(lGroupName);
            Form form = muc.getConfigurationForm();
            Form submitForm = form.createAnswerForm();
            for (Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
                    FormField field = (FormField) fields.next();
                    if (!FormField.TYPE_HIDDEN.equals(field.getType()) && field.getVariable() != null) {
                    submitForm.setDefaultAnswer(field.getVariable());
                }
            }
            
            // Set owners and configurations of the room
            List<String> owners = new ArrayList<String>();
            owners.add(XMPP.gConnection.getUser());
            submitForm.setAnswer("muc#roomconfig_roomowners", owners);
            submitForm.setAnswer("muc#roomconfig_persistentroom", true);
            submitForm.setAnswer("muc#roomconfig_roomdesc", lGroupName);
            muc.sendConfigurationForm(submitForm);
            
        }catch(Exception e)
        {
            Generals.Pause("Error: " + e.getMessage());
        }
    }
    
    public static void SendMessageToGroup()
    {
         Scanner scan = new Scanner(System.in);
         System.out.print("\nEnter the groupname: ");
         String lGroupName = scan.nextLine() + "@conference.alumchat.xyz";
         System.out.print("\nEnter the message: ");
         String lMessage = scan.nextLine();
         
        try {
            MultiUserChat muc = new MultiUserChat(XMPP.gConnection,lGroupName);
            if (muc != null)
            {
                muc.join(XMPP.gConnection.getUser().replace("@alumchat.xyz/Smack",""));
                muc.sendMessage(lMessage);
                muc.addMessageListener(new TaxiMultiListener());
                
                while(lMessage.equalsIgnoreCase("bye") == false)
                {
                    System.out.println("Ingrese el mensaje");
                    lMessage= scan.nextLine();
                    muc.sendMessage(lMessage);
                }
                
                muc.leave();
                
                Generals.Pause("Groupchat ended successfully.");
            } 
        } catch (XMPPException e) {
            Generals.Pause("Error: " + e.getMessage());
        }
    }
    
    public static class TaxiMultiListener implements PacketListener {
    @Override
    public void processPacket(Packet packet) {
            Message message = (Message) packet;
            String from = message.getFrom();
            String body = message.getBody();
            if (body != null)
                System.out.println(String.format("Receiving message from: '%1$s' from %2$s", body, from));
        }
    }
    public static void VerNotificaciones()
    {
        Roster roster = XMPP.gConnection.getRoster();
         
    }
    
    
}