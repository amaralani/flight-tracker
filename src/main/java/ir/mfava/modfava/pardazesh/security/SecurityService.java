package ir.mfava.modfava.pardazesh.security;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
