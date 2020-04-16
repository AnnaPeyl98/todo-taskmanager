package it.sevenbits.spring_boot.task_manager.web.service.signup;


import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.core.repository.users.UsersRepository;
import it.sevenbits.spring_boot.task_manager.web.model.request.SignUpRequest;
import it.sevenbits.spring_boot.task_manager.web.model.response.SignUpResponse;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Service for creating new user account
 */
@Service
public class SignUpServiceImpl implements SignUpService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create sign up service
     *
     * @param usersRepository repository with users
     * @param passwordEncoder encoder, that secures user password
     */
    public SignUpServiceImpl(final UsersRepository usersRepository, final PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create new account for user
     *
     * @param request request with user personal data
     * @return response with user ID or null if user with received username are already exists
     */
    @Override
    public SignUpResponse createAccount(final SignUpRequest request) {
        String securePassword = passwordEncoder.encode(request.getPassword());
        User user = usersRepository.createUser(new SignUpRequest(request.getUsername(), securePassword));
        return user == null ? null : new SignUpResponse(user.getId());
    }
}
