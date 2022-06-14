package com.pz.service;

import com.pz.dto.MaterialDto;
import com.pz.model.Materijal;
import com.pz.security.UserPrincipal;
import com.pz.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MaterijalService {
    List<Materijal> getAllMaterijal();
    List<Materijal> getMaterialsByCategoryId(Long id);

    Optional<Materijal> getMaterialById(Long id);

    ResponseEntity<Materijal> addMaterial(Materijal materijal, UserPrincipal currentUser);

    ResponseEntity<ApiResponse> deleteMaterial(Long id, UserPrincipal currentUser);
}
