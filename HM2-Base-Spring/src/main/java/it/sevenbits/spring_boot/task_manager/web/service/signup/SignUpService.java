package it.sevenbits.spring_boot.task_manager.web.service.signup;

import it.sevenbits.spring_boot.task_manager.web.model.request.SignUpRequest;
import it.sevenbits.spring_boot.task_manager.web.model.response.SignUpResponse;

/**
 * Interface for validating request for creating new account
 */
public interface SignUpService {
    /**
     * Create new account
     *
     * @param request request with user personal data
     * @return response with info about operation
     */
    SignUpResponse createAccount(SignUpRequest request);
}
