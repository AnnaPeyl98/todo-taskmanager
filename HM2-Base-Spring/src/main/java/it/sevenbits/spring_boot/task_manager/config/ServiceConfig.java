package it.sevenbits.spring_boot.task_manager.config;

import it.sevenbits.spring_boot.task_manager.core.repository.tasks.TasksRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.users.UsersRepository;
import it.sevenbits.spring_boot.task_manager.web.service.DataBaseService;
import it.sevenbits.spring_boot.task_manager.web.service.TaskService;
import it.sevenbits.spring_boot.task_manager.web.service.signin.SignInService;
import it.sevenbits.spring_boot.task_manager.web.service.signin.SignInServiceImpl;
import it.sevenbits.spring_boot.task_manager.web.service.signup.SignUpService;
import it.sevenbits.spring_boot.task_manager.web.service.signup.SignUpServiceImpl;
import it.sevenbits.spring_boot.task_manager.web.service.tokens.JsonWebTokenService;
import it.sevenbits.spring_boot.task_manager.web.service.tokens.JwtTokenService;
import it.sevenbits.spring_boot.task_manager.web.service.users.UsersService;
import it.sevenbits.spring_boot.task_manager.web.service.users.UsersServiceImpl;
import it.sevenbits.spring_boot.task_manager.web.service.whoami.WhoAmIService;
import it.sevenbits.spring_boot.task_manager.web.service.whoami.WhoAmIServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Defines configuration service
 */
@Configuration
public class ServiceConfig {
    /**
     * Create Service
     *
     * @param tasksRepository repository for tasks
     * @return service for this repository
     */
    @Bean
    public TaskService taskService(final TasksRepository tasksRepository) {
        return new DataBaseService(tasksRepository);
    }

    /**
     * Create users service
     *
     * @param usersRepository   repository with users
     * @return service created by repository
     */
    @Bean
    @Qualifier("usersService")
    public UsersService usersService(
            @Qualifier("usersRepository") final UsersRepository usersRepository) {
        return new UsersServiceImpl(usersRepository);
    }

    /**
     * Create service for working with JWT tokens
     *
     * @param settings settings for token signature
     * @return token service
     */
    @Bean
    @Qualifier(value = "jwtTokenService")
    public JwtTokenService jwtTokenService(final JwtSettings settings) {
        return new JsonWebTokenService(settings);
    }

    /**
     * Create service for signing in users
     *
     * @param usersRepository repository with users
     * @param passwordEncoder encoder of a user password
     * @return service that signs in user
     */
    @Bean
    @Qualifier(value = "signInService")
    public SignInService signInService(
            @Qualifier("usersRepository") final UsersRepository usersRepository,
            @Qualifier("passwordEncoder") final PasswordEncoder passwordEncoder) {
        return new SignInServiceImpl(usersRepository, passwordEncoder);
    }

    /**
     * Create service for creating new accounts
     *
     * @param usersRepository repository with users
     * @param passwordEncoder encoder of a user password
     * @return service
     */
    @Bean
    @Qualifier(value = "signUpService")
    public SignUpService signUpService(
            @Qualifier("usersRepository") final UsersRepository usersRepository,
            @Qualifier("passwordEncoder") final PasswordEncoder passwordEncoder) {
        return new SignUpServiceImpl(usersRepository, passwordEncoder);
    }

    /**
     * Create service for recognizing current user
     *
     * @param usersRepository repository with users
     * @param jwtTokenService service that works with user tokens
     * @return information service
     */
    @Bean
    @Qualifier(value = "whoAmIService")
    public WhoAmIService whoAmIService(
            @Qualifier("usersRepository") final UsersRepository usersRepository,
            @Qualifier(value = "jwtTokenService") final JwtTokenService jwtTokenService) {
        return new WhoAmIServiceImpl(usersRepository, jwtTokenService);
    }
}
