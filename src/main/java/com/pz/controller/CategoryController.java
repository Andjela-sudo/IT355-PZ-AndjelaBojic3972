package com.pz.controller;


import com.pz.exception.UnauthorizedException;
import com.pz.model.Category;
import com.pz.security.CurrentUser;
import com.pz.security.UserPrincipal;
import com.pz.service.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Category> getTag(@PathVariable(name = "id") Long id) {

        Optional<Category> category = categoryService.getCategoryById(id);
        Category response = null;
        if(category.isPresent()){
            response = category.get();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories() {
        List categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category,
                                                @CurrentUser UserPrincipal currentUser) {

        return categoryService.addCategory(category, currentUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "id") Long id,
                                                      @CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
        return categoryService.deleteCategory(id, currentUser);
    }


}
