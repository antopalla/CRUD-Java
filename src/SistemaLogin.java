import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SistemaLogin {

    private static Map<String, String> utenti = new HashMap<>();
    private static Map<String, Integer> tentativiFalliti = new HashMap<>();
    private static final int MAX_TENTATIVI = 3;

    public static void main(String[] args) {
        utenti.put("utente1", "password123");
        utenti.put("utente2", "segreto456");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Inserisci username: ");
            String username = scanner.nextLine();

            System.out.print("Inserisci password: ");
            String password = scanner.nextLine();

            if (effettuaLogin(username, password)) {
                System.out.println("Accesso riuscito!");
                break;
            } else {
                System.out.println("Credenziali errate. Riprova.");
            }
        }

        // System.out.println("Fai qualcosa dopo il login...");

        scanner.close();
    }

    private static boolean effettuaLogin(String username, String password) {
        if (utenti.containsKey(username)) {
            if (tentativiFalliti.getOrDefault(username, 0) >= MAX_TENTATIVI) {
                System.out.println("Account bloccato. Riprova più tardi.");
                return false;
            }

            if (utenti.get(username).equals(password)) {

                tentativiFalliti.put(username, 0);
                // Esegui la gestione della sessione (potresti usare una classe Session
                // dedicata)
                gestisciSessione(username);
                return true;
            } else {
                // Credenziali errate, incrementa i tentativi falliti
                int tentativi = tentativiFalliti.getOrDefault(username, 0);
                tentativiFalliti.put(username, tentativi + 1);
                System.out.println("Credenziali errate. Tentativi rimanenti: " + (MAX_TENTATIVI - tentativi - 1));
                return false;
            }
        } else {

            System.out.println("Account non trovato.");
            return false;
        }
    }

    private static void gestisciSessione(String username) {
        // Implementa la gestione della sessione qui (ad esempio, salva lo stato
        // dell'utente, genera un token, ecc.)
        System.out.println("Sessione per " + username + " creata con successo.");
    }
}