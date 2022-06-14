package com.pz.service.impl;

import com.pz.dto.MaterialDto;
import com.pz.exception.ResourceNotFoundException;
import com.pz.exception.UnauthorizedException;
import com.pz.model.Category;
import com.pz.model.Materijal;
import com.pz.repository.MaterijalRepository;
import com.pz.security.UserPrincipal;
import com.pz.service.MaterijalService;
import com.pz.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterijalServiceImpl implements MaterijalService {
    @Autowired
    private MaterijalRepository materijalRepository;

    @Override
    public List<Materijal> getAllMaterijal() {

        List<Materijal> all = materijalRepository.findAll();
//        return all.stream()
//                .map(material -> MaterialDto.builder()
//                        .createdBy(material.getCreatedBy())
//                        .title(material.getTitle())
//                        .description(material.getDescription())
//                        .photo(material.getPhoto())
//                        .price(material.getPrice())
//                        .id(material.getId())
//                        .createdBy(material.getCreatedBy())
//                        .build())
//                .collect(Collectors.toList());
        return all;

    }

    @Override
    public List<Materijal> getMaterialsByCategoryId(Long id) {
        List<Materijal> allByCategoryId = materijalRepository.findAllByCategoryId(id);
//        return allByCategoryId.stream()
//                .map(material -> MaterialDto.builder()
//                        .createdBy(material.getCreatedBy())
//                        .title(material.getTitle())
//                        .description(material.getDescription())
//                        .photo(material.getPhoto())
//                        .price(material.getPrice())
//                        .id(material.getId())
//                        .createdBy(material.getCreatedBy())
//                        .build())
//        .collect(Collectors.toList());
        return  allByCategoryId;
    }

    @Override
    public Optional<Materijal> getMaterialById(Long id) {
        return materijalRepository.findById(id);
    }

    @Override
    public ResponseEntity<Materijal> addMaterial(Materijal materijal, UserPrincipal currentUser) {
        materijal.setCreatedBy(currentUser.getId());
        Materijal newMaterial = materijalRepository.save(materijal);
        return new ResponseEntity<>(newMaterial, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteMaterial(Long id, UserPrincipal currentUser) {
        Materijal material = materijalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("material", "id", id));

        if (material.getCreatedBy().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            materijalRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted material"), HttpStatus.OK);
        }
        throw new UnauthorizedException("You don't have permission to delete this material");
    }
}
