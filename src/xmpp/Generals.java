package xmpp;

import java.io.IOException;

public class Generals {

    public static void Pause(String pMensaje)
    {
        try
        {
            System.out.println("\n" + pMensaje);
            System.out.println("Press \"ENTER\" to continue...");
            System.in.read();
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void ClearConsole()
    {
        //Limpiar la consola
        try{
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor(); 
        }catch(Exception e){
            Generals.Pause("Error: " + e.getMessage());
        }
    }
}
