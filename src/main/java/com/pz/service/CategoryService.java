package com.pz.service;

import com.pz.model.Category;
import com.pz.security.UserPrincipal;
import com.pz.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);

    ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser);

    ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser);
}
