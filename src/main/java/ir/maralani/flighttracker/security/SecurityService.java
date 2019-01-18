package ir.maralani.flighttracker.security;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
