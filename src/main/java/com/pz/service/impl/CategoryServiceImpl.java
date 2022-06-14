package com.pz.service.impl;

import com.pz.exception.ResourceNotFoundException;
import com.pz.exception.UnauthorizedException;
import com.pz.model.Category;
import com.pz.repository.CategoryRepository;
import com.pz.security.UserPrincipal;
import com.pz.service.CategoryService;
import com.pz.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id) ;
    }

    @Override
    public ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser) {
        category.setCreatedBy(currentUser.getId());
        Category newCategory = categoryRepository.save(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        System.out.println(category);
        if (category.getCreatedBy().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted category"), HttpStatus.OK);
        }
        throw new UnauthorizedException("You don't have permission to delete this category");
    }
}
