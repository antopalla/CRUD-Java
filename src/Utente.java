public class Utente {
    private String username;
    private String password;

    public Utente (String username, String password) {
        this.username = username;
        this.password = HashPassword.hash(password);
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = HashPassword.hash(password);
    }

    public String getUsername () {
        return username;
    }

    public String getPassword () {
        return password;
    }
}