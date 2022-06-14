package com.pz.service.impl;

import com.pz.exception.ResourceNotFoundException;
import com.pz.exception.UnauthorizedException;
import com.pz.model.*;
import com.pz.repository.ListRepository;
import com.pz.repository.MaterialListRepository;
import com.pz.repository.MaterijalRepository;
import com.pz.repository.UsersRepository;
import com.pz.security.UserPrincipal;
import com.pz.service.ListService;
import com.pz.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListServiceImpl implements ListService {
    @Autowired
    private ListRepository listRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MaterialListRepository materialListRepository;

    @Autowired
    private MaterijalRepository materijalRepository;

    @Override
    public java.util.List<List> getAllLists() {
        return listRepository.findAll();
    }

    @Override
    public java.util.List<List> getListsCreatedBy(Users user) {
        return listRepository.findAllByCreatedBy(user);
    }

    @Override
    public java.util.List<List> getListsByUserId(Long id) {
        Optional<Users> user = usersRepository.findById(id);
        if(user.isPresent()){
            return listRepository.findAllByCreatedBy(user.get());
        }
        throw new ResourceNotFoundException("user","id", id);
    }

    @Override
    public Optional<List> getListById(Long id) {
        return listRepository.findById(id);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteListItem( Long itemId, UserPrincipal currentUser) {
        MaterialList materialList = materialListRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("list item", "id", itemId));
        List list = listRepository.findById(materialList.getListId()).get();
        if (list.getCreatedBy().getId().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            materialListRepository.deleteById(itemId);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted list item"), HttpStatus.OK);
        }
        throw new UnauthorizedException("You don't have permission to delete this list item");

    }

    @Override
    public ResponseEntity<List> addList(List list, UserPrincipal currentUser) {
        Optional<Users> user = usersRepository.findById(currentUser.getId());
        list.setCreatedBy(user.get());
        List newList = listRepository.save(list);
        return new ResponseEntity<>(newList, HttpStatus.CREATED);
    }

    @Override
    public Optional<MaterialList> getItemById(Long itemId) {
        return materialListRepository.findById(itemId);
    }

    @Override
    public ResponseEntity<MaterialList> addListItem(MaterialList materialList, Long materialId) {
        Materijal material = materijalRepository.findById(materialId).get();
        materialList.setMaterial(material);
        MaterialList newItem = materialListRepository.save(materialList);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @Override
    public MaterialList updateItem(Long id, MaterialList newItem) {
        MaterialList materialList = materialListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("item", "id", id));

            materialList.setKolicina(newItem.getKolicina());

            return materialListRepository.save(materialList);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteList(Long id, UserPrincipal currentUser) {
        List list = listRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("list", "id", id));

        if (list.getCreatedBy().getId().equals(currentUser.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            listRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted list"), HttpStatus.OK);
        }
        throw new UnauthorizedException("You don't have permission to delete this list");
    }

}
