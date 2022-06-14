package com.pz.controller;


import com.pz.exception.UnauthorizedException;
import com.pz.model.Materijal;
import com.pz.security.CurrentUser;
import com.pz.security.UserPrincipal;
import com.pz.service.MaterijalService;
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
@RequestMapping("/api/material")
public class MaterijalController {
    @Autowired
    private MaterijalService materijalService;

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Materijal>> getMaterialsByCategory(@PathVariable(name = "id") Long id) {
        List<Materijal> response = materijalService.getMaterialsByCategoryId(id);

        return new ResponseEntity< >(response, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<Materijal>> getMaterialsByCategory() {
        List<Materijal> response = materijalService.getAllMaterijal();

        return new ResponseEntity< >(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Materijal> getMaterialById(@PathVariable(name = "id") Long id) {
        Optional<Materijal> material = materijalService.getMaterialById(id);
        Materijal response = null;
        if(material.isPresent()){
            response = material.get();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Materijal> addMaterial(@Valid @RequestBody Materijal materijal,
                                                @CurrentUser UserPrincipal currentUser) {
        return materijalService.addMaterial(materijal, currentUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteMaterial(@PathVariable(name = "id") Long id,
                                                      @CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
        return materijalService.deleteMaterial(id, currentUser);
    }
}
