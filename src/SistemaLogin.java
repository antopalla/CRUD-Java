import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SistemaLogin {

    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final Map<String, Utente> utenti = new HashMap<>();

    public static void main(String[] args) {
        Utente user1 = new Utente("daje", "daj3");
        Utente user2 = new Utente("tutta", "tutt0");
        Utente user3 = new Utente("kikko", "kikk1");

        utenti.put(user1.getUsername(), user1);
        utenti.put(user2.getUsername(), user2);
        utenti.put(user3.getUsername(), user3);

        eseguiLogin();
    }

    private static void eseguiLogin() {
        Scanner scanner = new Scanner(System.in);

        int tentativi = 0;
        boolean accessoConsentito = false;

        do {
            System.out.println("Inserire username: ");
            String username = scanner.nextLine();
            System.out.println("Inserire la password: ");
            String password = scanner.nextLine();

            Utente utente = utenti.get(username);

            if (utente != null && utente.getPassword().equals(HashPassword.hash(password))) {
                System.out.println("Accesso valido, benvenuto!");
                accessoConsentito = true;
            } else {
                tentativi++;
                System.out.println("Credenziali sbagliate. Tentativo " + tentativi + " di " + MAX_LOGIN_ATTEMPTS);

                if (tentativi >= MAX_LOGIN_ATTEMPTS) {
                    System.out.println("Hai superato i tentativi permessi, riprova più tardi.");
                    break;
                }
            }
        } while (!accessoConsentito);

        scanner.close();
    }
}


