package com.pz.repository;

import com.pz.model.List;
import com.pz.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<List, Long> {
    java.util.List<List> findAllByCreatedBy(Users user);
}
