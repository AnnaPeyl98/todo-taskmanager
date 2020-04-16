package it.sevenbits.spring_boot.task_manager.web.service.signin;

import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.web.model.SignInRequest;

/**
 * Interface for validating sign in user authentication
 */
public interface SignInService {
    /**
     * Sign in user
     *
     * @param request request with received username and password
     * @return user or null if password doesn't match
     */
    User signIn(SignInRequest request);
}
