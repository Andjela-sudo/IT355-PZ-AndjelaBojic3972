package com.pz.controller;

import com.pz.exception.UnauthorizedException;
import com.pz.model.MaterialList;
import com.pz.security.CurrentUser;
import com.pz.security.UserPrincipal;
import com.pz.service.ListService;
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
@RequestMapping("/api/list")
public class ListController {
    @Autowired
    private ListService listService;

    @GetMapping
    public ResponseEntity<List<com.pz.model.List>> getAllLists() {

        List<com.pz.model.List> response = listService.getAllLists();

        return new ResponseEntity< >(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<com.pz.model.List>> getMyLists(
            @CurrentUser UserPrincipal currentUser) {

        List<com.pz.model.List> response = listService.getListsByUserId(currentUser.getId());

        return new ResponseEntity< >(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.pz.model.List> getListById(@PathVariable(name = "id") Long id) {

        Optional<com.pz.model.List> response  = listService.getListById(id);
        com.pz.model.List list = null;
        if(response.isPresent()){
            list = response.get();
        }
        return new ResponseEntity< >(list, HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<com.pz.model.List>> getListsByUserId(@PathVariable(name = "id") Long id) {

        List<com.pz.model.List> response = listService.getListsByUserId(id);

        return new ResponseEntity< >(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteList(@PathVariable(name = "id") Long id,
                                                      @CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
        return listService.deleteList(id, currentUser);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<com.pz.model.List> addList(@Valid @RequestBody com.pz.model.List list,
                                                     @CurrentUser UserPrincipal currentUser) {

        return listService.addList(list, currentUser);
    }

    @GetMapping("/item/{item_id}")
    public ResponseEntity<MaterialList> getItemFromList(@PathVariable(name = "item_id") Long itemId) {

        Optional<MaterialList> response = listService.getItemById(itemId);

        return new ResponseEntity< >(response.get(), HttpStatus.OK);
    }

    @PostMapping("/item/{material_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MaterialList> addListItem( @RequestBody MaterialList materialList,
                                                     @PathVariable(name = "material_id") Long materialId) {

        return listService.addListItem(materialList, materialId);
    }

    @DeleteMapping("/item/{item_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteListItem(@PathVariable(name = "item_id") Long itemId,
                                                  @CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
        return listService.deleteListItem(itemId, currentUser);
    }

    @PutMapping("/item/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MaterialList> updateItem(@PathVariable(value = "id") Long id, @Valid @RequestBody MaterialList newItem) {
        MaterialList updatedItem = listService.updateItem(id, newItem);

        return new ResponseEntity< >(updatedItem, HttpStatus.OK);
    }

}
