package it.sevenbits.spring_boot.task_manager.web.service.users;

import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.core.repository.users.UsersRepository;
import it.sevenbits.spring_boot.task_manager.web.model.request.UpdateUserRequest;
import it.sevenbits.spring_boot.task_manager.web.model.response.UpdateUserResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service that validates requests to part of database with users and generates responses
 */
public class UsersServiceImpl implements UsersService {
    private final UsersRepository repository;
    private List<String> userRoles;
    private Pattern patternId;

    /**
     * Create service that validates requests and calls DAO
     *
     * @param repository DAO, connected to database
     */
    public UsersServiceImpl(final UsersRepository repository) {

        this.repository = repository;
        userRoles = new ArrayList<>();
        patternId = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
        Collections.addAll(userRoles, "ANONYMOUS", "USER", "ADMIN");
    }

    @Override
    public User getUserById(final String id) {
        if (id == null) {
            return null;
        }
        Matcher matcher = patternId.matcher(id);
        if (!matcher.matches()) {
            return null;
        }
        return repository.findUserById(id, false);
    }

    @Override
    public User getUserByName(final String name) {
        if (name == null) {
            return null;
        }
        return repository.findUserByName(name, false);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public UpdateUserResponse updateUser(final String id, final UpdateUserRequest request) {
        User needed = getUserById(id);
        if (needed == null) {
            return null;
        }

        boolean enabled = Optional
                .ofNullable(request.isEnabled())
                .orElse(true);
        List<String> roles = request.getAuthorities();
        if (roles != null) {
            for (String role : roles) {
                if (!userRoles.contains(role)) {
                    return new UpdateUserResponse("");
                }
            }
        } else {
            roles = needed.getAuthorities();
        }

        User updatedAccount = new User(id, needed.getUsername(), needed.getPassword(), enabled, roles);
        repository.updateUser(updatedAccount);
        return new UpdateUserResponse(id);
    }
}
