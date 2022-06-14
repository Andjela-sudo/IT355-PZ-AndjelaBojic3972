package com.pz.repository;

import com.pz.exception.ResourceNotFoundException;
import com.pz.model.Users;
import com.pz.security.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(@NotBlank String username);

    Optional<Users> findByEmail(@NotBlank String email);

    Boolean existsByUsername(@NotBlank String username);

    Boolean existsByEmail(@NotBlank String email);

    Optional<Users> findByUsernameOrEmail(String username, String email);

    default Users getUser(UserPrincipal currentUser) {
        return getUserByName(currentUser.getUsername());
    }

    default Users getUserByName(String username) {
        return findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }
}
