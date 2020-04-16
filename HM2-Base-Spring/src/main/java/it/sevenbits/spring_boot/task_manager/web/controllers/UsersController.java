package it.sevenbits.spring_boot.task_manager.web.controllers;

import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.web.model.request.UpdateUserRequest;
import it.sevenbits.spring_boot.task_manager.web.model.response.UpdateUserResponse;
import it.sevenbits.spring_boot.task_manager.web.service.users.UsersService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.Valid;

/**
 * Class-mediator that would return data from users service to user with special authorities
 */
@RestController
@RequestMapping(value = "/users")
public class UsersController {
    private final UsersService usersService;
    private Pattern patternId;

    /**
     * Create users controller by users service
     *
     * @param usersService service that validates requests
     */
    public UsersController(final UsersService usersService) {

        this.usersService = usersService;
        patternId = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
    }

    /**
     * Get user by his ID
     *
     * @param id ID of the user
     * @return ResponseEntity with user data or error code
     * 200 - found user
     * 403 - forbidden
     * 404 - user not found
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") final String id) {
        Matcher matcher = patternId.matcher(id);
        if (matcher.matches()) {
            User user = usersService.getUserById(id);
            if (user == null) {
                return ResponseEntity
                        .notFound()
                        .build();
            }
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(user);
        }
        return ResponseEntity
                .badRequest()
                .build();
    }

    /**
     * Get all users in server
     *
     * @return list with users
     * 200 - ok
     * 403 - forbidden
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(usersService.getAllUsers());
    }

    /**
     * Update user account
     *
     * @param id      user ID
     * @param request request with ne data
     * @return ResponseEntity with operation code
     * 204 - user updated
     * 400 - new data validation exception
     * 403 - forbidden
     * 404 - user not found
     */
    @PatchMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Void> updateUserAccount(@PathVariable(value = "id") final String id,
                                                  @RequestBody @Valid final UpdateUserRequest request) {
        Matcher matcher = patternId.matcher(id);
        if (!matcher.matches()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        UpdateUserResponse response = usersService.updateUser(id, request);
        if (response == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        if (response.getId().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        return ResponseEntity
                .noContent()
                .build();
    }
}


