import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class GestioneDB {
    Connection conn = null;

    public GestioneDB (String url, String username, String password) {
         try {
            conn = DriverManager.getConnection(url, username, password);
            if (conn != null) {
                System.out.println("Connessione stabilita.");
                System.out.println();
            }
            else {
                System.out.println("Impossibile connettersi!");
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public HashMap<Integer, Utente> scaricaUtenti () {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users");
            ResultSet res = preparedStatement.executeQuery();

            HashMap <Integer, Utente> utenti = new HashMap<>();

            while (res.next()) {
                String username = res.getString("username");
                String password = res.getString("password");
                String nome = res.getString("nome");
                String cognome = res.getString("cognome");
                String data = res.getString("datadinascita");
                int amministratore = res.getInt("amministratore");

                Utente u = new Utente(username, password, nome, cognome, LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd")), amministratore);

                utenti.put(utenti.size(), u);
            }

            return utenti;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void inserisciUtente (Utente u) {
        String query = "INSERT INTO users (username, password, nome, cognome, datadinascita, amministratore) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, u.getUsername());
            preparedStatement.setString(2, u.getPassword());
            preparedStatement.setString(3, u.getNome());
            preparedStatement.setString(4, u.getCognome());
            preparedStatement.setString(5, u.getDataDiNascita().toString());
            preparedStatement.setInt(6, u.getAmministratore());

            int righeModificate = preparedStatement.executeUpdate();

            if (righeModificate != 0) {
                System.out.println("Inserimento effettuato!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aggiornaPassword (String username, String password) {
        String query = "UPDATE users SET password = ? WHERE username = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);

            int righeModificate = preparedStatement.executeUpdate();

            if (righeModificate != 0) {
                System.out.println("Modifica effettuata!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPassword (String username) {
        String query = "SELECT * FROM users WHERE username = ?";

        String password = "";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, username);

            ResultSet res = preparedStatement.executeQuery();
            
            while (res.next()) {
                password = res.getString("password");
            }   
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    public void chiudiRisorse () {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}