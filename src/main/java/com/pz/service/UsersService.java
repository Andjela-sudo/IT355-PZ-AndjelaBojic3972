package com.pz.service;

import com.pz.model.Users;
import com.pz.security.UserPrincipal;
import com.pz.utils.ApiResponse;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<Users> getAllUsers();
    Optional<Users> getById(Long id);

    Users addUser(Users user);

    ApiResponse deleteUser(String username, UserPrincipal currentUser);
}
