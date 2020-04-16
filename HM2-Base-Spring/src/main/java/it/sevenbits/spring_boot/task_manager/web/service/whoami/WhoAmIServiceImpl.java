package it.sevenbits.spring_boot.task_manager.web.service.whoami;

import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.core.repository.users.UsersRepository;
import it.sevenbits.spring_boot.task_manager.web.service.tokens.JwtTokenService;

/**
 * Service that search for information about current user
 */
public class WhoAmIServiceImpl implements WhoAmIService {
    private final UsersRepository usersRepository;
    private final JwtTokenService jwtTokenService;

    /**
     * Create service
     *
     * @param usersRepository repository with users
     * @param jwtTokenService service that works with user tokens
     */
    public WhoAmIServiceImpl(final UsersRepository usersRepository, final JwtTokenService jwtTokenService) {
        this.usersRepository = usersRepository;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public User getCurrentUserInfo(final String token) {
        if (token == null) {
            return null;
        }
        String username = jwtTokenService.parseToken(token.split(" ")[1]).getPrincipal().toString();
        return usersRepository.findUserByName(username);
    }
}
