import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Registro {
    private GestioneDB conn;

    private static HashMap <Integer, Utente> mappa;

    public Registro(String DB_URL, String DB_USERNAME, String DB_PASSWORD){
        conn = new GestioneDB(DB_URL, DB_USERNAME, DB_PASSWORD);
        conn.creaTabella();
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

    public boolean esiste(String username){
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
                return mappa.get(i);
            }
        }
        return null;
    }

    public void aggiornaUtente (String username, Utente x) {
        conn.modificaUtente(username, x);
        mappa = conn.scaricaUtenti();
    }

    public void eliminaUtente (String username) {
        conn.eliminaUtente(username);
        mappa = conn.scaricaUtenti();
    }

    public void cambiaPassword (String username) {
        Scanner scanner = new Scanner(System.in);
        int tentativi = 0;

        String vecchiaPassword = getUtente(username).getPassword();
        
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
            return;
        } 
        else {
            System.out.println("Inserisci la nuova password: ");
            String nuovaPassword = scanner.nextLine();
                        
            conn.aggiornaPassword(username, HashPassword.hash(nuovaPassword));
            mappa = conn.scaricaUtenti();

            System.out.println("Password cambiata con successo.");        
            return; 
        }
    }   
}
