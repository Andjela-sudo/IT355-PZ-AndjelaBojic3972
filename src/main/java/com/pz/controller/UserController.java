package com.pz.controller;

import com.pz.model.Roles;
import com.pz.model.Users;
import com.pz.security.CurrentUser;
import com.pz.security.UserPrincipal;
import com.pz.service.ListService;
import com.pz.service.RolesService;
import com.pz.service.UsersService;
import com.pz.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ListService listService;

    @GetMapping("/")
    public ResponseEntity<List<Users>> getUsers(){
        return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Users> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        Users userSummary = new Users(currentUser.getId(),currentUser.getFirstName(),currentUser.getLastName(),currentUser.getUsername());

        return new ResponseEntity<>(userSummary, HttpStatus.OK);
    }

    @GetMapping("/{user_id}/lists")
    public ResponseEntity<List<com.pz.model.List>> getPostsCreatedBy(@PathVariable(value = "user_id") String id) {
        Optional<Users> user = usersService.getById(Long.parseLong(id));
        List<com.pz.model.List> response = null;
        if(user.isPresent()) {
            response = listService.getListsCreatedBy(user.get());
        }

        return new ResponseEntity<  >(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Users> addUser(@Valid @RequestBody Users user) {
        Users newUser = usersService.addUser(user);

        return new ResponseEntity< >(newUser, HttpStatus.CREATED);
    }


    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") String username,
                                                  @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = usersService.deleteUser(username, currentUser);

        return new ResponseEntity< >(apiResponse, HttpStatus.OK);
    }
}
