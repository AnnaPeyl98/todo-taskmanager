package it.sevenbits.spring_boot.task_manager.web.service.tokens;

import it.sevenbits.spring_boot.task_manager.core.model.User;
import org.springframework.security.core.Authentication;

import java.time.Duration;

/**
 * Interface of a service for generating and parsing tokens
 */
public interface JwtTokenService {
    /**
     * Create token for user
     *
     * @param user user, that needs a token
     * @return token
     */
    String createToken(User user);

    /**
     * Get token life duration
     *
     * @return token duration
     */
    Duration getTokenExpiredIn();

    /**
     * Parse token
     *
     * @param token received token
     * @return authenticated token
     */
    Authentication parseToken(String token);
}
