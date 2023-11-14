import java.util.Scanner;

public class Main {
    private static String DB_URL = "jdbc:mysql://localhost/utenti";
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        Registro registroUtenti = new Registro(DB_URL, DB_USERNAME, DB_PASSWORD);

        if (SistemaLogin.eseguiLogin(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            if (registroUtenti.getUtente(SistemaLogin.usernameAttuale).getAmministratore() == 1) {
                int menuAmministratore = -1;
                do {
                    System.out.println("Inserisci 1 per aggiungere un utente;\n2 per stampare gli utenti;\n3 per uscire: ");
                    menuAmministratore = scan.nextInt();
                    System.out.println();

                    switch  (menuAmministratore) {
                        case 1 -> {
                            registroUtenti.aggiungiUtente();
                            System.out.println();
                        }
                        case 2 -> {
                            registroUtenti.stampaUtenti();
                            System.out.println();
                        }
                        case 3 -> {
                            menuAmministratore = 0;
                        }
                    }

                } while (menuAmministratore != 0);
            }
            else {
                int menuUtente = -1;
                do {
                    System.out.println("Inserisci 1 per aggiungere nuove informazioni;\n2 per visualizzare le proprie informazioni;\n3 per modificare le proprie informazioni;\n4 per eliminare il proprio account;\n5 per uscire: ");
                    menuUtente = scan.nextInt();
                    System.out.println();

                    switch (menuUtente) {
                        case 1 -> {
                            int sottoMenu = -1;
                            do {
                                System.out.println("Inserisci 1 per impostare nome;\n2 per il cognome;\n3 per la data di nascita;\n4 per uscire: ");
                                sottoMenu = scan.nextInt();
                                System.out.println();

                                switch (sottoMenu) {
                                    case 1 -> {

                                    }
                                    case 2 -> {

                                    }
                                    case 3 -> {

                                    }
                                    case 4 -> {
                                        sottoMenu = 0;
                                    }
                                }
                            } while (sottoMenu != 0);
                        }
                        case 2 -> {
                            System.out.println(registroUtenti.getUtente(SistemaLogin.usernameAttuale).toString());
                            System.out.println();
                        }
                        case 3 -> {

                        }
                        case 4 -> {

                        }
                        case 5 -> {
                            menuUtente = 0;
                        }
                    }
                } while (menuUtente != 0);
            }
        }
    }
}
