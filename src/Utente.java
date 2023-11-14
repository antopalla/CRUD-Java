import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utente {
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private int amministratore;

    public Utente (String username, String password, int amministratore) {
        this.username = username;
        this.password = HashPassword.hash(password);
        this.nome = "";
        this.cognome = "";
        this.dataDiNascita = LocalDate.parse("1970-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.amministratore = amministratore;
    }

    public Utente (String username, String password, String nome, String cognome, LocalDate dataDiNascita, int amministratore) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.amministratore = amministratore;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = HashPassword.hash(password);
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public void setCognome (String cognome) {
        this.cognome = cognome;
    }

    public void setDataDiNascita (LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public void setAmministratore (int amministratore) {
        this.amministratore = amministratore;
    }

    public String getUsername () {
        return username;
    }

    public String getPassword () {
        return password;
    }

    public String getNome () {
        return nome;
    }

    public String getCognome () {
        return cognome;
    }

    public LocalDate getDataDiNascita () {
        return dataDiNascita;
    }

    public int getAmministratore () {
        return amministratore;
    }

    @Override
    public String toString () {
        return "Username: " + username + "\nNome: " + nome + "\nCognome: " + cognome + "\nData di nascita: " + dataDiNascita.toString();
    }
}