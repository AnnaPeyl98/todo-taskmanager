package it.sevenbits.spring_boot.task_manager.web.service.whoami;


import it.sevenbits.spring_boot.task_manager.core.model.User;

/**
 * Interface for recognizing current user
 */
public interface WhoAmIService {
    /**
     * Get information about current user
     *
     * @param token token, received in request header
     * @return user info
     */
    User getCurrentUserInfo(String token);
}
