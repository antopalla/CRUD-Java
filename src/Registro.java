import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Registro {
    private GestioneDB conn;

    private static HashMap <Integer, Utente> mappa;

    public Registro(String DB_URL, String DB_USERNAME, String DB_PASSWORD){
        conn = new GestioneDB(DB_URL, DB_USERNAME, DB_PASSWORD);
        mappa = conn.scaricaUtenti();
    }

    public HashMap<Integer, Utente> getMappa () {
        mappa = conn.scaricaUtenti();
        return mappa;
    }

    public void aggiungiUtente () {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inserisci un username: ");
        String username = scanner.nextLine();
        System.out.println("Inserisci una password: "); 
        String password = scanner.nextLine();
        System.out.println("1 se amministratore, altrimenti 0");
        int amministratore = scanner.nextInt();
        scanner.close();
        
        Utente u = new Utente(username, password, amministratore);

        if(!esiste(username)){
            conn.inserisciUtente(u);
            mappa = conn.scaricaUtenti();
        } else {
            System.out.println("Utente già esistente.");
        }
    }

    public void stampaUtenti () {
        mappa = conn.scaricaUtenti();

        System.out.println("Ecco gli utenti: ");
        for (Map.Entry<Integer, Utente> entry : mappa.entrySet()) {
            System.out.println(entry.getValue().toString());
            System.out.println();
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

    public Utente getUtente (String username){
        mappa = conn.scaricaUtenti();
         for(int i = 0 ; i < mappa.size() ; i++){
            if(username.equals(mappa.get(i).getUsername())){
                mappa.get(i);
            }
        }
        return null;
    }

    public void cambiaPassword (String username, String password) {
        Scanner scanner = new Scanner(System.in);
        int tentativi = 0;

        if(esiste(username)){
            for(int i=0;i<mappa.size();i++){
                if(username.equals(mappa.get(i).getUsername())){
                    String vecchiaPassword = mappa.get(i).getPassword();

                    System.out.println("Inserisci la vecchia password dell'utente: ");
                    String vecchiaPasswordInput = scanner.nextLine();
                    vecchiaPasswordInput = HashPassword.hash(vecchiaPasswordInput);
                    tentativi++;

                    while(!vecchiaPasswordInput.equals(vecchiaPassword) && tentativi <= 3) {
                        System.out.println("Password errata, riprova: ");
                        vecchiaPasswordInput = scanner.nextLine();
                        vecchiaPasswordInput = HashPassword.hash(vecchiaPasswordInput);
                        tentativi++;
                    } 
                    if (tentativi > 3){
                        System.out.println("Hai superato il numero massimo di tentativi.");
                        scanner.close();
                        break;
                    } 
                    else {
                        System.out.println("Inserisci la nuova password: ");
                        String nuovaPassword = scanner.nextLine();
                        
                        conn.aggiornaPassword(username, HashPassword.hash(nuovaPassword));
                        mappa = conn.scaricaUtenti();

                        System.out.println("Password cambiata con successo.");      
                        scanner.close(); 
                        break;             
                    }
                }
            }
        } 
    }   
}
