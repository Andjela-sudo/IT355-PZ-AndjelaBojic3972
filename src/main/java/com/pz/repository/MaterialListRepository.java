package com.pz.repository;

import com.pz.model.List;
import com.pz.model.MaterialList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialListRepository extends JpaRepository<MaterialList, Long> {
}
