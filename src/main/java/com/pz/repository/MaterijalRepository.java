package com.pz.repository;

import com.pz.model.Materijal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterijalRepository extends JpaRepository<Materijal, Long> {
    List<Materijal> findAllByCategoryId(Long id);
}
