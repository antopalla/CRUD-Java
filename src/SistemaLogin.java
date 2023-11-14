import java.util.Scanner;

public class SistemaLogin {

    private static final int MAX_LOGIN_ATTEMPTS = 3;
    public static String usernameAttuale = "";
    
    public static boolean eseguiLogin(String DB_URL, String DB_USERNAME, String DB_PASSWORD) {
        GestioneDB conn = new GestioneDB(DB_URL, DB_USERNAME, DB_PASSWORD);
        Scanner scanner = new Scanner(System.in);

        int tentativi = 0;

        do {
            System.out.println("Inserire username: ");
            String username = scanner.nextLine();
            usernameAttuale = username;
            System.out.println("Inserire la password: ");
            String password = scanner.nextLine();
            
            String hash = conn.getPassword(username);

            if (hash.equals(HashPassword.hash(password))) {
                System.out.println("Accesso valido, benvenuto!");
                System.out.println();

                conn.chiudiRisorse();
                scanner.close();

                return true;
            } 
            else {
                tentativi++;
                System.out.println("Credenziali sbagliate. Tentativo " + tentativi + " di " + MAX_LOGIN_ATTEMPTS);

                if (tentativi >= MAX_LOGIN_ATTEMPTS) {
                    System.out.println("Hai superato i tentativi permessi!");

                    conn.chiudiRisorse();
                    scanner.close();

                    return false;
                }
            }
        } while (true);
    }
}


