package xmpp;

import java.io.File;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.omg.CORBA.Environment;

public class FileTransfer {

    public static void SendFile(String pIdUsuario, String pArchivo, String pDescripcion)
    {
        FileTransferManager manager = new FileTransferManager(XMPP.gConnection);
        OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(pIdUsuario);
        
        try {
           transfer.sendFile(new File(pArchivo), pDescripcion);
        } catch (XMPPException e) {
           e.printStackTrace();
        }
        while(!transfer.isDone()) {
           if(transfer.getStatus().equals(Status.error))
              System.out.println("ERROR!!! " + transfer.getError());
           else if (transfer.getStatus().equals(Status.cancelled) || transfer.getStatus().equals(Status.refused))
              System.out.println("Cancelled!!! " + transfer.getError());
           
           try {
              Thread.sleep(1000L);
           } catch (InterruptedException e) {
              e.printStackTrace();
           }
        }
        if(transfer.getStatus().equals(Status.refused) || transfer.getStatus().equals(Status.error)
        || transfer.getStatus().equals(Status.cancelled))
            System.out.println("refused cancelled error " + transfer.getError());
        else
            System.out.println("Success");
    }
    
    public static void ReceiveFile(XMPPConnection pConnection)
    {
        FileTransferManager manager = new FileTransferManager(pConnection);
manager.addFileTransferListener(new FileTransferListener() {
   public void fileTransferRequest(final FileTransferRequest request) {
      new Thread(){
         @Override
         public void run() {
            IncomingFileTransfer transfer = request.accept();
            String home = System.getProperty("user.home");
            File file = new File(home + "/Downloads/" + transfer.getFileName());
            try{
                transfer.recieveFile(file);
                while(!transfer.isDone()) {
                   try{
                      Thread.sleep(1000L);
                   }catch (Exception e) {
                      System.out.println("Error: " + e.getMessage());
                   }
                   if(transfer.getStatus().equals(Status.error)) {
                      System.out.println("Error: " + transfer.getError());
                   }
                   if(transfer.getException() != null) {
                      transfer.getException().printStackTrace();
                   }
                }
             }catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
         };
       }.start();
    }
 });
    }
}

