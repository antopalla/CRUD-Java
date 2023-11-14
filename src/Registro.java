import java.util.HashMap;
import java.util.Scanner;
public class Registro {

    private static HashMap <Integer, Utente> mappa;

    public Registro(){
        mappa = new HashMap <Integer, Utente>();
    }

    public void aggiungiUtente(Utente u){
        String username = u.getUsername();
        if(!esiste(username)){
            mappa.put(mappa.size(), u);
        }else{
            System.out.println("Utente già esistente.");
        }
    }

    private boolean esiste(String username){
         for(int i = 0 ; i < mappa.size() ; i++){
            if(username.equals(mappa.get(i).getUsername())){
                return true;
            }
        }
        return false;
    }


    public void cambiaPassword(Utente u){
        Scanner s = new Scanner(System.in);
        int tentativi = 0;
        String username = u.getUsername();
        if(esiste(username)){
            for(int i=0;i<mappa.size();i++){
                if(username.equals(mappa.get(i).getUsername())){
                    String vecchiaPassword = mappa.get(i).getPassword();
                    System.out.println("Inserisci la vecchia password dell'utente: ");
                    String vecchiaPasswordInput = s.nextLine();
                    vecchiaPasswordInput = HashPassword.hash(vecchiaPasswordInput);
                    tentativi++;
                    while(!vecchiaPasswordInput.equals(vecchiaPassword)&&tentativi<=3){
                        System.out.println("Password errata, riprova: ");
                        vecchiaPasswordInput = s.nextLine();
                        vecchiaPasswordInput = HashPassword.hash(vecchiaPasswordInput);
                        tentativi++;
                    } 
                    if(tentativi>3){
                        System.out.println("Hai superato il numero massimo di tentativi.");
                    }else{
                        System.out.println("Inserisci la nuova password: ");
                        String nuovaPassword = s.nextLine();
                        mappa.get(i).setPassword(nuovaPassword);
                        System.out.println("Password cambiata con successo.");                    
                    }
                }
            }
        } 
    }   
    
}
