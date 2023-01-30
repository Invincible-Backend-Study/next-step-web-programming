package domain.common;


import java.util.Optional;

public class DomainException extends RuntimeException {
    private final String redirectUrl;

    public DomainException(String message, String redirectUrl) {
        super(message);
        this.redirectUrl = redirectUrl;
    }

    public DomainException(String message) {
        super(message);
        this.redirectUrl = null;
    }

    public Optional<String> getRedirectUrl() {
        return Optional.ofNullable(redirectUrl);
    }
}
