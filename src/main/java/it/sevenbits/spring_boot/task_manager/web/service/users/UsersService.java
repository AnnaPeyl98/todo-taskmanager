package it.sevenbits.spring_boot.task_manager.web.service.users;
import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.web.model.request.UpdateUserRequest;
import it.sevenbits.spring_boot.task_manager.web.model.response.UpdateUserResponse;

import java.util.List;

/**
 * Interface for work with users in server
 */
public interface UsersService {
    /**
     * Get user by ID
     *
     * @param id ID of the user
     * @return user from database
     */
    User getUserById(String id);

    /**
     * Get user by his name
     *
     * @param name name of the user
     * @return user from database
     */
    User getUserByName(String name);

    /**
     * Get all users in database
     *
     * @return list of users
     */
    List<User> getAllUsers();

    /**
     * Update user account
     *
     * @param id      user ID
     * @param request request with new account data
     * @return updated user
     */
    UpdateUserResponse updateUser(String id, UpdateUserRequest request);
}
