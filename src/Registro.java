import java.util.HashMap;
import java.util.Scanner;
public class Registro {

    private static HashMap <Integer, Utente> mappa;

    public Registro(){
        mappa = new HashMap <Integer, Utente>();
    }

    public void aggiungiUtente(HashMap <Integer,Utente> mappa){
      
        Scanner input = new Scanner(System.in);
        String username;
        String password;

        System.out.println("Inserire l' username > ");
        username = input.nextLine();

        System.out.println("Inserire password > ");
        password = input.nextLine();

        for(int i = 0 ; i < mappa.size() ; i++){
            if(esiste(username,mappa)){
                System.out.println("Username inserito non valido.");                
            }else{
                mappa.put(mappa.size(), new Utente(username, password));
            }
        }
    }

    public boolean esiste(String username, HashMap <Integer,Utente> mappa){
         for(int i = 0 ; i < mappa.size() ; i++){
            if(username.equals(mappa.get(i).getUsername())){
                return true;
            }
        }
        return false;
    }
    
}
