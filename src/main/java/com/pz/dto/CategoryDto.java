package com.pz.dto;

import com.pz.model.Materijal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Builder
@Setter
@Getter
public class CategoryDto {
    private Long id;

    private String name;

    private Long createdBy;

}
