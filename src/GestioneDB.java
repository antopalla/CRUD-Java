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

            }
            else {
                System.out.println("Impossibile connettersi!");
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public void creaTabella () {
        String queryVerificaTabella = "SHOW TABLES LIKE 'users';";
        String queryTabella = "CREATE TABLE users (id int(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,username varchar(32) NOT NULL,password varchar(255) NOT NULL,nome varchar(32) NOT NULL,cognome varchar(32) NOT NULL,datadinascita date NOT NULL,amministratore tinyint(1) NOT NULL)";
        String queryInsertAmministratore = "INSERT INTO users (id, username, password, nome, cognome, datadinascita, amministratore) VALUES(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'Ammini', 'Stratore', '1970-01-01', 1);";

         try {
            PreparedStatement statement1 = conn.prepareStatement(queryVerificaTabella);
            PreparedStatement statement2 = conn.prepareStatement(queryTabella);
            PreparedStatement statement3 = conn.prepareStatement(queryInsertAmministratore);

            ResultSet res = statement1.executeQuery();
            
            while (res.next()) {
                return;
            }
            statement2.executeUpdate(queryTabella);
            statement3.executeUpdate(queryInsertAmministratore);   

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

    public void modificaUtente (String username, Utente u) {
        String modificaUsername = "UPDATE users SET username = ? WHERE username = ?";
        String modificaPassword = "UPDATE users SET password = ? WHERE username = ?";
        String modificaNome = "UPDATE users SET nome = ? WHERE username = ?";
        String modificaCognome = "UPDATE users SET cognome = ? WHERE username = ?";
        String modificaDataDiNascita = "UPDATE users SET datadinascita = ? WHERE username = ?";
        String modificaAmministratore = "UPDATE users SET amministratore = ? WHERE username = ?";
        
        try {
            PreparedStatement preparedStatementUsername = conn.prepareStatement(modificaUsername);
            PreparedStatement preparedStatementPassword = conn.prepareStatement(modificaPassword);
            PreparedStatement preparedStatementNome = conn.prepareStatement(modificaNome);
            PreparedStatement preparedStatementCognome = conn.prepareStatement(modificaCognome);
            PreparedStatement preparedStatementDataDiNascita = conn.prepareStatement(modificaDataDiNascita);
            PreparedStatement preparedStatementAmministratore = conn.prepareStatement(modificaAmministratore);

            preparedStatementUsername.setString(1, u.getUsername());
            preparedStatementUsername.setString(2, username);

            preparedStatementPassword.setString(1, u.getPassword());
            preparedStatementPassword.setString(2, username);

            preparedStatementNome.setString(1, u.getNome());
            preparedStatementNome.setString(2, username);

            preparedStatementCognome.setString(1, u.getCognome());
            preparedStatementCognome.setString(2, username);

            preparedStatementDataDiNascita.setString(1, u.getDataDiNascita().toString());
            preparedStatementDataDiNascita.setString(2, username);

            preparedStatementAmministratore.setInt(1, u.getAmministratore());
            preparedStatementAmministratore.setString(2, username);
            
            int righeModificate1 = preparedStatementUsername.executeUpdate();
            int righeModificate2 = preparedStatementPassword.executeUpdate();
            int righeModificate3 = preparedStatementNome.executeUpdate();
            int righeModificate4 = preparedStatementCognome.executeUpdate();
            int righeModificate5 = preparedStatementDataDiNascita.executeUpdate();
            int righeModificate6 = preparedStatementAmministratore.executeUpdate();

            if (righeModificate1 != 0 && righeModificate2 != 0 && righeModificate3 != 0 && righeModificate4 != 0 && righeModificate5 != 0 && righeModificate6 != 0) {
                System.out.println("Modifiche effettuate!");
                System.out.println();
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

    public void eliminaUtente (String username) { 
        String query = "DELETE FROM users WHERE username = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, username);

            int righeModificate = preparedStatement.executeUpdate();

            if (righeModificate != 0) {
                System.out.println("Eliminazione effettuata!");
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