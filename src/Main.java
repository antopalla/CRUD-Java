import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static String DB_URL = "jdbc:mysql://localhost/utenti";
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        Registro registroUtenti = new Registro(DB_URL, DB_USERNAME, DB_PASSWORD);

        int menu = -1;
        do {
            System.out.println("1 per loggarti;\n2 per uscire: ");
            menu = scan.nextInt();
            System.out.println();
            
            switch (menu) {
                case 1 -> {
                    if (SistemaLogin.eseguiLogin(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                        if (registroUtenti.getUtente(SistemaLogin.usernameAttuale).getAmministratore() == 1) {
                            int menuAmministratore = -1;
                            do {
                                System.out.println("Inserisci 1 per aggiungere un utente;\n2 per stampare gli utenti;\n3 per uscire: ");
                                menuAmministratore = scan.nextInt();
                                scan.nextLine();
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
                                System.out.println("Inserisci 1 per aggiungere nuove informazioni;\n2 per visualizzare le proprie informazioni;\n3 per modificare username e password;\n4 per eliminare il proprio account;\n5 per uscire: ");
                                menuUtente = scan.nextInt();
                                scan.nextLine();
                                System.out.println();

                                switch (menuUtente) {
                                    case 1 -> {
                                        int sottoMenu = -1;
                                        Utente x = registroUtenti.getUtente(SistemaLogin.usernameAttuale);
                                        do {
                                            System.out.println("Inserisci 1 per impostare nome;\n2 per il cognome;\n3 per la data di nascita;\n4 per uscire: ");
                                            sottoMenu = scan.nextInt();
                                            scan.nextLine();
                                            System.out.println();

                                            switch (sottoMenu) {
                                                case 1 -> {
                                                    System.out.println("Inserisci il nome: ");
                                                    String nome = scan.nextLine();
                                                    System.out.println();
                                                    x.setNome(nome);
                                                }
                                                case 2 -> {
                                                    System.out.println("Inserisci il cognome: ");
                                                    String cognome = scan.nextLine();
                                                    System.out.println();
                                                    x.setCognome(cognome);
                                                }
                                                case 3 -> {
                                                    System.out.println("Inserisci la data di nascita yyyy-MM-dd: ");
                                                    String data = scan.nextLine();
                                                    System.out.println();
                                                    x.setDataDiNascita(LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                                }
                                                case 4 -> {
                                                    registroUtenti.aggiornaUtente(SistemaLogin.usernameAttuale, x);
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
                                        int sottoMenu = -1;
                                        Utente x = registroUtenti.getUtente(SistemaLogin.usernameAttuale);
                                        do {
                                            System.out.println("Inserisci 1 per cambiare username;\n2 per cambiare la password: ");
                                            sottoMenu = scan.nextInt();
                                            scan.nextLine();
                                            System.out.println();
                                            
                                            switch (sottoMenu) {
                                                case 1 -> {
                                                    String username = "";
                                                    do {
                                                        System.out.println("Inserisci il nuovo username, non deve essere presente!");
                                                        username = scan.nextLine();
                                                        System.out.println();
                                                    } while (registroUtenti.esiste(username));
                                                    
                                                    x.setUsername(username);
                                                    registroUtenti.aggiornaUtente(SistemaLogin.usernameAttuale, x);
                                                    System.out.println();
                                                    sottoMenu = 0;
                                                    menuUtente = 0;
                                                }
                                                case 2 -> {
                                                    registroUtenti.cambiaPassword(SistemaLogin.usernameAttuale);
                                                    System.out.println();
                                                    sottoMenu = 0;
                                                    menuUtente = 0;
                                                }
                                            }
                                        } while (sottoMenu != 0);
                                    }
                                    case 4 -> {
                                        System.out.println("Sicuro di eliminare il proprio utente? Y per confermare");
                                        String input = scan.nextLine();
                                        if (input.equals("Y")) {
                                            registroUtenti.eliminaUtente(SistemaLogin.usernameAttuale);
                                            menuUtente = 0;
                                            break;
                                        }
                                    }
                                    case 5 -> {
                                        menuUtente = 0;
                                    }
                                }
                            } while (menuUtente != 0);
                        }
                    }
                }
                case 2 -> {
                    menu = 0;
                }
            }
        } while (menu !=0);
    }
}
