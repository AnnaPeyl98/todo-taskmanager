package it.sevenbits.spring_boot.task_manager.web.controllers;

import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.web.model.request.SignInRequest;
import it.sevenbits.spring_boot.task_manager.web.model.response.SignInResponse;
import it.sevenbits.spring_boot.task_manager.web.service.signin.SignInService;
import it.sevenbits.spring_boot.task_manager.web.service.tokens.JwtTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Controller for handling sign in requests
 */
@Controller
@RequestMapping("/signin")
public class SignInController {
    private final SignInService signInService;
    private final JwtTokenService jwtTokenService;

    /**
     * Create controller for signing in user
     *
     * @param signInService   service that verifying requests
     * @param jwtTokenService service that works with user tokens
     */
    public SignInController(final SignInService signInService,
                            final JwtTokenService jwtTokenService) {
        this.signInService = signInService;
        this.jwtTokenService = jwtTokenService;
    }

    /**
     * Sign in user in server
     *
     * @param request request with user personal data
     * @return response for signing in
     * 200 - user signed in
     * 403 - received user data doesn't match with data in server
     */
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public ResponseEntity<SignInResponse> createSession(@RequestBody @Valid final SignInRequest request) {
        User user = signInService.signIn(request);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }
        String token = jwtTokenService.createToken(user);
        return ResponseEntity
                .ok()
                .body(new SignInResponse(token));
    }
}
