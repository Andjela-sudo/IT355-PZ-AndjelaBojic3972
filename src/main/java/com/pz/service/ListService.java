package com.pz.service;

import com.pz.model.List;
import com.pz.model.MaterialList;
import com.pz.model.Users;
import com.pz.security.UserPrincipal;
import com.pz.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ListService {
    java.util.List<List> getAllLists();
    java.util.List<List> getListsCreatedBy(Users user);

    java.util.List<List> getListsByUserId(Long id);

    ResponseEntity<ApiResponse> deleteList(Long id, UserPrincipal currentUser);

    Optional<List> getListById(Long id);

    ResponseEntity<ApiResponse> deleteListItem( Long itemId, UserPrincipal currentUser);

    ResponseEntity<List> addList(List list, UserPrincipal currentUser);

    Optional<MaterialList> getItemById(Long itemId);

    ResponseEntity<MaterialList> addListItem(MaterialList materialList, Long materialId);

    MaterialList updateItem(Long id, MaterialList newItem);
}
