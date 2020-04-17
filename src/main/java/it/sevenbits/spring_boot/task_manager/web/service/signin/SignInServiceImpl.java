package it.sevenbits.spring_boot.task_manager.web.service.signin;


import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.core.repository.users.UsersRepository;
import it.sevenbits.spring_boot.task_manager.web.model.request.SignInRequest;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Service that verifying authentication user data
 */
@Service
public class SignInServiceImpl implements SignInService {
    private UsersRepository repository;
    private PasswordEncoder passwordEncoder;

    /**
     * Create authentication service
     *
     * @param repository      repository with users
     * @param passwordEncoder encoder, that verifying data
     */
    public SignInServiceImpl(final UsersRepository repository, final PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signIn(final SignInRequest request) {
        User user = repository.findUserByName(request.getUsername());

        // TODO add exceptions!!!
        if (user == null) {
            return null;
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return null;
        }
        return new User(user.getId(), user.getUsername(), user.getPassword(), true, user.getAuthorities());
    }
}
